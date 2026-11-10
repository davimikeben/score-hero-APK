package com.rusergames.overpeak.mod

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.view.*
import kotlinx.coroutines.*

// Mod Menu for OverPeak v0.71
// Features: Infinite Ammo, God Mode, Unlimited Energy, etc.
// NOTE: For IL2CPP games, actual patching requires IL2CPP tools (Il2CppDumper/Il2CppPatcher)
// This menu provides UI and applies available patches where possible

public class ModMenuActivity : Activity() {

    // Mod state flags
    private var infiniteAmmo: Boolean = false
    private var godMode: Boolean = false
    private var unlimitedEnergy: Boolean = false
    private var noAds: Boolean = false
    private var allSuitsUnlocked: Boolean = false
    private var speedMultiplier: Float = 1.0f

    // UI Elements
    private var chkInfiniteAmmo: CheckBox? = null
    private var chkGodMode: CheckBox? = null
    private var chkUnlimitedEnergy: CheckBox? = null
    private var chkNoAds: CheckBox? = null
    private var chkAllSuits: CheckBox? = null
    private var txtSpeed: SeekBar? = null
    private var txtSpeedValue: TextView? = null

    private static final String TAG = "ModMenuActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mod_menu)

        // Initialize UI
        chkInfiniteAmmo = findViewById(R.id.chk_infinite_ammo)
        chkGodMode = findViewById(R.id.chk_god_mode)
        chkUnlimitedEnergy = findViewById(R.id.chk_unlimited_energy)
        chkNoAds = findViewById(R.id.chk_no_ads)
        chkAllSuits = findViewById(R.id.chk_all_suits)
        txtSpeed = findViewById(R.id.seekbar_speed)
        txtSpeedValue = findViewById(R.id.text_speed_value)

        // Set default states from shared preferences
        loadModSettings()

        // Set checkbox listeners
        chkInfiniteAmmo?.setOnCheckedChangeListener { buttonView, isChecked ->
            infiniteAmmo = isChecked
            applyInfiniteAmmoPatch(isChecked)
            saveModSettings()
            Log.d(TAG, "Infinite Ammo: ${if (infiniteAmmo) "ON" else "OFF"}")
        }

        chkGodMode?.setOnCheckedChangeListener { buttonView, isChecked ->
            godMode = isChecked
            applyGodModePatch(isChecked)
            saveModSettings()
            Log.d(TAG, "God Mode: ${if (godMode) "ON" else "OFF"}")
        }

        chkUnlimitedEnergy?.setOnCheckedChangeListener { buttonView, isChecked ->
            unlimitedEnergy = isChecked
            applyUnlimitedEnergyPatch(isChecked)
            saveModSettings()
            Log.d(TAG, "Unlimited Energy: ${if (unlimitedEnergy) "ON" else "OFF"}")
        }

        chkNoAds?.setOnCheckedChangeListener { buttonView, isChecked ->
            noAds = isChecked
            applyNoAdsPatch(isChecked)
            saveModSettings()
            Log.d(TAG, "No Ads: ${if (noAds) "ON" else "OFF"}")
        }

        chkAllSuits?.setOnCheckedChangeListener { buttonView, isChecked ->
            allSuitsUnlocked = isChecked
            applyAllSuitsPatch(isChecked)
            saveModSettings()
            Log.d(TAG, "All Suits: ${if (allSuitsUnlocked) "ON" else "OFF"}")
        }

        // Speed multiplier seekbar
        txtSpeed?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    speedMultiplier = progress / 10.0f
                    txtSpeedValue?.text = "${speedMultiplier}x"
                    applySpeedPatch(speedMultiplier)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set initial states
        updateUIStates()
    }

    private fun loadModSettings() {
        val prefs = getSharedPreferences("overpeak_mod", MODE_PRIVATE)
        infiniteAmmo = prefs.getBoolean("infinite_ammo", false)
        godMode = prefs.getBoolean("god_mode", false)
        unlimitedEnergy = prefs.getBoolean("unlimited_energy", false)
        noAds = prefs.getBoolean("no_ads", false)
        allSuitsUnlocked = prefs.getBoolean("all_suits_unlocked", false)
        speedMultiplier = prefs.getFloat("speed_multiplier", 1.0f)
    }

    private fun saveModSettings() {
        val prefs = getSharedPreferences("overpeak_mod", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("infinite_ammo", infiniteAmmo)
        editor.putBoolean("god_mode", godMode)
        editor.putBoolean("unlimited_energy", unlimitedEnergy)
        editor.putBoolean("no_ads", noAds)
        editor.putBoolean("all_suits_unlocked", allSuitsUnlocked)
        editor.putFloat("speed_multiplier", speedMultiplier)
        editor.apply()
    }

    private fun updateUIStates() {
        chkInfiniteAmmo?.isChecked = infiniteAmmo
        chkGodMode?.isChecked = godMode
        chkUnlimitedEnergy?.isChecked = unlimitedEnergy
        chkNoAds?.isChecked = noAds
        chkAllSuits?.isChecked = allSuitsUnlocked
        txtSpeed?.progress = (speedMultiplier * 10).toInt()
        txtSpeedValue?.text = "${speedMultiplier}x"
    }

    // ==========================================
    // PATCH APPLICATION METHODS
    // ==========================================

    private fun applyInfiniteAmmoPatch(enabled: Boolean) {
        if (enabled) {
            // For IL2CPP games, this would require IL2CPP patching
            // For traditional Android games, this would patch smali
            // Currently logs status - actual patching depends on game architecture
            Log.d(TAG, "Infinite Ammo patch applied (status only - IL2CPP requires separate patching)")
        } else {
            Log.d(TAG, "Infinite Ammo patch removed")
        }
    }

    private fun applyGodModePatch(enabled: Boolean) {
        if (enabled) {
            Log.d(TAG, "God Mode patch applied (status only)")
        } else {
            Log.d(TAG, "God Mode patch removed")
        }
    }

    private fun applyUnlimitedEnergyPatch(enabled: Boolean) {
        if (enabled) {
            Log.d(TAG, "Unlimited Energy patch applied (status only)")
        } else {
            Log.d(TAG, "Unlimited Energy patch removed")
        }
    }

    private fun applyNoAdsPatch(enabled: Boolean) {
        if (enabled) {
            Log.d(TAG, "No Ads patch applied (status only)")
        } else {
            Log.d(TAG, "No Ads patch removed")
        }
    }

    private fun applyAllSuitsPatch(enabled: Boolean) {
        if (enabled) {
            Log.d(TAG, "All Suits Unlocked patch applied (status only)")
        } else {
            Log.d(TAG, "All Suits Unlocked patch removed")
        }
    }

    private fun applySpeedPatch(multiplier: Float) {
        Log.d(TAG, "Speed set to: ${multiplier}x")
    }

    // Override onBackPressed to show mod status
    override fun onBackPressed() {
        val status = "Mod Menu Status:\n" +
            "Infinite Ammo: ${if (infiniteAmmo) "ON" else "OFF"}\n" +
            "God Mode: ${if (godMode) "ON" else "OFF"}\n" +
            "Unlimited Energy: ${if (unlimitedEnergy) "ON" else "OFF"}\n" +
            "No Ads: ${if (noAds) "ON" else "OFF"}\n" +
            "Speed: ${speedMultiplier}x"
        Toast.makeText(this, status, Toast.LENGTH_LONG).show()
        super.onBackPressed()
    }
}
