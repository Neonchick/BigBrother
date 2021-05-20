package hse.bigbrother.options

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import androidx.appcompat.app.AlertDialog
import hse.bigbrother.R
import moxy.MvpAppCompatActivity
import javax.inject.Singleton

@Singleton
class OptionsChecker(private val context: Context) {

    private var activity: MvpAppCompatActivity? = null

    private var isNetworkEnable: Boolean

    fun setActivity(activity: MvpAppCompatActivity) {
        this.activity = activity
    }

    fun deleteActivity() {
        activity = null
    }

    init {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        isNetworkEnable = true
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        isNetworkEnable = false
                    }
                }
                )
            }
            isNetworkEnable = false
        } catch (e: Exception) {
            isNetworkEnable = false
        }
    }

    fun isAllNeededOptionsEnable() = isNetworkEnable && isBluetoothEnable() && isLocationEnable()

    private fun isBluetoothEnable() = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true

    private fun isLocationEnable(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun checkOptions() {
        if (!isNetworkEnable) {
            showDialog(
                title = context.getString(R.string.no_network_title),
                message = context.getString(R.string.no_network_message)
            )
            return
        }
        if (!isBluetoothEnable()) {
            showDialog(
                title = context.getString(R.string.no_bluetooth_title),
                message = context.getString(R.string.no_bluetooth_message)
            )
            return
        }
        if (!isLocationEnable()) {
            showDialog(
                title = context.getString(R.string.no_location_title),
                message = context.getString(R.string.no_location_message)
            )
            return
        }
    }

    private fun showDialog(
        title: String,
        message: String
    ) {
        activity?.let { activity ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok, null)
            builder.show()
        }
    }

}