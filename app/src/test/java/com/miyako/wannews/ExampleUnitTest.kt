package com.miyako.wannews

import com.miyako.wannews.util.InputUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("test")
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testIpv6() {
        val ipv6 = "2001::001:245";
        val gatway = "2001::2001:234";
        val mask = 64;
        if (InputUtils.verifyIPv6(ipv6)) {
            val res = InputUtils.isValidIPv6Head(ipv6)
            if (res) {
                println("合法ipv6")
                InputUtils.verifyIPv6AndGateway(ipv6, gatway, mask)
            } else {
                println("非法ipv6")
            }
        } else {
            println("error")
        }

    }
}