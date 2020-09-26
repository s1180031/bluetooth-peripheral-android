package com.github.s1180031.domain.di

import com.github.s1180031.domain.model.BleServiceMaker
import com.github.s1180031.domain.model.PeripheralManager
import com.github.s1180031.domain.model.PeripheralManagerImpl
import com.github.s1180031.domain.usecase.BleAdvertiseUseCase
import com.github.s1180031.domain.usecase.BleAdvertiseUseCaseImpl
import com.github.s1180031.domain.usecase.SelectBleTypeUseCase
import com.github.s1180031.domain.usecase.SelectBleTypeUseCaseImpl
import org.koin.dsl.module

val usecaseModule = module {
    factory { BleServiceMaker() }

    single<PeripheralManager> { PeripheralManagerImpl(get(), get()) }

    factory<SelectBleTypeUseCase> { SelectBleTypeUseCaseImpl() }
    factory<BleAdvertiseUseCase> { BleAdvertiseUseCaseImpl(get()) }
}
