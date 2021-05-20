package hse.bigbrother.volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hse.bigbrother.bottomnavigation.contacts.data.BeaconConnect
import hse.bigbrother.login.data.LoginInfo
import hse.bigbrother.sharedpreferences.SharedPreferencesRepository
import hse.bigbrother.volley.dto.BeaconConnectsDto
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Singleton

@Singleton
class VolleyRequests(
    context: Context,
    sharedPreferencesRepository: SharedPreferencesRepository
) {

    private val requestQueue = Volley.newRequestQueue(context)

    private val gsonBuilder: GsonBuilder = GsonBuilder()

    private val gson: Gson = gsonBuilder.create()

    private val headers: MutableMap<String, String> =
        mutableMapOf(TOKEN_KEY to (sharedPreferencesRepository.getToken() ?: ""))

    fun login(
        loginInfo: LoginInfo,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            URL + LOGIN_URL,
            JSONObject(gson.toJson(loginInfo).toString()),
            listener,
            errorListener
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun getUserInfo(
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        val jsonObjectRequest = JsonObjectRequestWithHeaders(
            Request.Method.GET,
            URL + USER_INFO_URL,
            null,
            listener,
            errorListener,
            headers
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun getScenarios(
        listener: Response.Listener<JSONArray>,
        errorListener: Response.ErrorListener
    ) {
        val jsonArrayRequest = JsonArrayRequestWithHeaders(
            Request.Method.GET,
            URL + SCENARIOS_URL,
            null,
            listener,
            errorListener,
            headers
        )
        requestQueue.add(jsonArrayRequest)
    }

    fun getBeaconInfo(
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        val jsonObjectRequest = JsonObjectRequestWithHeaders(
            Request.Method.GET,
            URL + BEACON_INFO_URL,
            null,
            listener,
            errorListener,
            headers
        )
        requestQueue.add(jsonObjectRequest)
    }

    fun sendBeaconConnects(
        beaconConnects: Collection<BeaconConnect>,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        val jsonObjectRequest = JsonObjectRequestWithHeaders(
            Request.Method.POST,
            URL + SEND_BEACON_CONNECTS_URL,
            JSONObject(gson.toJson(BeaconConnectsDto(connects = beaconConnects)).toString()),
            listener,
            errorListener,
            headers
        )
        requestQueue.add(jsonObjectRequest)
    }

    companion object {

        private const val TOKEN_KEY = "token"

        private const val URL = "https://08af607e-f135-4815-aca1-5155942f7c41.mock.pstmn.io"

        private const val LOGIN_URL = "/auth/login"
        private const val TEST_LOGIN_URL_WRONG_DATA = "/auth/login2"
        private const val TEST_LOGIN_URL = "/auth/login3"

        private const val USER_INFO_URL = "/userInfo"

        private const val SCENARIOS_URL = "/scenarios"

        private const val BEACON_INFO_URL = "/beaconInfo"

        private const val SEND_BEACON_CONNECTS_URL = "/sendBeaconConnects"

    }

}