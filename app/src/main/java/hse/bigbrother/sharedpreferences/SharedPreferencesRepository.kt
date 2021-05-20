package hse.bigbrother.sharedpreferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesRepository @Inject constructor() {

    var sharedPreferences: SharedPreferences? = null

    fun putUserId(userId: String?) = putString(USER_ID_KEY, userId)

    fun putToken(token: String?) = putString(TOKEN_KEY, token)

    fun getUserId():String? {
        return getString(USER_ID_KEY)
    }

    fun getToken() = getString(TOKEN_KEY)

    fun deleteUserId() = putString(USER_ID_KEY, null)

    fun deleteToken() = putString(TOKEN_KEY, null)

    fun isLoginNeed() = getUserId() == null || getToken() == null

    fun isFirstLaunch() = sharedPreferences?.getBoolean(FIRST_LAUNCH_KEY, true) ?: true

    fun setRelaunch() = putBoolean(FIRST_LAUNCH_KEY, false)

        @SuppressLint("CommitPrefEdits")
    private fun putString(key: String, value: String?) {
        if (sharedPreferences != null) {
            with(sharedPreferences!!.edit()) {
                this.putString(key, value)
                apply()
            }
        }
    }

    private fun getString(key: String) = sharedPreferences?.getString(key, null)

    @SuppressLint("CommitPrefEdits")
    private fun putBoolean(key: String, value: Boolean) {
        if (sharedPreferences != null) {
            with(sharedPreferences!!.edit()) {
                this.putBoolean(key, value)
                apply()
            }
        }
    }

    companion object {

        private const val USER_ID_KEY = "USER_ID"
        private const val TOKEN_KEY = "TOKEN"
        private const val FIRST_LAUNCH_KEY = "FIRST_LAUNCH"

    }

}