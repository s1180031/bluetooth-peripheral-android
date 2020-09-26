package com.github.s1180031.domain.model

import android.bluetooth.BluetoothGatt

enum class BluetoothState(private val rawState: Int) {
    DISCONNECTED(BluetoothGatt.STATE_DISCONNECTED),
    DISCONNECTING(BluetoothGatt.STATE_DISCONNECTING),
    CONNECTING(BluetoothGatt.STATE_CONNECTING),
    CONNECTED(BluetoothGatt.STATE_CONNECTED);

    companion object {
        internal fun fromState(state: Int): BluetoothState {
            return values().find { it.rawState == state } ?: DISCONNECTED
        }
    }
}
