package com.example.foodium.utils

import android.util.Log
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

fun getErrorMessage(error: Throwable): String {
    Log.d("before conversion","$error")
    return when (error) {
        is HttpException -> {
            val errorBody = error.response()?.errorBody()?.string()?:"unknown"
            try {
                val jsonObject = JSONObject(errorBody ?: "")
                Log.d("error at coversion",jsonObject.getString("message"))
                jsonObject.getString("message") // Assuming the API returns { "message": "Error details" }
            } catch (e: Exception) {
                "Server Error: ${error.code()}"
            }
        }

        is IOException -> "Network Error. Please check your connection."
        else -> error.localizedMessage ?: "Unknown Error"
    }
}
