package hse.bigbrother.volley

import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray

class JsonArrayRequestWithHeaders(
    method: Int,
    url: String,
    jsonRequest: JSONArray?,
    listener: Response.Listener<JSONArray>,
    errorListener: Response.ErrorListener?,
    private val requestHeaders: MutableMap<String, String>,
) : JsonArrayRequest(method, url, jsonRequest, listener, errorListener) {
    override fun getHeaders(): MutableMap<String, String> {
        return requestHeaders;
    }
}
