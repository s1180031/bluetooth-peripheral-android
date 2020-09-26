package com.github.s1180031.domain.entity.ble

import java.util.UUID

internal enum class BLEService(private val uuidString: String) {
    CYCLING_SPEED_AND_CADENCE("00001816-0000-1000-8000-00805f9b34fb"),
    BATTERY_INFORMATION("0000180f-0000-1000-8000-00805f9b34fb");

    val uuid: UUID get() = UUID.fromString(uuidString)
}
