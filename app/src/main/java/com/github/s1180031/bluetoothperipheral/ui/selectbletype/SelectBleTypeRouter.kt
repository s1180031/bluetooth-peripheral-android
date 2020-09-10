package com.github.s1180031.bluetoothperipheral.ui.selectbletype

import androidx.fragment.app.Fragment
import com.github.s1180031.domain.model.BleType

interface SelectBleTypeRouter {
    fun goToSettingScreen(bleType: BleType)
}

class SelectBleTypeRouterImpl(fragment: Fragment) : Router(fragment), SelectBleTypeRouter {
    override fun goToSettingScreen(bleType: BleType) {
        val navController = navController ?: return
    }
}
