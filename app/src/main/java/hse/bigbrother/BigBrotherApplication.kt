package hse.bigbrother

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import hse.bigbrother.di.AppComponent
import hse.bigbrother.di.DaggerAppComponent
import hse.bigbrother.di.modelus.ContextModule

class BigBrotherApplication: Application() {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .contextModule(ContextModule(this))
        .build()

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel();
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                TRANSMITTER_SERVICE_CHANNEL_ID,
                "BigBrother Transmitter Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }


    companion object {

        const val TRANSMITTER_SERVICE_CHANNEL_ID = "BigBrotherTransmitterServiceChannel"

    }

}