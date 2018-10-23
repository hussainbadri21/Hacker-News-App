package com.example.hussain.hny.Activity

import android.os.Bundle
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.bumptech.glide.Glide
import com.example.hussain.hny.API.Posts_API
import com.example.hussain.hny.API_Response.PostsResponse
import com.example.hussain.hny.Adapters.FeedAdapter
import com.example.hussain.hny.R
import com.example.hussain.hny.R.id.toolbar
import com.example.hussain.hny.defaults.ActivityArchitecture
import com.example.hussain.hny.defaults.AdapterArchitecture
import com.example.hussain.hny.utils.RetrofitWrapper

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import com.google.gson.Gson
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ActivityArchitecture() {

    var postID: Array<Long>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroll_bg.background = applicationContext.getDrawable(R.drawable.bg_shape)
        }

//        main_layout.alpha = 0.3F
//        recycler.layoutManager = LinearLayoutManager(applicationContext)
//        fetchPostID()


    }

    private fun fetchPostID() {
        val api = retrofit!!.create(Posts_API::class.java)
        val call = api.fetchTopPostID()
        call.enqueue(object : Callback<Array<Long>> {
            override fun onResponse(call: Call<Array<Long>>, response: Response<Array<Long>>) {
                try {
                    val data: Array<Long>?
                    data = response.body()
                    if (data != null) {
                        postID = data
                        main_layout.alpha = 1.0F
                        recycler.adapter = FeedAdapter(postID, applicationContext,shared_preferences!!)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<Array<Long>>, t: Throwable) {


            }
        })
    }
}
