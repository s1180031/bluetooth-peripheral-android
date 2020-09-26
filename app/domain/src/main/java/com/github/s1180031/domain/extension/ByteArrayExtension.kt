package com.github.s1180031.domain.extension

fun Int.toByteArray(): ByteArray {
    var value = this
    return ByteArray(4) {
        val byte = value and 0xFF
        value = value shr 8
        return@ByteArray byte.toByte()
    }
}

fun Short.toByteArray(): ByteArray {
    var value = this.toInt()
    return ByteArray(2) {
        val byte = value and 0xFF
        value = value shr 8
        return@ByteArray byte.toByte()
    }
}
