package hse.bigbrother.ibeacon

import com.google.gson.annotations.SerializedName

data class BeaconInfo(
    @SerializedName("regionUUID")
    val regionUUID: String?,
    @SerializedName("groupId")
    val groupId: String?
)
