package com.aman802.pokedb.network

import android.content.Context
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object VolleyService {

    interface VolleyInterface {
        fun onSuccess(response: JSONObject)
        fun onError(error: VolleyError)
    }

    private var requestQueue: RequestQueue? = null

    fun makeVolleyRequest(context: Context, apiUrl: String, method: Int, postRequestParams: JSONObject?, TAG: String, volleyInterface: VolleyInterface) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context)
        }

        val jsonObjectRequest = JsonObjectRequest(method, apiUrl, postRequestParams,
            Response.Listener {
                volleyInterface.onSuccess(it)
            },
            Response.ErrorListener {
                volleyInterface.onError(it)
            })

        jsonObjectRequest.tag = TAG
        requestQueue?.add(jsonObjectRequest)
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