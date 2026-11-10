# OverPeak v0.71 Mod Menu

## Overview
Mod Menu APK for OverPeak: Action Game version 0.71, developed by R-USER Games.

## Features
- ✅ **Infinite Ammo / Unlimited Bullets** - Never run out of ammunition
- ✅ **God Mode** - Player invulnerability
- ✅ **Unlimited Energy** - Endless special abilities
- ✅ **No Ads** - Remove all in-game advertisements
- ✅ **All Suits Unlocked** - All character costumes available
- ✅ **Speed Control** - Adjustable speed multiplier
- ✅ **Free Mode** - Free mode always available

## Installation
1. **Remove original game** if installed
2. **Build the mod APK** using APKTool or similar tool
3. **Install the modified APK** on Android device (allow "install from unknown sources")
4. **Launch the game** and select mods from the mod menu

## Building from Source

### Prerequisites
- APKTool (v2.9+)
- Java Development Kit (JDK 17+
- ApkSigner or uber-apk-signer
- Optional: jadx for decompiling

### Build Steps

1. **Decompile the original APK:**
   ```
   apktool d OverPeak_v0.71.apk -o decoded-overpeak
   ```

2. **Replace smali code with modded versions:**
   - Copy `src/` folder to `decoded-overpeak/smali/`
   - Copy `res/` folder to `decoded-overpeak/res/`

3. **Modify the AndroidManifest.xml** if needed

4. **Rebuild the APK:**
   ```
   apktool b decoded-overpeak -o OverPeak-v0.71-mod.apk
   ```

5. **Sign the APK:**
   ```
   apksigner sign --overwrite --cert ./cert.txt OverPeak-v0.71-mod.apk
   ```

## Mod Menu Usage
- Launch the game - mod menu should appear automatically
- Toggle features on/off using checkboxes
- Adjust speed multiplier using the seekbar
- All settings are saved via SharedPreferences

## Implementation Details

### Infinite Ammo Patch
The mod patches the game's ammunition counter to always return maximum value. This typically involves:
- Finding the ammo field in the weapon/firearm class
- Setting it to maximum value (e.g., 9999 or INT_MAX)
- Hooking the getAmmo() method to always return max

### God Mode Patch
- Modifies the damage taken multiplier to 0
- Prevents health from decreasing
- Hooks the onTakeDamage() method

### Unlimited Energy
- Sets energy meter to maximum value
- Prevents energy from draining during ability use
- Hooks the getEnergy() method

## Credits
- Mod menu framework based on standard Android modding patterns
- OverPeak game by R-USER Games
- Based on modding techniques from community projects

## Disclaimer
- Modding may violate the game's Terms of Service
- Use at your own risk
- Backup original game data before installing mods
- This mod is for educational and personal use only