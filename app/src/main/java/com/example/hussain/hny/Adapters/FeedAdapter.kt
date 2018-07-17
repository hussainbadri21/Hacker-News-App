package com.example.hussain.hny.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hussain.hny.API.Posts_API
import com.example.hussain.hny.API_Response.PostsResponse
import com.example.hussain.hny.R
import com.example.hussain.hny.defaults.AdapterArchitecture
import kotlinx.android.synthetic.main.post_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class FeedAdapter(val items: Array<Long>?, val context: Context, val sharedPreferences: SharedPreferences) : AdapterArchitecture<FeedAdapter.Holder>() {


    var metaRetrofit: Posts_API? = null
    var data: ArrayList<PostsResponse>? = ArrayList()

    init {
        mContext = context;
        TAG = "FeedAdapter"
        onCreate();
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.title
        val container = view
        val image = view.image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.post_layout, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, @SuppressLint("RecyclerView") position: Int) {

        if (data != null && data!!.size > position) {
            holder.title?.text = data!![position].title
            if (data!![position]?.meta?.image != null) {
                picasso(data!![position].meta?.image, holder.image)
                holder.image.visibility = View.VISIBLE
            } else {
                holder.image.visibility = View.GONE
            }
        } else {
            val api = retrofit!!.create(Posts_API::class.java)
            val call = api.fetchTopPost(items!![position].toString())
            call.enqueue(object : Callback<PostsResponse> {
                override fun onFailure(call: Call<PostsResponse>?, t: Throwable?) {
                }

                override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                    try {
                        val dataMain: PostsResponse?
                        dataMain = response.body()
                        if (dataMain != null) {
                            data!!.add(position,dataMain)
                            holder.title?.text = dataMain.title
                            if (dataMain.url != null) {
                                val retrofitMeta = Retrofit.Builder()
                                        .baseUrl("https://api.urlmeta.org/?url=")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()

                                metaRetrofit = retrofitMeta.create(Posts_API::class.java)

                                val callMeta = metaRetrofit!!.getMetaInfo(dataMain.url!!)

                                callMeta.enqueue(object : Callback<PostsResponse> {
                                    override fun onResponse(callMeta: Call<PostsResponse>, response: Response<PostsResponse>) {
                                        val data2: PostsResponse?
                                        data2 = response.body()
                                        if (data2 != null) {
                                            data!![position].meta = data2.meta
                                            if (data2?.meta?.image != null) {
                                                picasso(data2.meta?.image, holder.image)
                                                holder.image.visibility = View.VISIBLE

                                            } else {
                                                holder.image.visibility = View.GONE
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<PostsResponse>, t: Throwable) {

                                    }
                                })
                            }
                        }
                    } catch (e: Exception) {
                        log(e)
                    }
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}


