package com.github.s1180031.domain.entity.ble

import java.util.UUID

internal enum class BLECharacteristic(private val assignNumber: String) {
    CSC_MEASUREMENT("2A5B"),
    CSC_FEATURE("2A5C"),
    SENSOR_LOCATION("2A5D"),
    SC_CONTROL_POINT("2A55"),
    CLIENT_CHARACTERISTIC_CONFIGURATION("2902");

    val uuid: UUID get() = UUID.fromString("0000$assignNumber-0000-1000-8000-00805f9b34fb")

    companion object {
        fun from(uuid: UUID) = values().find { it.uuid == uuid }
    }
}
