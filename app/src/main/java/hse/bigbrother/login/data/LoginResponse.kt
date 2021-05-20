package hse.bigbrother.login.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("status")
    val status: String? = null,
)