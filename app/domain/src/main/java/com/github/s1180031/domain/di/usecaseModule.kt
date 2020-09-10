package com.github.s1180031.domain.di

import com.github.s1180031.domain.usecase.SelectBleTypeUseCase
import com.github.s1180031.domain.usecase.SelectBleTypeUseCaseImpl
import org.koin.dsl.module

val usecaseModule = module {
    factory<SelectBleTypeUseCase> { SelectBleTypeUseCaseImpl() }
}
