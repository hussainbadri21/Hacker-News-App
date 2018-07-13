package com.example.hussain.hny.API

import com.example.hussain.hny.API_Response.PostsResponse

import retrofit2.Call
import retrofit2.http.*

interface Posts_API {


    @GET("topstories.json?print=pretty")
    fun fetchTopPostID(): Call<Array<Long>>

    @GET("item/{post_id}.json?print=pretty")
    fun fetchTopPost(@Path("post_id") post_id:String? ): Call<PostsResponse>

    @GET(".")
    fun getMetaInfo(@Query("url")  url:String ): Call<PostsResponse>
}
