package hse.bigbrother.ibeacon

import org.altbeacon.beacon.Beacon

interface PersonContactListener {

    fun onRangeChanged(beacons: Collection<Beacon>)

}