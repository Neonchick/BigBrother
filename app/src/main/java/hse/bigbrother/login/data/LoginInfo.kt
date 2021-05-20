package hse.bigbrother.login.data

import com.google.gson.annotations.SerializedName

data class LoginInfo(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String? = null,
)