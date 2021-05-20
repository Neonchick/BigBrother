package hse.bigbrother.ibeacon

import android.content.Context
import hse.bigbrother.bottomnavigation.contacts.ContactsRepository
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.BeaconTransmitter
import javax.inject.Singleton

@Singleton
class  PersonContactTransmitter(
    context: Context,
    private val contactsRepository: ContactsRepository
) {

    private val beaconParser = BeaconParser().setBeaconLayout(BEACON_LAYOUT)

    private val beaconTransmitter = BeaconTransmitter(context, beaconParser)

    fun startAdvertising(personId: String) {
        val beacon = Beacon.Builder()
            .setId1(contactsRepository.beaconInfo?.regionUUID ?: DEFAULT_REGION_UUID)
            .setId2(contactsRepository.beaconInfo?.groupId ?: DEFAULT_GROUP_ID)
            .setId3(personId)
            .setManufacturer(MANUFACTURER)
            .setTxPower(TX_POWER)
            .build()
        beaconTransmitter.startAdvertising(beacon)
    }

    fun stopAdvertising() = beaconTransmitter.stopAdvertising()

    companion object {
        private const val DEFAULT_REGION_UUID = "2f234454-cf6d-4a0f-adf2-f4911ba9ffa6"
        private const val DEFAULT_GROUP_ID = "1"
        private const val MANUFACTURER = 0x004c
        private const val TX_POWER = -59
        private const val BEACON_LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
    }
}