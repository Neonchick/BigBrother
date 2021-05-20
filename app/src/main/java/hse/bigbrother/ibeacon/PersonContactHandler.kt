package hse.bigbrother.ibeacon

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import hse.bigbrother.bottomnavigation.contacts.ContactsRepository
import org.altbeacon.beacon.*
import javax.inject.Singleton

@Singleton
class PersonContactHandler(
    private val context: Context,
    private val contactsRepository: ContactsRepository
) : BeaconConsumer {

    private val beaconManager: BeaconManager = BeaconManager.getInstanceForApplication(context)

    init {
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(BEACON_LAYOUT))
    }

    private var listeners: MutableSet<PersonContactListener> = mutableSetOf()

    fun addListener(listener: PersonContactListener) {
        listeners.add(listener)
        beaconManager.bind(this)
    }

    fun deleteListener(listener: PersonContactListener) {
        listeners.remove(listener)
    }

    fun deleteListeners() {
        beaconManager.unbind(this)
        listeners.clear()
    }

    override fun onBeaconServiceConnect() {
        beaconManager.removeAllMonitorNotifiers()
        beaconManager.addRangeNotifier { beacons, _ ->
            if (beacons.isNotEmpty()) {
                listeners.forEach { listener -> listener.onRangeChanged(beacons) }
            }
        }

        beaconManager.startRangingBeaconsInRegion(
            Region(
                UNIQUE_ID,
                Identifier.parse(
                    contactsRepository.beaconInfo?.regionUUID ?: DEFAULT_REGION_UUID
                ),
                Identifier.parse(
                    contactsRepository.beaconInfo?.groupId ?: DEFAULT_GROUP_ID
                ),
                null
            )
        )
    }

    override fun getApplicationContext(): Context = context

    override fun unbindService(p0: ServiceConnection?) {}

    override fun bindService(p0: Intent?, p1: ServiceConnection?, p2: Int): Boolean = true

    companion object {

        private const val DEFAULT_REGION_UUID = "2f234454-cf6d-4a0f-adf2-f4911ba9ffa6"
        private const val DEFAULT_GROUP_ID = "1"
        private const val BEACON_LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
        private const val UNIQUE_ID = "BigBrotherUniqueId"

    }

}