package com.github.s1180031.domain.usecase

import android.util.Log
import com.github.s1180031.domain.entity.ble.BLECharacteristic
import com.github.s1180031.domain.entity.ble.CscFeature
import com.github.s1180031.domain.extension.toByteArray
import com.github.s1180031.domain.model.BleType
import com.github.s1180031.domain.model.BluetoothState
import com.github.s1180031.domain.model.PeripheralManager
import com.github.s1180031.domain.model.PeripheralManagerCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface BleAdvertiseUseCase {
    var speedRevolution: Long
    fun startAdvertise(bleType: BleType)
}

internal class BleAdvertiseUseCaseImpl(
    private val peripheralManager: PeripheralManager
) : BleAdvertiseUseCase {
    override var speedRevolution: Long = 0

    private var time: Long = 0

    override fun startAdvertise(bleType: BleType) {
        peripheralManager.callback = object : PeripheralManagerCallback {
            override fun onConnectionStateChange(state: BluetoothState) {
                Log.e(TAG, state.toString())
            }

            override fun onReadRequest(characteristic: BLECharacteristic): ByteArray? {
                return when (characteristic) {
                    BLECharacteristic.CSC_FEATURE -> CscFeature.SPEED.data
                    else -> null
                }
            }

            override fun onNotifyChangeRequest(
                characteristic: BLECharacteristic,
                isEnabled: Boolean
            ) {
                GlobalScope.launch(Dispatchers.IO) {
                    var counter = 0
                    while (true) {
                        delay(1000)
                        counter++
                        speedRevolution += 5
                        speedRevolution
                        time += 1024
                        peripheralManager.requestNotify(
                            BLECharacteristic.CSC_MEASUREMENT,
                            getSpeedValue(speedRevolution, time)
                        )
                    }
                }
            }
        }
        peripheralManager.advertise(bleType)
    }

    private fun getSpeedValue(revolution: Long, time: Long): ByteArray {
        return revolution.toInt().toByteArray() + time.toShort().toByteArray()
    }

    companion object {
        private const val TAG = "BleAdvertiseUseCase"
    }
}
