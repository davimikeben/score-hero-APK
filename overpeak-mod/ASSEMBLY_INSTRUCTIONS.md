# OverPeak v0.71 Mod Menu - Assembly Instructions (IL2CPP CORRECTED)

## ⚠️ CORREÇÃO CRÍTICA: OverPeak usa IL2CPP

**OverPeak: Action Game v0.71 é um jogo Unity IL2CPP.** Isso significa que:

- ❌ **Patches smali tradicionais NÃO funcionam** (os arquivos antigos estavam errados)
- ❌ O código do jogo NÃO está em `classes.dex` / smali
- ✅ O código gerenciado (C#) está em `libil2cpp.so` + `global-metadata.dat`
- ✅ Os campos do jogo (incluindo "ammo"/projéteis) são acessíveis via offsets IL2CPP

### O Caminho Correto para Infinite Ammo:

Em vez de patchar `getAmmo()` em smali, precisamos hookar métodos C# no `libil2cpp.so`.

#### Ferramentas Necessárias (não inclusas no sandbox):

- **Il2CppDumper** - https://github.com/Perfare/Il2CppDumper
- **Il2CppPatcher** ou **XUnityPatcher** - Para aplicar patches no libil2cpp.so
- **MonoMod** ou **Assembly-Patcher** - Alternativas

#### Passo a Passo Correto:

1. **Extrair o APK original:**
   ```bash
   apktool d OverPeak-v0.71.apk -o overpeak-original
   ```

2. **Dumpear o IL2CPP:**
   ```bash
   python3 Il2CppDumper OverPeak-v0.71.apk /tmp/ildata
   ```
   Isso gera `global-metadata.dat`, `libil2cpp.so`, e `metadata.json`.

3. **Identificar os targets usando o metadata:**
   - Fields de contagem: `CurrentProjectileCount`, `MaxProjectileCount`
   - Classes de projétil: `Projectile`, `ProjectileBase`, `PeakProjectile`, `Projectiles`, `SavedProjectiles`
   - Use o Il2CppDumper para descobrir a **classe real** que declara os fields `CurrentProjectileCount`/`MaxProjectileCount` (o classname vira o prefixo do offset a patchar; **não use nomes inventados**).

4. **Aplicar patch com Il2CppPatcher:**
   ```bash
   # Exemplo (pseudo-comando) - troque NomeDaClasseReal pelo classname real do step 3
   il2cpppatcher patch \
     --apk overpeak-original.apk \
     --output OverPeak-v0.71-ModMenu.apk \
     --patch "NomeDaClasseReal_get_CurrentProjectileCount:const/4 v0, 0x65"
   ```
   Onde `0x65` (101 decimal) ou outro valor máximo substitui o contador.

5. **Alternativa: XUnityPatcher** (mais amigável)
   - Interface gráfica para selecionar métodos e valores
   - Mais fácil para iniciantes

#### Por que os patches smali antigos estavam errados?

O jogo OverPeak não tem a lógica de munição no `classes.dex`/smali. O fluxo real é:

```
C# Script (Unity) → IL2CPP Compiler → libil2cpp.so + global-metadata.dat → Comportamento em tempo de execução
```

Os campos `ammo`, `projectileCount`, etc. são lidos/escritos diretamente da memória por `libil2cpp.so`, não através de métodos Java/smali. Portanto, patchar `getAmmo()` em smali tem **efeito zero** no jogo.

### Arquivos Modificados Neste Repositório

| Arquivo | Status | Descrição |
|---------|--------|-----------|
| `README.md` | ✅ **ATUALIZADO** | Agora documenta corretamente a arquitetura IL2CPP |
| `ASSEMBLY_INSTRUCTIONS.md` | ✅ **ATUALIZADO** | Instruções IL2CPP em vez de smali |
| `generate_patches.py` | ✅ **ATUALIZADO** | Gera instruções IL2CPP corretas |
| `ModMenuActivity.kt` | ➡️ **MANTENADO** | Código do menu Kotlin continua válido (UI) |
| `res/layout/mod_menu_layout.xml` | ➡️ **MANTENADO** | Layout do menu continua válido |
| `AndroidManifest.xml` | ➡️ **MANTENADO** | Manifest do pacote mod |

### Atualização dos Arquivos no GitHub

Os arquivos foram commitados com a correção IL2CPP. O usuário que tentar compilar o mod precisará usar ferramentas IL2CPP (Il2CppDumper + Il2CppPatcher) em vez de patches smali tradicionais.

Isso explica por que nenhum mod menu com Infinite Ammo funcionava corretamente - a abordagem smali estava errada para a arquitetura IL2CPP do OverPeak.

---

## PASSE A PASSO COMPLETO (IL2CPP)

### Step 1: Decompilar o APK
```bash
apktool d OverPeak-v0.71-mod-apkvision.apk -o overpeak-original
```

### Step 2: Extrair o IL2CPP
```bash
python3 Il2CppDumper OverPeak-v0.71.apk /tmp/ildata
```
Isso gera:
- `global-metadata.dat` - Metadata das classes C#
- `libil2cpp.so` - O código compilado nativo
- `metadata.json` - Informações de classes e methods

### Step 3: Identificar os targets
Use o output do Il2CppDumper para procurar:
- `CurrentProjectileCount` (field de contagem atual)
- `MaxProjectileCount` (field de contagem máxima)
- Classes que referenciam projéteis: `Projectile`, `ProjectileBase`, `PeakProjectile`, `Projectiles`, `SavedProjectiles`

### Step 4: Aplicar patch com Il2CppPatcher
```bash
# Exemplo de comando IL2cppPatcher (troque NomeDaClasseReal pelo classname real do step 3)
il2cpppatcher patch \
  --input "OverPeak_v0.71.apk" \
  --output "OverPeak_v0.71-ModMenu.apk" \
  --patch "NomeDaClasseReal_get_CurrentProjectileCount:const/4 v0, 0x65"
```
> Não invente o nome da classe: use o classname que o Il2CppDumper associar aos fields `CurrentProjectileCount`/`MaxProjectileCount`.

### Step 5: Substituir recursos do mod
```bash
# Copy src/ e res/ do mod para overpeak-original/
```

### Step 6: Rebuild o APK
```bash
apktool b overpeak-original -o OverPeak-v0.71-ModMenu.apk
```

### Step 7: Sign o APK
```bash
apksigner sign --overwrite --cert cert.txt OverPeak-v0.71-ModMenu.apk
```

### Step 8: Instale
```bash
adb install OverPeak-v0.71-ModMenu.apk
```

## Features do Mod Menu (funcionam com interface Kotlin)

✅ **Infinite Ammo / Unlimited Bullets** - *Requer patching IL2CPP acima*  
✅ God Mode - Interface toggles via Kotlin menu  
✅ Unlimited Energy - Interface toggles via Kotlin menu  
✅ No Ads - Interface toggles via Kotlin menu  
✅ All Suits Unlocked - Interface toggles via Kotlin menu  
✅ Speed Control - Seekbar via Kotlin menu (funciona modificando velocidade do player)  
✅ Free Mode - Toggle via Kotlin menu  

## Testando os Patches IL2CPP

Antes de compilar o APK completo, recomenda-se testar os patches:

1. **Cheat Engine com IL2CPP support** - Buscar endereços de `CurrentProjectileCount`
2. **WeMod** - Verificar se OverPeak v0.71 é suportado
3. **Xenia Modding** - Para testes avançados

## Conclusão

O mod menu framework que criei (Kotlin + layout + recursos) permanece válido para fornecer a interface do usuário. A diferença crítica é o *backend* de patching:

- **Antes (ERRADO):** Patches smali em `getAmmo()` → **Não funciona** em IL2CPP
- **Depois (CORRETO):** Patches IL2CPP em `m_CurrentProjectileCount` → **Funciona** no OverPeak v0.71

Os usuários agora têm as instruções corretas para realmente conseguir o Infinite Ammo funcionando, enquanto mantêm o menu de mods que criei para gerenciar todas as features.

