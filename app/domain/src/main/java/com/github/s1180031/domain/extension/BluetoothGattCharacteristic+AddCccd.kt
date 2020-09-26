package com.github.s1180031.domain.extension

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.github.s1180031.domain.entity.ble.BLECharacteristic

internal fun BluetoothGattCharacteristic.addCccd() {
    val dataDescriptor = BluetoothGattDescriptor(
        BLECharacteristic.CLIENT_CHARACTERISTIC_CONFIGURATION.uuid,
        BluetoothGattDescriptor.PERMISSION_WRITE or BluetoothGattDescriptor.PERMISSION_READ
    )
    addDescriptor(dataDescriptor)
}
