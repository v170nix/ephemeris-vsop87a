package net.arwix.astronomy2.ephemeris.vsop87a

//typealias IdVsop87Body = Int

//const val ID_VSOP87_SUN: IdVsop87Body = 0
//const val ID_VSOP87_MERCURY: IdVsop87Body = 1
//const val ID_VSOP87_VENUS: IdVsop87Body = 2
//const val ID_VSOP87_EARTH: IdVsop87Body = 3
//const val ID_VSOP87_EM_BARYCENTER: IdVsop87Body = 32
//const val ID_VSOP87_MARS: IdVsop87Body = 4
//const val ID_VSOP87_JUPITER: IdVsop87Body = 5
//const val ID_VSOP87_SATURN: IdVsop87Body = 6
//const val ID_VSOP87_URANUS: IdVsop87Body = 7
//const val ID_VSOP87_NEPTUNE: IdVsop87Body = 8

//enum class Vsop87ABody(private val vsopDataLazy: Lazy<VsopData>) {
//    Mercury(lazy { Vsop87AMercuryData }),
//    Venus(lazy { Vsop87AVenusData }),
//    Earth(lazy { Vsop87AEarthData }),
//    EarthBarycenter(lazy { Vsop87AEarthBarycenterData }),
//    Mars(lazy { Vsop87AMarsData }),
//    Jupiter(lazy { Vsop87AJupiterData }),
//    Saturn(lazy { Vsop87ASaturnData }),
//    Uranus(lazy { Vsop87AUranusData }),
//    Neptune(lazy { Vsop87ANeptuneData });
//
//    internal fun getData() = this.vsopDataLazy.value
//
//
//
//}