package net.arwix.astronomy2.ephemeris.vsop87a

import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Vsop87EphemerisFunctionsTest {

    @Test
    fun createCoroutineVsop87Coordinates() {
        runBlocking {
            createCoroutineVsop87Coordinates(ID_VSOP87_EARTH).invoke(0.5).let {
                System.out.println(it)
            }
        }
    }
}