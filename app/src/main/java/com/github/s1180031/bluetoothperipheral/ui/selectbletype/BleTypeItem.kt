package com.github.s1180031.bluetoothperipheral.ui.selectbletype

import com.github.s1180031.domain.model.BleType

data class BleTypeItem(val bleType: BleType) {
    val text: String
        get() = when (bleType) {
            BleType.CYCLING_SPEED_AND_CADENCE -> "Cycling Speed and Cadence"
            BleType.CYCLING_POWER -> "Cycling Power"
        }
}
