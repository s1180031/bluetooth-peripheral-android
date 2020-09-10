package com.github.s1180031.domain.usecase

import com.github.s1180031.domain.model.BleType

interface SelectBleTypeUseCase {
    suspend fun getBleTypes(): List<BleType>
}

internal class SelectBleTypeUseCaseImpl : SelectBleTypeUseCase {
    override suspend fun getBleTypes(): List<BleType> {
        return BleType.values().toList()
    }
}
