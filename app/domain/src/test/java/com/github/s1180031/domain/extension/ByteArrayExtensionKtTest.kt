package com.github.s1180031.domain.extension

import org.junit.Assert
import org.junit.Test

class ByteArrayExtensionKtTest {

    @Test
    fun int型をByteArrayに変換するテスト() {
        val int = 0x12345678
        val byteArray = int.toByteArray()
        Assert.assertEquals(4, byteArray.count())
        Assert.assertEquals(0x78.toByte(), byteArray[0])
        Assert.assertEquals(0x56.toByte(), byteArray[1])
        Assert.assertEquals(0x34.toByte(), byteArray[2])
        Assert.assertEquals(0x12.toByte(), byteArray[3])
    }

    @Test
    fun short型をByteArrayに変換するテスト() {
        val int = 0x1234.toShort()
        val byteArray = int.toByteArray()
        Assert.assertEquals(2, byteArray.count())
        Assert.assertEquals(0x34.toByte(), byteArray[0])
        Assert.assertEquals(0x12.toByte(), byteArray[1])
    }
}
