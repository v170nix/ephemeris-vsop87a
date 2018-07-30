package net.arwix.astronomy2.ephemeris.vsop87a

import kotlinx.coroutines.experimental.CancellationException
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.isActive
import net.arwix.astronomy2.core.Ecliptic
import net.arwix.astronomy2.core.Heliocentric
import net.arwix.astronomy2.core.J2000
import net.arwix.astronomy2.core.ephemeris.coordinates.getCoroutineHeliocentricEclipticCoordinates
import net.arwix.astronomy2.core.ephemeris.coordinates.getHeliocentricEclipticCoordinates
import net.arwix.astronomy2.core.vector.RectangularVector
import kotlin.coroutines.experimental.coroutineContext
import kotlin.math.cos

fun createVsop87Coordinates(idVsop87Body: IdVsop87Body): getHeliocentricEclipticCoordinates {
    val data = when (idVsop87Body) {
        ID_VSOP87_SUN -> return { _ -> RectangularVector() }
        ID_VSOP87_EARTH -> EarthData
        else -> throw IndexOutOfBoundsException()
    }

    return { jT ->
        val jT10 = jT / 10.0
        RectangularVector(
                accumulate(data.X0, jT10)
                        + accumulate(data.X1, jT10) * jT10
                        + accumulate(data.X2, jT10) * jT10 * jT10
                        + accumulate(data.X3, jT10) * jT10 * jT10 * jT10
                        + accumulate(data.X4, jT10) * jT10 * jT10 * jT10 * jT10
                        + accumulate(data.X5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10,
                accumulate(data.Y0, jT10)
                        + accumulate(data.Y1, jT10) * jT10
                        + accumulate(data.Y2, jT10) * jT10 * jT10
                        + accumulate(data.Y3, jT10) * jT10 * jT10 * jT10
                        + accumulate(data.Y4, jT10) * jT10 * jT10 * jT10 * jT10
                        + accumulate(data.Y5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10,
                accumulate(data.Z0, jT10)
                        + accumulate(data.Z1, jT10) * jT10
                        + accumulate(data.Z2, jT10) * jT10 * jT10
                        + accumulate(data.Z3, jT10) * jT10 * jT10 * jT10
                        + accumulate(data.Z4, jT10) * jT10 * jT10 * jT10 * jT10
                        + accumulate(data.Z5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10)
    }
}

internal fun getVsopData(idVsop87Body: IdVsop87Body): VsopData =
        when (idVsop87Body) {
            ID_VSOP87_MERCURY -> MercuryData
            ID_VSOP87_VENUS -> VenusData
            ID_VSOP87_EARTH -> EarthData
            ID_VSOP87_EM_BARYCENTER -> EarthBarycenter
            ID_VSOP87_MARS -> MarsData
            ID_VSOP87_JUPITER -> JupiterData
            ID_VSOP87_SATURN -> SaturnData
            ID_VSOP87_URANUS -> UranusData
            ID_VSOP87_NEPTUNE -> NeptuneData
            else -> throw IndexOutOfBoundsException()
        }

@Heliocentric @Ecliptic @J2000
fun createCoroutineVsop87Coordinates(idVsop87Body: IdVsop87Body): getCoroutineHeliocentricEclipticCoordinates {
    val data: VsopData = if (idVsop87Body == ID_VSOP87_SUN) return  { _ -> RectangularVector() }
    else getVsopData(idVsop87Body)

    return { jT ->
        val jT10 = jT / 10.0
        val X0 = async { accumulate(data.X0, jT10) }
        val X1 = async { accumulate(data.X1, jT10) * jT10 }
        val X2 = async { accumulate(data.X2, jT10) * jT10 * jT10 }
        val X3 = async { accumulate(data.X3, jT10) * jT10 * jT10 * jT10 }
        val X4 = async { accumulate(data.X4, jT10) * jT10 * jT10 * jT10 * jT10 }
        val X5 = async { accumulate(data.X5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10 }

        val Y0 = async { accumulate(data.Y0, jT10) }
        val Y1 = async { accumulate(data.Y1, jT10) * jT10 }
        val Y2 = async { accumulate(data.Y2, jT10) * jT10 * jT10 }
        val Y3 = async { accumulate(data.Y3, jT10) * jT10 * jT10 * jT10 }
        val Y4 = async { accumulate(data.Y4, jT10) * jT10 * jT10 * jT10 * jT10 }
        val Y5 = async { accumulate(data.Y5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10 }

        val Z0 = async { accumulate(data.Z0, jT10) }
        val Z1 = async { accumulate(data.Z1, jT10) * jT10 }
        val Z2 = async { accumulate(data.Z2, jT10) * jT10 * jT10 }
        val Z3 = async { accumulate(data.Z3, jT10) * jT10 * jT10 * jT10 }
        val Z4 = async { accumulate(data.Z4, jT10) * jT10 * jT10 * jT10 * jT10 }
        val Z5 = async { accumulate(data.Z5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10 }

        val x = X0.await() + X1.await() + X2.await() + X3.await() + X4.await() + X5.await()

        if (!coroutineContext.isActive) throw CancellationException()

        val y = Y0.await() + Y1.await() + Y2.await() + Y3.await() + Y4.await() + Y5.await()

        if (!coroutineContext.isActive) throw CancellationException()

        val z = Z0.await() + Z1.await() + Z2.await() + Z3.await() + Z4.await() + Z5.await()

        RectangularVector(x, y, z)
    }
}

private inline fun accumulate(data: Array<DoubleArray>, jT10: Double) = data.fold(0.0) { acc, element ->
    acc + element[0] * cos(element[1] + element[2] * jT10)
}