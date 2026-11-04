
# Score! Hero Mod - Moedas Infinitas & Sem Ads

APK modificado do jogo **Score! Hero** (versão 4.730) para Android.

## O que foi modificado

- **Moedas infinitas:** o valor concedido nas recompensas foi forçado para 100.000,000 (100M) de moedas.
- **Sem anúncios:** a inicialização/exibição de anúncios (AdMob) foi desativada.

## Como o mod funciona

1. O jogo original guarda a lógica de recompensas e anúncios no codigo nativo via JNI (`FTTJNI` / `FTTANR`).
2. O mod altera os pontos de abertura do fluxo no codigo Java/Smali:
   - `FTTAdSupport.IsAdMobInitialised` -> retorna false (sem ads).
   - `FTTAdSupport.ShowAdMobVideo` / handler de recompensa -> concede quantidade massiva de moedas.

## Instalação

- Desinstalar a versão original antes.
- Instalar o APK modificado (liberar "instalar desconhecidos").
