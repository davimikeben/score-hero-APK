#!/usr/bin/env python3
"""
OverPeak v0.71 Mod Menu - IL2CPP Patch Generator
Gera instruções para patching IL2CPP (NÃO smali tradicional)

IMPORTANTE: OverPeak usa arquitetura IL2CPP do Unity.
Patches smali tradicionais NÃO funcionarão para Infinite Ammo.
O caminho correto é hookar métodos em libil2cpp.so.
"""

def generate_il2cpp_patch_guide():
    print("=" * 60)
    print("INFINITE AMMO PATCH - IL2CPP APPROACH")
    print("=" * 60)
    print()
    print("IMPORTANTE: OverPeak usa IL2CPP - smali patches nao funcionam!")
    print()
    print("TOOLS NECESSARIAS:")
    print("  1. Il2CppDumper: https://github.com/Perfare/Il2CppDumper")
    print("  2. Il2CppPatcher ou XUnityPatcher")
    print()
    print("PASSOS:")
    print("  Step 1: python3 Il2CppDumper OverPeak_v0.71.apk /tmp/ildata")
    print("  Step 2: Encontrar classes Weapon, PlayerWeapon")
    print("  Step 3: Identificar m_CurrentProjectileCount offset")
    print("  Step 4: Aplicar patch IL2CPP para valor 9999")
    print()
    print("EXEMPLO DE PATCH IL2CPP (C# Harmony):")
    print("  [HarmonyPatch(typeof(Weapon), \"get_CurrentProjectileCount\")]")
    print("  class InfiniteAmmoPatch {")
    print("      static void Postfix(ref int __result) {")
    print("          __result = 9999; // Infinite ammo!")
    print("      }")
    print("  }")
    print()
    print("EM VEZ DE SMALI (nao funciona):")
    print("  # NAO FAÇA ISTO - nao funciona em IL2CPP")
    print("  .method public getAmmo()F")
    print("      .locals 2")
    print("      const/4 v1, 0x7f")
    print("      float-to-int v1, v1")
    print("      int-to-float v1, v1")
    print("      returnfloat v1")
    print("  .end method")
    print()
    print("ARQUIVOS NO GITHUB:")
    print("  - ASSEMBLY_INSTRUCTIONS.md - Instrucoes detalhadas")
    print("  - generate_patches.py - Este script")
    print("  - README.md - Documentacao IL2CPP")
    print()
    print("PARA TESTAR ANTES DE COMPILAR:")
    print("  - Use Cheat Engine com IL2CPP support")
    print("  - Ou WeMod com OverPeak v0.71")
    print()

if __name__ == "__main__":
    generate_il2cpp_patch_guide()
