package net.arwix.astronomy2.ephemeris.vsop87a

abstract class VsopData {
    internal abstract val X0: Array<DoubleArray>
    internal abstract val X1: Array<DoubleArray>
    internal abstract val X2: Array<DoubleArray>
    internal abstract val X3: Array<DoubleArray>
    internal abstract val X4: Array<DoubleArray>
    internal abstract val X5: Array<DoubleArray>

    internal abstract val Y0: Array<DoubleArray>
    internal abstract val Y1: Array<DoubleArray>
    internal abstract val Y2: Array<DoubleArray>
    internal abstract val Y3: Array<DoubleArray>
    internal abstract val Y4: Array<DoubleArray>
    internal abstract val Y5: Array<DoubleArray>

    internal abstract val Z0: Array<DoubleArray>
    internal abstract val Z1: Array<DoubleArray>
    internal abstract val Z2: Array<DoubleArray>
    internal abstract val Z3: Array<DoubleArray>
    internal abstract val Z4: Array<DoubleArray>
    internal abstract val Z5: Array<DoubleArray>
}

//abstract class VsopData(private val epoch: Epoch, private val obj: VsopObject) : EclipticCoordinates<VsopObject> {
//
//    override fun getIdObject() = obj
//
//    override fun getEpoch(): Epoch = epoch
//
//    override fun getEclipticCoordinates(T: Double): Vector {
//        val innerT = T / 10.0
//        return RectangularVector(X0(innerT) + X1(innerT) + X2(innerT) + X3(innerT) + X4(innerT) + X5(innerT),
//                Y0(innerT) + Y1(innerT) + Y2(innerT) + Y3(innerT) + Y4(innerT) + Y5(innerT),
//                Z0(innerT) + Z1(innerT) + Z2(innerT) + Z3(innerT) + Z4(innerT) + Z5(innerT))
//    }
//
//    abstract protected fun X0(T: Double): Double
//    abstract protected fun X1(T: Double): Double
//    abstract protected fun X2(T: Double): Double
//    abstract protected fun X3(T: Double): Double
//    abstract protected fun X4(T: Double): Double
//    abstract protected fun X5(T: Double): Double
//
//    abstract protected fun Y0(T: Double): Double
//    abstract protected fun Y1(T: Double): Double
//    abstract protected fun Y2(T: Double): Double
//    abstract protected fun Y3(T: Double): Double
//    abstract protected fun Y4(T: Double): Double
//    abstract protected fun Y5(T: Double): Double
//
//    abstract protected fun Z0(T: Double): Double
//    abstract protected fun Z1(T: Double): Double
//    abstract protected fun Z2(T: Double): Double
//    abstract protected fun Z3(T: Double): Double
//    abstract protected fun Z4(T: Double): Double
//    abstract protected fun Z5(T: Double): Double
//}