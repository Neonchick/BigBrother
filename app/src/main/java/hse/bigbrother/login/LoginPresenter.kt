package hse.bigbrother.login

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hse.bigbrother.login.data.LoginInfo
import hse.bigbrother.login.data.LoginResponse
import hse.bigbrother.sharedpreferences.SharedPreferencesRepository
import hse.bigbrother.volley.VolleyRequests
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    private val volleyRequests: VolleyRequests,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) : MvpPresenter<LoginView>() {

    private val gsonBuilder: GsonBuilder = GsonBuilder()

    private val gson: Gson = gsonBuilder.create()

    fun loginButtonClick(loginInfo: LoginInfo) {
        viewState.showProgress()
        volleyRequests.login(
            loginInfo = loginInfo,
            listener = { response ->
                val loginResponse = gson.fromJson(response.toString(), LoginResponse::class.java)
                if (loginResponse.token == null || loginResponse.username == null) {
                    viewState.showWrongData()
                } else {
                    sharedPreferencesRepository.putUserId(loginResponse.username)
                    sharedPreferencesRepository.putToken(loginResponse.token)
                    viewState.loginSuccess()
                }
            },
            errorListener = { error ->
                Log.e("LoginPresenter", error.toString())
                viewState.showError()
            }
        )
    }

}