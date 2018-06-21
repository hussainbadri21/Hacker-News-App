package com.example.hussain.hny.API

import com.example.hussain.hny.API_Response.PostsResponse

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Posts_API {

    @POST("hny/")
    fun fetchPosts(): Call<PostsResponse>
}
