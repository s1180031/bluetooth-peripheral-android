package com.github.s1180031.bluetoothperipheral

import android.app.Application
import com.github.s1180031.domain.di.usecaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BluetoothApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BluetoothApplication)
            modules(usecaseModule)
        }
    }
}
