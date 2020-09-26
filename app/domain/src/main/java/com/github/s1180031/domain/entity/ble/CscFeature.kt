package com.github.s1180031.domain.entity.ble

internal enum class CscFeature(private val value: Byte) {
    SPEED(0x01),
    CADENCE(0x02),
    MULTIPLE(0x03);

    val data: ByteArray get() = byteArrayOf(value)
}
