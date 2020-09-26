package com.github.s1180031.domain.model

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import com.github.s1180031.domain.entity.ble.BLECharacteristic
import com.github.s1180031.domain.entity.ble.BLEService
import com.github.s1180031.domain.extension.addCccd

internal class BleServiceMaker {
    fun make(bleType: BleType): BluetoothGattService = when(bleType) {
        BleType.CYCLING_SPEED_AND_CADENCE -> makeCscService()
    }

    private fun makeCscService(): BluetoothGattService {
        val bluetoothGattService = BluetoothGattService(
            BLEService.CYCLING_SPEED_AND_CADENCE.uuid,
            BluetoothGattService.SERVICE_TYPE_PRIMARY
        )
        val cscFeatureCharacteristic = BluetoothGattCharacteristic(
            BLECharacteristic.CSC_FEATURE.uuid,
            BluetoothGattCharacteristic.PROPERTY_READ,
            BluetoothGattCharacteristic.PERMISSION_READ
        )
        val cscMeasureCharacteristic = BluetoothGattCharacteristic(
            BLECharacteristic.CSC_MEASUREMENT.uuid,
            BluetoothGattCharacteristic.PROPERTY_NOTIFY,
            BluetoothGattCharacteristic.PERMISSION_READ
        ).also { it.addCccd() }
        val scControlPoint = BluetoothGattCharacteristic(
            BLECharacteristic.SC_CONTROL_POINT.uuid,
            BluetoothGattCharacteristic.PROPERTY_INDICATE or BluetoothGattCharacteristic.PROPERTY_WRITE,
            BluetoothGattDescriptor.PERMISSION_WRITE or BluetoothGattDescriptor.PERMISSION_READ
        ).also { it.addCccd() }
        val sensorLocation = BluetoothGattCharacteristic(
            BLECharacteristic.SENSOR_LOCATION.uuid,
            BluetoothGattCharacteristic.PROPERTY_READ,
            BluetoothGattDescriptor.PERMISSION_READ
        )

        bluetoothGattService.addCharacteristic(cscFeatureCharacteristic)
        bluetoothGattService.addCharacteristic(scControlPoint)
        bluetoothGattService.addCharacteristic(sensorLocation)
        bluetoothGattService.addCharacteristic(cscMeasureCharacteristic)
        return bluetoothGattService
    }
}