package hse.bigbrother

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import hse.bigbrother.bottomnavigation.BottomNavigationFragment
import hse.bigbrother.login.LoginFragment
import hse.bigbrother.options.OptionsChecker
import hse.bigbrother.permissions.PermissionsHelper
import hse.bigbrother.sharedpreferences.SharedPreferencesRepository
import moxy.MvpAppCompatActivity
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    @Inject
    lateinit var permissionsHelper: PermissionsHelper

    @Inject
    lateinit var optionsChecker: OptionsChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as BigBrotherApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        permissionsHelper.setActivity(this)
        optionsChecker.setActivity(this)

        sharedPreferencesRepository.sharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                if (sharedPreferencesRepository.isLoginNeed()) {
                    add<LoginFragment>(R.id.fragment_container_view)
                } else {
                    add<BottomNavigationFragment>(R.id.fragment_container_view)
                }
            }
        }

        if (sharedPreferencesRepository.isFirstLaunch()) {
            permissionsHelper.showLocationAccessPermissionsDialog()
            sharedPreferencesRepository.setRelaunch()
        } else {
            permissionsHelper.getPermissions()
        }
    }

    fun login() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<BottomNavigationFragment>(R.id.fragment_container_view)
        }
    }

    fun logout() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<LoginFragment>(R.id.fragment_container_view)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        permissionsHelper.onRequestPermissionsResult(permissions, grantResults)
    }

    override fun onDestroy() {
        permissionsHelper.deleteActivity()
        optionsChecker.deleteActivity()
        super.onDestroy()
    }

}