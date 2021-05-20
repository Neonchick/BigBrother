package hse.bigbrother.bottomnavigation.scenarios

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hse.bigbrother.bottomnavigation.scenarios.data.Scenario
import hse.bigbrother.volley.VolleyRequests
import javax.inject.Singleton

@Singleton
class ScenariosRepository(
    private val volleyRequests: VolleyRequests
) {

    private val gsonBuilder: GsonBuilder = GsonBuilder()

    private val gson: Gson = gsonBuilder.create()

    private var scenarios: List<Scenario>? = null

    fun getScenarios(
        listener: (List<Scenario>) -> Unit,
        errorListener: () -> Unit
    ) {
        volleyRequests.getScenarios(
            listener = { response ->
                val scenarios =
                    gson.fromJson(response.toString(), Array<Scenario>::class.java).asList()
                this.scenarios = scenarios
                listener(scenarios)
            },
            errorListener = { error ->
                Log.e("ScenariosRepository", error.toString())
                errorListener()
            }
        )
    }

    fun deleteInfo() {
        scenarios = null
    }

}