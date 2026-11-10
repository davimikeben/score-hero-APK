# OverPeak v0.71 Mod Menu

## ⚠️ IMPORTANTE: Arquitetura IL2CPP

**OverPeak: Action Game v0.71 usa Unity IL2CPP**, o que significa que o código do jogo NÃO está em smali (classes.dex), mas sim em `libil2cpp.so` + `global-metadata.dat`.

### Isso afeta o patching de Infinite Ammo:

- ❌ **Patches smali tradicionais NÃO funcionam** no OverPeak
- ❌ Métodos como `getAmmo()` não existem no código gerenciado do Unity
- ✅ **O caminho correto é hookar métodos IL2CPP** em `libil2cpp.so`

### Features do Mod Menu

✅ **Infinite Ammo / Unlimited Bullets** - *Requer patching IL2CPP (ver docs)*  
✅ God Mode  
✅ Unlimited Energy  
✅ No Ads  
✅ All Suits Unlocked  
✅ **Speed Control** - Adjustable speed multiplier  
✅ **Free Mode** - Free mode always available  

### Building from Source

#### Prerequisites

- [ ] **Il2CppDumper** - Para extrair metadata e offsets
- [ ] **Il2CppPatcher** ou **XUnityPatcher** - Para aplicar patches no libil2cpp.so
- [ ] **APKTool** - Para dessempacar/reesempacar recursos Android
- [ ] **apksigner** - Para assinar o APK
- [ ] **Java JDK 17+** - Still needed for APKTool

#### Passo a Passo IL2CPP

1. **Decompile o APK original:**
   ```bash
   apktool d OverPeak_v0.71.apk -o overpeak-decompiled
   ```

2. **Dumpear o IL2CPP com Il2CppDumper:**
   ```bash
   python3 Il2CppDumper OverPeak_v0.71.apk /tmp/ildata
   ```
   Isso gera `global-metadata.dat`, `libil2cpp.so`, `metadata.json`.

3. **Identificar métodos target usando o metadata:**
   - Classes:  - fields: , 
   - Fields: `m_CurrentProjectileCount`, `m_MaxProjectileCount`
   - Methods: `get_CurrentProjectileCount()`, `get_MaxProjectileCount()`

4. **Aplicar patch com Il2CppPatcher ou XUnityPatcher:**
   - Patch `get_CurrentProjectileCount` → sempre retorna 9999
   - Patch `get_MaxProjectileCount` → sempre retorna 9999

5. **Substituir recursos do mod:**
   - Copy `src/` e `res/` do mod para `overpeak-decompiled/`

6. **Rebuild o APK:**
   ```bash
   apktool b overpeak-decompiled -o OverPeak-v0.71-ModMenu.apk
   ```

7. **Sign o APK:**
   ```bash
   apksigner sign --overwrite --cert ./cert.txt OverPeak-v0.71-ModMenu.apk
   ```

8. **Instale:**
   ```bash
   adb install OverPeak-v0.71-ModMenu.apk
   ```

### Mod Menu Usage

- Launch the game - mod menu should appear automatically
- Toggle features on/off using checkboxes
- Adjust speed multiplier using the seekbar
- All settings are saved via SharedPreferences

### Documentação Completa

Para instruções detalhadas com telas e comandos exatos, consulte:
- `ASSEMBLY_INSTRUCTIONS.md` - Instruções passo-a-passo IL2CPP
- `generate_patches.py` - Script que gera patches IL2CPP corretos
- `README.md` - Esta visão geral

### Desclaimer

- Modding pode violar os Termos de Serviço do jogo
- Use por sua própria risco
- Faça backup dos dados originais antes de instalar mods
- Este mod é para uso educacional e pessoal somente
