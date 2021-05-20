package hse.bigbrother.volley.dto

import com.google.gson.annotations.SerializedName
import hse.bigbrother.bottomnavigation.contacts.data.BeaconConnect

data class BeaconConnectsDto(
    @SerializedName("connects")
    val connects: Collection<BeaconConnect>
)
