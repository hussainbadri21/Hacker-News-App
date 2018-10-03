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
                        recycler.adapter = PostAdapter(postID, applicationContext,shared_preferences!!)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<Array<Long>>, t: Throwable) {


            }
        })
    }


    class PostAdapter(val items: Array<Long>?, val context: Context, val sharedPreferences: SharedPreferences) :  RecyclerView.Adapter<PostAdapter.Holder>() {


        var metaRetrofit: Posts_API? = null

        class Holder(view: View) : RecyclerView.ViewHolder(view) {

            val post_card = view.post_card
            val post_meta_title = view.post_meta_title
            val post_meta_image = view.post_meta_image
            val position = view.position

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(context).inflate(R.layout.post_layout, parent, false))
        }

        override fun onBindViewHolder(holder: Holder, position2: Int) {

            val position=holder.adapterPosition
            val gson = Gson()
            val json = sharedPreferences.getString(items!![position].toString(), "");
            val obj = gson.fromJson<PostsResponse>(json, PostsResponse::class.java!!)
            if (false) {
                holder.post_meta_title?.text = obj.title
                if (obj.meta==null){
                    if (obj.url != null) {
                        val retrofitMeta = Retrofit.Builder()
                                .baseUrl("https://api.urlmeta.org/?url=")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                        metaRetrofit = retrofitMeta.create(Posts_API::class.java)

                        val callMeta = metaRetrofit!!.getMetaInfo(obj.url!!)

                        callMeta.enqueue(object : Callback<PostsResponse> {
                            override fun onResponse(callMeta: Call<PostsResponse>, response: Response<PostsResponse>) {
                                val data: PostsResponse?
                                data = response.body()
                                obj.meta = data?.meta
                                val gson = Gson()
                                val json = gson.toJson(obj)
//                                storeStringInSharedPreferences(obj.id!!, json)
                                if (data != null) {
                                    if (data?.meta?.image != null) {
//                                        glide(data.meta?.image, holder.post_meta_image)
                                        holder.post_meta_image.visibility = View.VISIBLE

                                    } else {
                                        holder.post_meta_image.visibility = View.GONE
                                    }
                                }
                            }

                            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {

                            }
                        })
                    }

                }else{
                    if (obj.meta?.image != null) {
                        Glide.with(context).load(obj.meta?.image).into(holder.post_meta_image)
//                        glide(obj.meta?.image, holder.post_meta_image)
                        holder.post_meta_image.visibility = View.VISIBLE
                    }
                    else{
                        holder.post_meta_image.visibility = View.GONE
                    }
                }
            } else {
                val retrofit = RetrofitWrapper.companion.getRetrofitRequest(context!!, sharedPreferences!!, true)
                val api = retrofit!!.create(Posts_API::class.java)
                val call = api.fetchTopPost(items!![position].toString())
                holder.position?.text=(position.toString()+":"+items!![position].toString())
                call.enqueue(object : Callback<PostsResponse> {
                    override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                        try {
                            val dataMain: PostsResponse?
                            dataMain = response.body()
                            if (dataMain != null) {
                                dataMain.isRead = false
                                holder.post_meta_title?.text = dataMain.title

                                if (dataMain.url != null) {
                                    val retrofitMeta = Retrofit.Builder()
                                            .baseUrl("https://api.urlmeta.org/?url=")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build()

                                    metaRetrofit = retrofitMeta.create(Posts_API::class.java)

                                    val callMeta = metaRetrofit!!.getMetaInfo(dataMain.url!!)

                                    callMeta.enqueue(object : Callback<PostsResponse> {
                                        override fun onResponse(callMeta: Call<PostsResponse>, response: Response<PostsResponse>) {
                                            val data: PostsResponse?
                                            data = response.body()
                                            dataMain.meta = data?.meta
                                            val gson = Gson()
                                            val json = gson.toJson(dataMain)
//                                            storeStringInSharedPreferences(dataMain.id!!, json)
                                            if (data != null) {
                                                if (data?.meta?.image != null) {
                                                    Glide.with(context).load(data.meta?.image).into(holder.post_meta_image)
//                                                    glide(data.meta?.image, holder.post_meta_image)
                                                    holder.post_meta_image.visibility = View.VISIBLE

                                                } else {
                                                    holder.post_meta_image.visibility = View.GONE
                                                }
                                            }
                                        }

                                        override fun onFailure(call: Call<PostsResponse>, t: Throwable) {

                                        }
                                    })
                                }
                                val gson = Gson()
                                val json = gson.toJson(dataMain)
//                                storeStringInSharedPreferences(dataMain.id!!, json)
                            }
                        } catch (e: Exception) {
//                            log(e)
                        }
                    }

                    override fun onFailure(callMeta: Call<PostsResponse>, t: Throwable) {

                    }
                })
            }

        }

        override fun getItemCount(): Int {
            if (items?.size != null)
                return items.size
            else
                return 0
        }

    }


}
