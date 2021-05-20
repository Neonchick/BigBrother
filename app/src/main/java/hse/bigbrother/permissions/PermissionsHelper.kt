package hse.bigbrother.permissions

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import hse.bigbrother.R
import moxy.MvpAppCompatActivity
import javax.inject.Singleton

@Singleton
class PermissionsHelper {

    private var activity: MvpAppCompatActivity? = null

    fun setActivity(activity: MvpAppCompatActivity) {
        this.activity = activity
    }

    fun deleteActivity() {
        activity = null
    }

    fun hasPermissions(): Boolean {
        activity?.let { activity ->
            with(activity) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun getPermissions() {
        activity?.let { activity ->
            with(activity) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        getLocationAccessPermissions()
                    } else if (checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        getBackgroundLocationAccessPermission()
                    }
                }
            }
        }
    }

    fun showLocationAccessPermissionsDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity?.let { activity ->
                with(activity) {
                    showDialog(
                        title = getString(R.string.app_needs_location_access),
                        message = getString(R.string.grant_location_access),
                        dismissListener = {
                            requestPermissions(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                                ),
                                PERMISSION_REQUEST_FINE_LOCATION
                            )
                        }
                    )
                }
            }
        }
    }

    fun onRequestPermissionsResult(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        activity?.let { activity ->
            with(activity) {
                for (i in permissions.indices) {
                    when (permissions[i]) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.d(BIG_BROTHER_TAG, FINE_LOCATION_GRANTED)
                            } else {
                                showFunctionalityLimitedDialog(
                                    message = getString(R.string.not_location_access)
                                )
                                return
                            }
                        }
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION -> {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Log.d(BIG_BROTHER_TAG, BACKGROUND_LOCATION_GRANTED)
                            } else {
                                showFunctionalityLimitedDialog(
                                    message = getString(R.string.not_background_location_access)
                                )
                                return
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getLocationAccessPermissions() {
        activity?.let { activity ->
            with(activity) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                ) {
                    showLocationAccessPermissionsDialog()
                } else {
                    showFunctionalityLimitedDialog(
                        message = getString(R.string.not_location_access_go_to_settings)
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getBackgroundLocationAccessPermission() {
        activity?.let { activity ->
            with(activity) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    )
                ) {
                    showDialog(
                        title = getString(R.string.app_needs_background_location_access),
                        message = getString(R.string.grant_background_location_access),
                        dismissListener = {
                            requestPermissions(
                                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                                PERMISSION_REQUEST_BACKGROUND_LOCATION
                            )
                        }
                    )
                } else {
                    showFunctionalityLimitedDialog(
                        message = getString(R.string.not_background_location_access_go_to_settings)
                    )
                }
            }
        }
    }

    private fun showFunctionalityLimitedDialog(message: String) {
        activity?.let { activity ->
            showDialog(
                title = activity.getString(R.string.functionality_limited),
                message = message,
                dismissListener = {}
            )
        }
    }

    private fun showDialog(
        title: String,
        message: String,
        dismissListener: DialogInterface.OnDismissListener
    ) {
        activity?.let { activity ->
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok, null)
            builder.setOnDismissListener(dismissListener)
            builder.show()
        }
    }


    companion object {

        private const val PERMISSION_REQUEST_FINE_LOCATION = 1
        private const val PERMISSION_REQUEST_BACKGROUND_LOCATION = 2
        private const val BIG_BROTHER_TAG = "BigBrother"
        private const val FINE_LOCATION_GRANTED = "Fine location permission granted"
        private const val BACKGROUND_LOCATION_GRANTED = "Background location permission granted"

    }

}