package com.github.s1180031.domain.model

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import com.github.s1180031.domain.entity.ble.BLECharacteristic
import com.github.s1180031.domain.entity.ble.BLEService

internal interface PeripheralManagerCallback {
    fun onConnectionStateChange(state: BluetoothState)
    fun onReadRequest(characteristic: BLECharacteristic): ByteArray?
    fun onNotifyChangeRequest(characteristic: BLECharacteristic, isEnabled: Boolean)
}

internal interface PeripheralManager {
    var callback: PeripheralManagerCallback?
    fun advertise(bleType: BleType)
    fun stop()
    fun requestNotify(characteristicType: BLECharacteristic, value: ByteArray)
}

internal class PeripheralManagerImpl(
    private val context: Context,
    private val serviceMaker: BleServiceMaker
) : PeripheralManager {
    override var callback: PeripheralManagerCallback? = null
    private val manager = context.getSystemService(BluetoothManager::class.java)
    private var server: BluetoothGattServer? = null
    private var device: BluetoothDevice? = null
    private var characteristics: List<BluetoothGattCharacteristic> = emptyList()

    private val serverCallback = object : BluetoothGattServerCallback() {
        override fun onConnectionStateChange(
            device: BluetoothDevice,
            status: Int,
            newState: Int
        ) {
            callback?.onConnectionStateChange(BluetoothState.fromState(newState))
        }

        override fun onCharacteristicReadRequest(
            device: BluetoothDevice,
            requestId: Int,
            offset: Int,
            characteristic: BluetoothGattCharacteristic
        ) {
            val server = server ?: return
            val callback = callback
            val uuid = BLECharacteristic.from(characteristic.uuid)
            if (uuid == null || callback == null) {
                server.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, offset, byteArrayOf())
                return
            }
            val value = callback.onReadRequest(uuid)
            if (value != null) {
                server.sendResponse(
                    device,
                    requestId,
                    BluetoothGatt.GATT_SUCCESS,
                    offset,
                    value
                )
            } else {
                server.sendResponse(device, requestId, BluetoothGatt.GATT_FAILURE, offset, byteArrayOf())
            }
        }

        override fun onDescriptorWriteRequest(
            device: BluetoothDevice,
            requestId: Int,
            descriptor: BluetoothGattDescriptor,
            preparedWrite: Boolean,
            responseNeeded: Boolean,
            offset: Int,
            value: ByteArray
        ) {
            this@PeripheralManagerImpl.device = device
            val isEnabled = value.any { it != 0x00.toByte() }
            BLECharacteristic.from(descriptor.characteristic.uuid)?.let {
                callback?.onNotifyChangeRequest(it, isEnabled)
            }
            if (descriptor.uuid == BLECharacteristic.CLIENT_CHARACTERISTIC_CONFIGURATION.uuid) {
                server?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, null)
            }
        }
    }

    override fun advertise(bleType: BleType) {
        server = manager.openGattServer(context, serverCallback)

        val service = when (bleType) {
            BleType.CYCLING_SPEED_AND_CADENCE -> serviceMaker.make(bleType)
        }
        characteristics = service.characteristics.toList()

        server?.addService(service)
        val advertiseData = AdvertiseData.Builder()
            .setIncludeTxPowerLevel(true)
            .addServiceUuid(ParcelUuid.fromString(BLEService.CYCLING_SPEED_AND_CADENCE.uuid.toString()))
            .build()

        val setting = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
            .setTimeout(10_000)
            .setConnectable(true)
            .build()

        val respBuilder = AdvertiseData.Builder()
            .setIncludeDeviceName(true)
            .build()

        manager.adapter.bluetoothLeAdvertiser.startAdvertising(
            setting,
            advertiseData,
            respBuilder,
            object : AdvertiseCallback() {
                override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                    Log.d(TAG, "onStartSuccess")
                }

                override fun onStartFailure(errorCode: Int) {
                    Log.d(TAG, "onStartFailure")
                }
            }
        )
    }

    override fun stop() {
        server?.close()
    }

    override fun requestNotify(characteristicType: BLECharacteristic, value: ByteArray) {
        val device = device ?: return
        val characteristic = characteristics.find { it.uuid == characteristicType.uuid } ?: return
        characteristic.value = value
        server?.notifyCharacteristicChanged(
            device,
            characteristic,
            true
        )
    }

    companion object {
        private const val TAG = "BluetoothService"
    }
}
