package hse.bigbrother.volley

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class JsonObjectRequestWithHeaders(
    method: Int,
    url: String,
    jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener?,
    private val requestHeaders: MutableMap<String, String>,
): JsonObjectRequest(
    method,
    url,
    jsonRequest,
    listener,
    errorListener
) {
    override fun getHeaders(): MutableMap<String, String> {
        return requestHeaders;
    }
}