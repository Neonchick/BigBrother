package hse.bigbrother.bottomnavigation.contacts.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import hse.bigbrother.BigBrotherApplication
import hse.bigbrother.BigBrotherApplication.Companion.TRANSMITTER_SERVICE_CHANNEL_ID
import hse.bigbrother.MainActivity
import hse.bigbrother.R
import hse.bigbrother.bottomnavigation.contacts.ContactsRepository
import hse.bigbrother.ibeacon.PersonContactHandler
import hse.bigbrother.ibeacon.PersonContactListener
import hse.bigbrother.ibeacon.PersonContactTransmitter
import hse.bigbrother.sharedpreferences.SharedPreferencesRepository
import org.altbeacon.beacon.Beacon
import javax.inject.Inject

class TransmitterService : PersonContactListener, Service() {

    @Inject
    lateinit var personContactTransmitter: PersonContactTransmitter

    @Inject
    lateinit var personContactHandler: PersonContactHandler

    @Inject
    lateinit var contactsRepository: ContactsRepository

    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    override fun onCreate() {
        (applicationContext as BigBrotherApplication).appComponent.inject(this)
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        personContactTransmitter.startAdvertising(
            personId = sharedPreferencesRepository.getUserId() ?: DEFAULT_USER_ID
        )
        personContactHandler.addListener(this)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification: Notification =
            NotificationCompat.Builder(this, TRANSMITTER_SERVICE_CHANNEL_ID)
                .setContentTitle(CONTENT_TITLE)
                .setSmallIcon(R.drawable.ic_baseline_remove_red_eye_24)
                .setContentIntent(pendingIntent)
                .build()
        startForeground(FOREGROUND_ID, notification)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        personContactTransmitter.stopAdvertising()
        personContactHandler.deleteListeners()

        super.onDestroy()
    }

    override fun onRangeChanged(beacons: Collection<Beacon>) =
        contactsRepository.onRangeChanged(beacons)

    companion object {

        private const val DEFAULT_USER_ID = "0"
        private const val FOREGROUND_ID = 1
        private const val CONTENT_TITLE = "Отслеживание включено"

    }

}