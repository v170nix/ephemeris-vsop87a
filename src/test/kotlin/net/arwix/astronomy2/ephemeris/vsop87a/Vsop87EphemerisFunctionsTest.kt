package net.arwix.astronomy2.ephemeris.vsop87a

import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Vsop87EphemerisFunctionsTest {

    @ParameterizedTest
    @MethodSource("getData")
    fun `Coordinates`(data: Data) {
        runBlocking {
            val vector = createSuspendedVsop87ACoordinates(data.id).invoke(data.jT).also {
                System.out.println(it)
            }
            assertArrayEquals(data.vector, vector.toArray())
        }
    }

    internal data class Data(val id: Int, val jT: Double, val vector: DoubleArray)

    fun getData() = listOf(
            Data(ID_VSOP87_MERCURY, 0.5, doubleArrayOf(-0.17951421009745386, 0.2678116610871014, 0.038348376780681856)),
            Data(ID_VSOP87_VENUS, 0.5, doubleArrayOf(0.1417824775000777, -0.7133841642050578, -0.01802581474852584)),
            Data(ID_VSOP87_EARTH, 0.5, doubleArrayOf(-0.1716125006836458, 0.9682587683227163, -1.0977609826272948E-4)),
            Data(ID_VSOP87_EM_BARYCENTER, 0.5, doubleArrayOf(-0.171583296518176, 0.9682682377875933, -1.0795861692371957E-4)),
            Data(ID_VSOP87_MARS, 0.5, doubleArrayOf(-1.5432315120596511, -0.5035899853317461, 0.027202004460763252)),
            Data(ID_VSOP87_JUPITER, 0.5, doubleArrayOf(-2.3910546127650303, 4.664070272032234, 0.033966714165738876)),
            Data(ID_VSOP87_SATURN, 0.5, doubleArrayOf(4.766239751052515, -8.7737132940204, -0.037879624876213304)),
            Data(ID_VSOP87_URANUS, 0.5, doubleArrayOf(-17.823171829534186, 4.071512823576951, 0.2458509882743705)),
            Data(ID_VSOP87_NEPTUNE, 0.5, doubleArrayOf(17.397928561207056, 24.197927112419535, -0.8992271522836394))

    )

}