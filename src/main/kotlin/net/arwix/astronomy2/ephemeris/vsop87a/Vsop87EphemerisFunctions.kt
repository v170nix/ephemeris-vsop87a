package net.arwix.astronomy2.ephemeris.vsop87a

import kotlinx.coroutines.*
import net.arwix.astronomy2.core.Ecliptic
import net.arwix.astronomy2.core.Heliocentric
import net.arwix.astronomy2.core.J2000
import net.arwix.astronomy2.core.JT
import net.arwix.astronomy2.core.vector.RectangularVector
import net.arwix.astronomy2.core.vector.Vector
import kotlin.math.cos


@Heliocentric
@Ecliptic
@J2000
suspend fun getVsop87ACoordinates(data: VsopData, jT: JT): Vector = coroutineScope {
//    if (idVsop87Body == ID_VSOP87_SUN) return { RectangularVector() }
//    else getVsopData(idVsop87Body)


    val jT10 = jT / 10.0
    val X0 = async(Dispatchers.Default) { accumulate(data.X0, jT10) }
    val X1 = async(Dispatchers.Default) { accumulate(data.X1, jT10) * jT10 }
    val X2 = async(Dispatchers.Default) { accumulate(data.X2, jT10) * jT10 * jT10 }
    val X3 = async(Dispatchers.Default) { accumulate(data.X3, jT10) * jT10 * jT10 * jT10 }
    val X4 = async(Dispatchers.Default) { accumulate(data.X4, jT10) * jT10 * jT10 * jT10 * jT10 }
    val X5 = async(Dispatchers.Default) { accumulate(data.X5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10 }

    val x = X0.await() + X1.await() + X2.await() + X3.await() + X4.await() + X5.await()
    if (!coroutineContext.isActive) throw CancellationException()

    val Y0 = async(Dispatchers.Default) { accumulate(data.Y0, jT10) }
    val Y1 = async(Dispatchers.Default) { accumulate(data.Y1, jT10) * jT10 }
    val Y2 = async(Dispatchers.Default) { accumulate(data.Y2, jT10) * jT10 * jT10 }
    val Y3 = async(Dispatchers.Default) { accumulate(data.Y3, jT10) * jT10 * jT10 * jT10 }
    val Y4 = async(Dispatchers.Default) { accumulate(data.Y4, jT10) * jT10 * jT10 * jT10 * jT10 }
    val Y5 = async(Dispatchers.Default) { accumulate(data.Y5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10 }

    val y = Y0.await() + Y1.await() + Y2.await() + Y3.await() + Y4.await() + Y5.await()
    if (!coroutineContext.isActive) throw CancellationException()

    val Z0 = async(Dispatchers.Default) { accumulate(data.Z0, jT10) }
    val Z1 = async(Dispatchers.Default) { accumulate(data.Z1, jT10) * jT10 }
    val Z2 = async(Dispatchers.Default) { accumulate(data.Z2, jT10) * jT10 * jT10 }
    val Z3 = async(Dispatchers.Default) { accumulate(data.Z3, jT10) * jT10 * jT10 * jT10 }
    val Z4 = async(Dispatchers.Default) { accumulate(data.Z4, jT10) * jT10 * jT10 * jT10 * jT10 }
    val Z5 = async(Dispatchers.Default) { accumulate(data.Z5, jT10) * jT10 * jT10 * jT10 * jT10 * jT10 }

    val z = Z0.await() + Z1.await() + Z2.await() + Z3.await() + Z4.await() + Z5.await()

    RectangularVector(x, y, z)
}


private fun accumulate(data: Array<DoubleArray>, jT10: Double) = data.fold(0.0) { acc, element ->
    acc + element[0] * cos(element[1] + element[2] * jT10)
}