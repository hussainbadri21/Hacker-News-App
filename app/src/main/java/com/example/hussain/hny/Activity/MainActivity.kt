package com.example.hussain.hny.Activity

import android.os.Bundle
import android.content.Context
import android.opengl.Visibility
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.bumptech.glide.Glide
import com.example.hussain.hny.API.Posts_API
import com.example.hussain.hny.API_Response.Posts
import com.example.hussain.hny.API_Response.PostsResponse
import com.example.hussain.hny.R
import com.example.hussain.hny.defaults.ActivityArchitecture

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ActivityArchitecture() {

    var postsData: Array<Posts>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        onCreate()
        main_layout.alpha = 0.3F
        fetchHNPosts()
        recycler.layoutManager = LinearLayoutManager(applicationContext)


    }

    private fun fetchHNPosts() {
        val api = retrofit!!.create(Posts_API::class.java)
        val call = api.fetchPosts()
        call.enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                try {
                    val data: PostsResponse?
                    data = response.body()
                    if (data != null && data.status == "1") {
                        postsData = data.posts
                        main_layout.alpha = 1.0F
                        recycler.adapter = PostAdapter(postsData,applicationContext)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {


            }
        })
    }

    class PostAdapter(val items: Array<Posts>?, val context: Context) : RecyclerView.Adapter<PostAdapter.Holder>() {

        class Holder (view: View) : RecyclerView.ViewHolder(view) {

            val post_card = view.post_card
            val post_meta_title = view.post_meta_title
            val post_meta_image = view.post_meta_image
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(context).inflate(R.layout.post_layout, parent, false))
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            Log.e("HNT",items?.size.toString())
            holder.post_meta_title?.text = items?.get(position)?.title
            if(items?.get(position)?.title !=null)
             Glide.with(context!!).load(items.get(position).image).into(holder.post_meta_image)
            else
                holder.post_meta_image.visibility = View.GONE

        }

        override fun getItemCount(): Int {
            if(items?.size != null)
            return items.size
            else
                return  0
        }

    }


}
