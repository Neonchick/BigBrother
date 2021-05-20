package hse.bigbrother.bottomnavigation.scenarios.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Scenario(
    val name: String? = null,
    val description: String? = null,
    val isSuccessStatus: Boolean? = null,
    val role: String? = null,
    val time: String? = null,
    val peopleCount: Int? = null,
) : Parcelable