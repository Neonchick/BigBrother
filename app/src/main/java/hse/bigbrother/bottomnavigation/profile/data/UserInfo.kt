package hse.bigbrother.bottomnavigation.profile.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("job")
    val job: String? = null,
)
