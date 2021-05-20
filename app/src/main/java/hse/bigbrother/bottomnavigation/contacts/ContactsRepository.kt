package hse.bigbrother.bottomnavigation.contacts

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hse.bigbrother.bottomnavigation.contacts.data.BeaconConnect
import hse.bigbrother.ibeacon.BeaconInfo
import hse.bigbrother.volley.VolleyRequests
import org.altbeacon.beacon.Beacon
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Singleton
class ContactsRepository(
    private val volleyRequests: VolleyRequests
) {

    private val gsonBuilder: GsonBuilder = GsonBuilder()

    private val gson: Gson = gsonBuilder.create()

    var beaconInfo: BeaconInfo? = null

    var isTransmitterOn = false

    var connectionList: MutableList<BeaconConnect> = mutableListOf()

    fun onRangeChanged(beacons: Collection<Beacon>) {
        val beaconConnects = beacons.map { beacon ->
            BeaconConnect(
                id = beacon.id3.toString(),
                distance = "%.2f".format(beacon.distance),
                time = getCurrentTime()
            )
        }
        connectionList.addAll(0, beaconConnects)
        sendBeaconConnects(beaconConnects)
    }

    private fun sendBeaconConnects(beaconConnects: Collection<BeaconConnect>) {
        volleyRequests.sendBeaconConnects(
            beaconConnects,
            listener = {},
            errorListener = { error ->
                Log.e("ContactsRepository", error.toString())
            }
        )
    }

    private fun getCurrentTime(): String {
        val currentDate = Date()
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return timeFormat.format(currentDate)
    }

    fun deleteInfo() {
        isTransmitterOn = false
        connectionList = mutableListOf()
        beaconInfo = null
    }

    fun getBeaconInfo(
        listener: () -> Unit,
        errorListener: () -> Unit
    ) {
        if (beaconInfo != null) {
            listener()
        } else {
            volleyRequests.getBeaconInfo(
                listener = { response ->
                    this.beaconInfo = gson.fromJson(response.toString(), BeaconInfo::class.java)
                    if (beaconInfo?.regionUUID != null && beaconInfo?.groupId != null) {
                        listener()
                    } else {
                        errorListener()
                    }
                },
                errorListener = { error ->
                    Log.e("ContactsRepository", error.toString())
                    errorListener()
                }
            )
        }
    }

}