package com.aman802.pokedb.network

import android.content.Context
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object VolleyService {

    interface JSONObjectInterface {
        fun onJSONObjectSuccess(response: JSONObject)
        fun onError(error: VolleyError)
    }

    interface JSONArrayInterface {
        fun onJSONArraySuccess(response: JSONArray)
        fun onError(error: VolleyError)
    }

    private var requestQueue: RequestQueue? = null

    fun makeJSONObjectRequest(context: Context, apiUrl: String, method: Int, postRequestParams: JSONObject?, TAG: String, mCallback: JSONObjectInterface) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context)
        }

        val jsonObjectRequest = JsonObjectRequest(method, apiUrl, postRequestParams,
            Response.Listener {
                mCallback.onJSONObjectSuccess(it)
            },
            Response.ErrorListener {
                mCallback.onError(it)
            })

        jsonObjectRequest.tag = TAG
        requestQueue?.add(jsonObjectRequest)
    }

    fun makeJSONArrayRequest(context: Context, apiUrl: String, method: Int, postRequestParams: JSONArray?, TAG: String, mCallback: JSONArrayInterface) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context)
        }

        val jsonArrayRequest = JsonArrayRequest(method, apiUrl, postRequestParams,
            Response.Listener<JSONArray> {
                mCallback.onJSONArraySuccess(it)
            },
            Response.ErrorListener {
                mCallback.onError(it)
            })

        jsonArrayRequest.tag = TAG
        jsonArrayRequest.retryPolicy = DefaultRetryPolicy(TimeUnit.SECONDS.toMillis(10).toInt(), 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        requestQueue?.add(jsonArrayRequest)
    }

    fun handleVolleyError(error: VolleyError, displayToast: Boolean, context: Context) {

        var toastMessage = ""

        if (error is NetworkError) {
            toastMessage = "Unstable Internet Connection! Please check your connection."
        }

        else if (error is TimeoutError) {
            toastMessage = "Timeout Error"
        }

        else if (error is ServerError) {
            toastMessage = getVolleyErrorMessage(error)
        }

        if (displayToast) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getVolleyErrorMessage(error: VolleyError): String {
        val response: NetworkResponse? = error.networkResponse
        if (response?.data != null) {
            val jsonObject = JSONObject(String(response.data))
            return jsonObject.getString("message")
        }
        return "NULL";
    }

}