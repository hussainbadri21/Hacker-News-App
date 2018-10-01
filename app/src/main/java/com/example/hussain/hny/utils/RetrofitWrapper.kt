package com.example.hussain.hny.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log

import com.example.hussain.hny.BuildConfig
import com.example.hussain.hny.Activity.MainActivity
import com.example.hussain.hny.R

import org.json.JSONObject

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Gp on 6/9/2017.
 */

class RetrofitWrapper {
    object companion {

        fun getRetrofitRequest(mContext: Context, shared_preferences: SharedPreferences,
                               logoutOnInvalidToken: Boolean): Retrofit {
            val TAG = "RetrofitWrapper"
            val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                if (message.contains("\"_chst_rt\":")) {
                    try {
                        val obj = JSONObject(message)
                        if (obj != null && obj.get("_chst_rt") != null) {
                            val tokenMessage = obj.get("_chst_rt").toString()
                            if (tokenMessage != "") {
                                val tokenObject = JSONObject(tokenMessage)
                                if (tokenObject != null && tokenObject.get("token") != null) {
                                    val token = tokenObject.get("token").toString()
                                    if (token != "") {
                                        if (token != null) {
                                            if (BuildConfig.DEBUG)
                                                Log.d(TAG, "_chst_rt: $token")
                                        }

                                        if (shared_preferences != null) {
                                            refreshToken(shared_preferences, token)
                                        }

                                    }
                                }
                            }
                        }

                    } catch (t: Throwable) {
                        Log.e(TAG, "Could not parse refresh token:")
                    }

                }
                Log.d(TAG, "response: $message")
            })

            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
            builder.addInterceptor(logInterceptor)

            builder.addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                        .header("Referer", "ref")
//                                mContext.resources.getString(R.string.platform_type))
                        .header("Authorization", shared_preferences.getString("token", "")!!)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())

                val request = requestBuilder.build()
                val response = chain.proceed(request!!)

                if (request != null) {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "request: " + response.toString())
                }

                if (response.code() > 0) {
                    when (response.code()) {
                        400 // Bad Request
                        -> {
                        }
                        401 // Forbidden
                        -> {
                        }
                        403 // Forbidden
                        -> {
                        }
                        404 // Not Found
                        -> {
                        }
                        408 // Request Timeout
                        -> {
                        }
                        429 // Too Many Requests
                        -> {
                        }
                        500 // Internal Server Error
                        -> {
                        }
                        302 // Invalid token
                        -> if (logoutOnInvalidToken) {
                            promptLogin(mContext, shared_preferences)
                        }
                        200 // success
                        -> {
                        }
                    }
                }

                response
            }
            val okClient = builder.build()

            return Retrofit.Builder()
                    .baseUrl(mContext.getString(R.string.API_URL))
//                            mContext.resources.getString(R.string.api_url))
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        @SuppressLint("WrongConstant")
        private fun promptLogin(mContext: Context, sharedPreferences: SharedPreferences) {
            try {
                val shared_preferences_editor = sharedPreferences.edit()
                shared_preferences_editor.putBoolean("login_status", false)
                shared_preferences_editor.remove("uid")
                shared_preferences_editor.remove("usr")
                shared_preferences_editor.remove("email")
                shared_preferences_editor.remove("last_name")
                shared_preferences_editor.remove("user_type")
                shared_preferences_editor.remove("user_image")
                shared_preferences_editor.remove("profession")
                shared_preferences_editor.remove("recent_course_id")
                shared_preferences_editor.remove("total_time")
                shared_preferences_editor.remove("points")
                shared_preferences_editor.apply()

                val intent_login = Intent(mContext, MainActivity::class.java)
                intent_login.putExtra("unregisterFCM", true)
                intent_login.putExtra("previousUID", sharedPreferences.getString("uid", "0"))
                intent_login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                mContext.startActivity(intent_login)

            } catch (e: Exception) {

            }

        }


        private fun refreshToken(sharedPreferences: SharedPreferences, token: String?) {
            try {
                val shared_preferences_editor = sharedPreferences.edit()
                shared_preferences_editor.putString("token", token)
                shared_preferences_editor.commit()
            } catch (e: Exception) {

            }

        }
    }

}