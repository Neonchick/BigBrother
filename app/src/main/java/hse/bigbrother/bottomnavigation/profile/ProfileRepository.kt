package hse.bigbrother.bottomnavigation.profile

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hse.bigbrother.bottomnavigation.profile.data.UserInfo
import hse.bigbrother.volley.VolleyRequests
import javax.inject.Singleton

@Singleton
class ProfileRepository(
    private val volleyRequests: VolleyRequests
) {

    private val gsonBuilder: GsonBuilder = GsonBuilder()

    private val gson: Gson = gsonBuilder.create()

    private var userInfo: UserInfo? = null

    fun getUserInfo(
        listener: (UserInfo) -> Unit,
        errorListener: () -> Unit
    ) {
        if (userInfo != null) {
            listener(userInfo!!)
        } else {
            volleyRequests.getUserInfo(
                listener = { response ->
                    val userInfo = gson.fromJson(response.toString(), UserInfo::class.java)
                    this.userInfo = userInfo
                    listener(userInfo)
                },
                errorListener = { error ->
                    Log.e("ProfileRepository", error.toString())
                    errorListener()
                }
            )
        }
    }

    fun deleteInfo() {
        userInfo = null
    }

}