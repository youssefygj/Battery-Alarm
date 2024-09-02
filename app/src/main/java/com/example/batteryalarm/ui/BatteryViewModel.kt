package com.example.batteryalarm.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batteryalarm.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BatteryViewModel(private val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(0) // Replace 0 with a default drawable resource ID
    val uiState: StateFlow<Int> = _uiState.asStateFlow()

    init {
        monitorBatteryStatus()
    }

    private fun monitorBatteryStatus() {
        val batteryStatusReceiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
                val batteryPct = if (scale > 0) level / scale.toFloat() else -1f

                if (batteryPct >= 0) {  // Ensure valid battery percentage
                    viewModelScope.launch {
                        _uiState.update {
                            if (batteryPct <= 0.20f) {
                                // Set to low battery icon
                                R.drawable.battery_low
                            } else {
                                // Set to full battery icon
                                R.drawable.battery_full
                            }
                        }
                    }
                }
            }
        }

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryStatusReceiver, intentFilter)
    }

}
