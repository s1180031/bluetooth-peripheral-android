package com.github.s1180031.bluetoothperipheral.ui.bleadvertise

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.s1180031.domain.model.BleType
import com.github.s1180031.domain.usecase.BleAdvertiseUseCase
import org.koin.core.Koin

class BleAdvertiseFragmentViewModel(
    private val bleType: BleType,
    private val useCase: BleAdvertiseUseCase
) : ViewModel() {
    val advertiseStateLiveData get() = advertiseState
    private val advertiseState: MutableLiveData<String> = MutableLiveData("")

    fun onCreate() {
        Log.d(TAG, "bleType: $bleType")
        useCase.startAdvertise(bleType)
    }

    companion object {
        private const val TAG = "BleAdvertiseFragmentViewModel"
    }

    class Factory(private val bleType: BleType, private val koin: Koin) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BleAdvertiseFragmentViewModel(bleType, koin.get()) as T
        }
    }
}
