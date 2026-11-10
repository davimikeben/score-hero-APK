# OverPeak v0.71 Mod Menu - Assembly Instructions

## Prerequisites

1. **APKTool** - https://github.com/iBotPeaches/Apktool
2. **Java JDK 17+** - Required for APKTool
3. **ApkSigner** - For signing the modified APK
4. **Optional: jadx** - For decompiling and analyzing smali code

## Step-by-Step Assembly

### Step 1: Extract Original APK
```bash
apktool d OverPeak-v0.71-mod-apkvision.apk -o overpeak-original
```

### Step 2: Replace Smali Code
Copy the modified smali files:
```bash
# Copy smali source
cp -r /workspace/overpeak-mod/smali/* overpeak-original/smali/

# Copy res resources
cp -r /workspace/overpeak-mod/res/* overpeak-original/res/
```

### Step 3: Update AndroidManifest
The AndroidManifest.xml in the mod package should match the original package:
- Package name: `com.rusergames.overpeak`
- Activities should be preserved from original

### Step 4: Rebuild APK
```bash
apktool b overpeak-original -o OverPeak-v0.71-mod-menu.apk
```

### Step 5: Sign the APK
```bash
# Option 1: Use existing keystore
apksigner sign --overwrite --ks debug.keystore OverPeak-v0.71-mod-menu.apk

# Option 2: Use debug mode
uber-apk-signer --apk OverPeak-v0.71-mod-menu.apk --overwrite
```

### Step 6: Install and Test
```bash
# Install on device (USB debugging required)
adb install OverPeak-v0.71-mod-menu.apk

# Or install via sideload
```

## Mod Menu Integration

The ModMenuActivity needs to be injected into the original game. Two approaches:

### Approach A: Replace Main Activity
1. In AndroidManifest, replace the original MainActivity with ModMenuActivity
2. ModMenuActivity will apply patches on creation
3. Then start the original game's main activity

### Approach B: Add Mod Menu as Separate Entry
1. Keep original MainActivity intact
2. Add ModMenuActivity as additional launchable activity
3. User can launch from app drawer or shortcut

## Patch Implementation Notes

### Finding Target Methods
To implement the patches, you need to find the relevant smali methods:

1. **Decompile with jadx:**
   ```bash
   jadx -d decoded overpeak-original.apk
   ```

2. **Search for ammo-related code:**
   Look for methods like:
   - `getAmmo()` or `getBulletCount()`
   - `setAmmo()` or `setBulletCount()`
   - `fireWeapon()` or `shoot()`

3. **Common patterns in OverPeak:**
   - Weapon class with ammo field
   - FirearmController or similar
   - Game manager handling resources

### Sample Smali Patch for Infinite Ammo

```smali
# Original method
.method public getAmmo()F
    .locals 1
    .param  land F v0, "this" land LandFG; land "game/weapon/Firearm;"
    .prologue
    const-float v0, 0.0f
    returnfloat v0
.end method

# Patched method - Infinite Ammo
.method public getAmmo()F
    .locals 2
    .param  land F v0, "this" land LandFG; land "game/weapon/Firearm;"
    .prologue
    # Return maximum ammo instead of 0
    const/4 v1, 0x7f
    float-to-int v1, v1
    int-to-float v1, v1
    returnfloat v1
.end method
```

### Sample Smali Patch for God Mode

```smali
# Original takeDamage method
.method public takeDamage(F)V
    .locals 3
    .param  land F v0, "damage" land F
    .param  land I v1, "source" land I
    .prologue
    # Subtract damage from health
    invoke-virtual {p0, v0, v1}, LandFG;->setHealth(F)V
    # Check if health <= 0
    invoke-virtual {p0}, LandFG;->isDead()Z
    ...
.end method

# Patched - God Mode
.method public takeDamage(F)V
    .locals 3
    .param  land F v0, "damage" land F
    .param  land I v1, "source" land I
    .prologue
    # God mode: ignore all damage
    # Skip the entire damage application
    return-void
.end method
```

## Mod Menu Features Mapping

| Feature | Smali Patch Target |
|---------|-------------------|
| Infinite Ammo | `getAmmo()` method return value |
| God Mode | `takeDamage()` method body |
| Unlimited Energy | `getEnergy()` method return value |
| No Ads | `AdMobInitialised` / `showAd()` methods |
| All Suits Unlocked | `suitIndex` or `unlockSuit()` methods |
| Speed Multiplier | Player movement velocity |
| Free Mode | Game state check methods |

## Testing

After installation:
1. Launch the game via the mod menu
2. Test each feature:
   - Fire weapon - should have unlimited ammo
   - Take damage - player should not die (God Mode)
   - Use abilities - energy should not decrease
   - Check suits - all should be unlocked
3. Adjust speed using the seekbar if available

## Known Issues

- May need signature verification bypass
- Some features may conflict with each other
- Game updates may break the mod
- Requires Android 5.1+ (as per game requirements)

## Credits

- OverPeak modding community
- APKTool developers
- Reverse engineering contributors