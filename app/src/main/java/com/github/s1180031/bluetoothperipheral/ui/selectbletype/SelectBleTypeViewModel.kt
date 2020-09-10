package com.github.s1180031.bluetoothperipheral.ui.selectbletype

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.s1180031.domain.usecase.SelectBleTypeUseCase
import kotlinx.coroutines.launch
import org.koin.core.Koin

class SelectBleTypeViewModel(
    private val usecase: SelectBleTypeUseCase
) : ViewModel() {

    //region public property
    var router: SelectBleTypeRouter? = null
    val bleTypesLiveData: LiveData<List<BleTypeItem>> get() = bleTypes
    //endregion

    private val bleTypes = MutableLiveData<List<BleTypeItem>>()

    init {
        bleTypes.value = emptyList()
    }

    fun onCreate() {
        viewModelScope.launch {
            bleTypes.value = usecase.getBleTypes().map { BleTypeItem(it) }
        }
    }

    fun onItemSelected(item: BleTypeItem) {
        router?.goToSettingScreen(item.bleType)
    }

    class Factory(private val koin: Koin) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SelectBleTypeViewModel(koin.get()) as T
        }
    }
}
