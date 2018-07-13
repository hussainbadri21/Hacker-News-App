package com.example.hussain.hny.defaults

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.Uri
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.example.hussain.hny.R
import com.example.hussain.hny.utils.RetrofitWrapper
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

import java.util.ArrayList

import retrofit2.Retrofit

import android.content.Context.MODE_PRIVATE


@SuppressLint("Registered")
abstract class AdapterArchitecture<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>(), ArchitecturalTemplate {

    var TAG = "DefaultAdapter"
    var shared_preferences: SharedPreferences? = null
    var shared_preferences_editor: SharedPreferences.Editor? = null
    var mContext: Context? = null
    var fragmentManager: FragmentManager? = null
    var retrofit: Retrofit? = null

    override val isNetworkConnected: Boolean
        get() {
            if (mContext != null) {
                val cm = mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return cm?.activeNetworkInfo != null
            }
            return false
        }

    //Example Usage
    //    public class Feed_Adapter extends AdapterArchitecture<Feed_Adapter.feed_view>
    //    public Feed_Adapter(Context mContext, ArrayList<Feed_Element> feed_collection,
    //                        SharedPreferences shared_preferences, Activity activity, FragmentManager fragmentManager, Feed_Listener vmCallBack, int lesson_id) {
    //        this.mContext = mContext;
    //        this.feed_collection = feed_collection;
    //        this.shared_preferences = shared_preferences;
    //        shared_preferences_editor = shared_preferences.edit();
    //        this.activity = activity;
    //        if (activity != null) {
    //            smallBang = SmallBang.attach2Window(activity);
    //        }
    //        this.lesson_id = lesson_id;
    //        this.fragmentManager = fragmentManager;
    //        this.vmCallBack = vmCallBack;
    //        setHasStableIds(true);
    //        TAG = "Feed_Adapter";
    //        onCreate();
    //    }


    //Define all the steps that need to be initialized in starting
    override fun onCreate() {
        onPageStart()
        defineUIComponentsAndAdapters()
    }

    //Define all the steps that need to be initialized in starting
    override fun onPageStart() {
        configureAPIClient()
        customInitializer()
        setHasStableIds(true)
    }

    override fun configureAPIClient() {
        configureDB()
        try {
            retrofit = RetrofitWrapper.companion.getRetrofitRequest(mContext!!, shared_preferences!!, true)
        } catch (e: Exception) {
            log(e)
        }

    }

    override fun configureDB() {
        try {
            shared_preferences = mContext!!.getSharedPreferences("Chalkst_Pref", MODE_PRIVATE)
            shared_preferences_editor = shared_preferences!!.edit()
        } catch (e: Exception) {
            log(e)
        }

    }


    //Override and define custom initialization
    override fun customInitializer() {

    }

    //here override all the defineUIComponentsAndAdapters functions
    override fun defineUIComponentsAndAdapters() {

    }

    //Override and define what needs to be reloaded
    override fun reload() {

    }

    @ColorInt
    override fun getColorVal(@ColorRes resourceID: Int): Int {
        return ContextCompat.getColor(mContext!!, resourceID)
    }


    override fun setText(view: TextView, msg: String?) {
        view.text = sanitizeString(msg)
    }

    override fun setText(view: TextView, msg: String?, visiblity: Int) {
        view.text = sanitizeString(msg, view, visiblity)
    }

    override fun setText(view: TextView, msg: String?, viewtohide: View, visiblity: Int) {
        view.text = sanitizeString(msg, viewtohide, visiblity)
    }

    override fun setText(view: TextView, msg: SpannableStringBuilder) {
        view.text = sanitizeString(msg)
    }

    override fun setText(view: TextView, msg: SpannableStringBuilder, visiblity: Int) {
        view.text = sanitizeString(msg, view, visiblity)
    }

    override fun setText(view: TextView, msg: SpannableStringBuilder, viewtohide: View, visiblity: Int) {
        view.text = sanitizeString(msg, viewtohide, visiblity)
    }


    override fun picasso(img_url: String?, error: Int, placeholder: Int, view: ImageView) {
        if (verifyString(img_url))
            Picasso.get().load(img_url).error(error).placeholder(placeholder).into(view)
        else
            Picasso.get().load(placeholder).error(error).placeholder(placeholder).into(view)
    }

    override fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView) {
        if (img_url != null)
            Picasso.get().load(img_url).error(error).placeholder(placeholder).into(view)
        else
            Picasso.get().load(placeholder).error(error).placeholder(placeholder).into(view)
    }

    override fun picasso(img_url: String?, error: Int, placeholder: Int, view: ImageView, transformation: Transformation) {
        if (verifyString(img_url))
            Picasso.get().load(img_url).error(error).placeholder(placeholder).transform(transformation).into(view)
        else
            Picasso.get().load(placeholder).error(error).placeholder(placeholder).transform(transformation).into(view)
    }

    override fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, transformation: Transformation) {
        if (img_url != null)
            Picasso.get().load(img_url).error(error).placeholder(placeholder).transform(transformation).into(view)
        else
            Picasso.get().load(placeholder).error(error).placeholder(placeholder).transform(transformation).into(view)
    }

    override fun picasso(img_url: String?, error: Int, placeholder: Int, view: ImageView, visibility: Int) {
        if (verifyString(img_url)) {
            Picasso.get().load(img_url).error(error).placeholder(placeholder).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, visibility: Int) {
        if (img_url != null) {
            Picasso.get().load(img_url).error(error).placeholder(placeholder).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String?, error: Int, placeholder: Int, view: ImageView, visibility: Int, transformation: Transformation) {
        if (verifyString(img_url)) {
            Picasso.get().load(img_url).error(error).placeholder(placeholder).transform(transformation).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, visibility: Int, transformation: Transformation) {
        if (img_url != null) {
            Picasso.get().load(img_url).error(error).placeholder(placeholder).transform(transformation).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String?, view: ImageView) {
        if (verifyString(img_url))
            Picasso.get().load(img_url).into(view)
    }

    override fun picasso(img_url: Uri, view: ImageView) {
        if (img_url != null)
            Picasso.get().load(img_url).into(view)
    }

    override fun picasso(img_url: String?, view: ImageView, transformation: Transformation) {
        if (verifyString(img_url))
            Picasso.get().load(img_url).transform(transformation).into(view)
    }

    override fun picasso(img_url: Uri, view: ImageView, transformation: Transformation) {
        if (img_url != null)
            Picasso.get().load(img_url).transform(transformation).into(view)
    }

    override fun picasso(img_url: String?, view: ImageView, visibility: Int) {
        if (verifyString(img_url)) {
            Picasso.get().load(img_url).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, visibility: Int) {
        if (img_url != null) {
            Picasso.get().load(img_url).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String?, view: ImageView, visibility: Int, transformation: Transformation) {
        if (verifyString(img_url)) {
            Picasso.get().load(img_url).transform(transformation).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, visibility: Int, transformation: Transformation) {
        if (img_url != null) {
            Picasso.get().load(img_url).transform(transformation).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String?, view: ImageView, resourceID: Int, isErrorImage: Boolean) {
        if (verifyString(img_url)) {
            if (isErrorImage) {
                Picasso.get().load(img_url).error(resourceID).into(view)
            } else {
                Picasso.get().load(img_url).placeholder(resourceID).into(view)
            }
        } else {
            Picasso.get().load(resourceID).into(view)
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean) {
        if (img_url != null) {
            if (isErrorImage) {
                Picasso.get().load(img_url).error(resourceID).into(view)
            } else {
                Picasso.get().load(img_url).placeholder(resourceID).into(view)
            }
        } else {
            Picasso.get().load(resourceID).into(view)
        }
    }

    override fun picasso(img_url: String?, view: ImageView, resourceID: Int, isErrorImage: Boolean, transformation: Transformation) {
        if (verifyString(img_url)) {
            if (isErrorImage) {
                Picasso.get().load(img_url).transform(transformation).error(resourceID).into(view)
            } else {
                Picasso.get().load(img_url).placeholder(resourceID).transform(transformation).into(view)
            }
        } else {
            Picasso.get().load(resourceID).transform(transformation).into(view)
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, transformation: Transformation) {
        if (img_url != null) {
            if (isErrorImage) {
                Picasso.get().load(img_url).transform(transformation).error(resourceID).into(view)
            } else {
                Picasso.get().load(img_url).placeholder(resourceID).transform(transformation).into(view)
            }
        } else {
            Picasso.get().load(resourceID).transform(transformation).into(view)
        }
    }

    override fun picasso(img_url: String?, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int) {
        if (verifyString(img_url)) {
            if (isErrorImage) {
                Picasso.get().load(img_url).error(resourceID).into(view)
            } else {
                Picasso.get().load(img_url).placeholder(resourceID).into(view)
            }
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int) {
        if (img_url != null) {
            if (isErrorImage) {
                Picasso.get().load(img_url).error(resourceID).into(view)
            } else {
                Picasso.get().load(img_url).placeholder(resourceID).into(view)
            }
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String?, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int, transformation: Transformation) {
        if (verifyString(img_url)) {
            if (isErrorImage) {
                Picasso.get().load(img_url).error(resourceID).transform(transformation).into(view)
            } else {
                Picasso.get().load(img_url).placeholder(resourceID).transform(transformation).into(view)
            }
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int, transformation: Transformation) {
        if (img_url != null) {
            if (isErrorImage) {
                Picasso.get().load(img_url).error(resourceID).transform(transformation).into(view)
            } else {
                Picasso.get().load(img_url).placeholder(resourceID).transform(transformation).into(view)
            }
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun glide(img_url: String?, view: ImageView) {
        if (verifyString(img_url)) {
            Glide.with(mContext!!).load(img_url).into(view)
        }
    }

    override fun glide(img_url: String?, view: ImageView, visiblity: Int) {
        if (verifyString(img_url)) {
            Glide.with(mContext!!).load(img_url).into(view)
            view.visibility = View.VISIBLE
        } else
            view.visibility = visiblity
    }

    //Toasts And Logs
    override fun toast(msg: String?) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resourceID: Int) {
        Toast.makeText(mContext, mContext!!.resources.getString(resourceID), Toast.LENGTH_SHORT).show()
    }

    override fun toast(msg: String?, time: Int) {
        Toast.makeText(mContext, msg, time).show()
    }

    override fun error_toast() {
        toast(R.string.error_toast_msg)
    }

    override fun log(msg: String?) {
        if (msg == null) {
            Log.d(TAG, "NULL")
        } else {
            Log.d(TAG, msg)
        }
    }

    override fun log(tag: String?, msg: String?) {
        if (msg == null) {
            Log.d(TAG, "NULL")
        } else {
            Log.d(tag, msg)
        }
    }

    override fun log(e: Exception) {
        if (e == null) {
            Log.d(TAG, "NULL")
        } else {
            Log.d(TAG, "Error", e)
        }
    }

    //Toasts And Logs
    override fun verifyString(str: String?): Boolean {
        return str != null && str.trim { it <= ' ' } != ""
    }

    override fun verifyString(str: SpannableStringBuilder): Boolean {
        return str != null && str != SpannableStringBuilder("")
    }

    override fun sanitizeString(str: String?): String? {
        return if (str != null && str.trim { it <= ' ' } != "") str else ""
    }

    override fun sanitizeString(str: SpannableStringBuilder): SpannableStringBuilder {
        return if (str != null && str != SpannableStringBuilder("")) str else SpannableStringBuilder("")
    }

    override fun sanitizeString(str: String?, view: View, visibility: Int): String? {
        if (str != null && str.trim { it <= ' ' } != "") {
            view.visibility = View.VISIBLE
            return str
        } else {
            view.visibility = visibility
            return ""
        }
    }

    override fun sanitizeString(str: SpannableStringBuilder, view: View, visibility: Int): SpannableStringBuilder {
        if (str != null && str != SpannableStringBuilder("")) {
            view.visibility = View.VISIBLE
            return str
        } else {
            view.visibility = visibility
            return SpannableStringBuilder("")
        }
    }


    override fun checkArray(`object`: ArrayList<*>): Boolean {
        return `object` != null && `object`.size > 0
    }
    //Screen Measurements


    //Shared Preferences Functions
    override fun getStringFromSharedPreferences(key: String?): String? {
        return if (shared_preferences != null)
            shared_preferences!!.getString(key, "")
        else
            ""
    }

    override fun getIntFromSharedPreferences(key: String?): Int {
        return if (shared_preferences != null)
            shared_preferences!!.getInt(key, 0)
        else
            0
    }

    override fun getBooleanFromSharedPreferences(key: String?): Boolean? {
        return if (shared_preferences != null)
            shared_preferences!!.getBoolean(key, false)
        else
            false
    }

    override fun getStringFromSharedPreferences(key: String?, common: String?): String? {
        return if (shared_preferences != null)
            shared_preferences!!.getString(key, common)
        else
            common
    }

    override fun getIntFromSharedPreferences(key: String?, common: Int): Int {
        return if (shared_preferences != null)
            shared_preferences!!.getInt(key, common)
        else
            common
    }

    override fun getBooleanFromSharedPreferences(key: String?, common: Boolean?): Boolean? {
        return if (shared_preferences != null)
            shared_preferences!!.getBoolean(key, common!!)
        else
            common
    }

    override fun storeStringInSharedPreferences(key: String?, value: String?) {
        if (shared_preferences != null)
            shared_preferences_editor?.putString(key, value)?.apply()
    }

    override fun storeIntInSharedPreferences(key: String?, value: Int) {
        if (shared_preferences != null)
            shared_preferences_editor?.putInt(key, value)?.apply()
    }

    override fun storeBooleanInSharedPreferences(key: String?, value: Boolean?) {
        if (shared_preferences != null)
            shared_preferences_editor?.putBoolean(key, value!!)?.apply()
    }

    override fun removeFromSharedPreferences(key: String?) {
        if (shared_preferences != null)
            shared_preferences_editor?.remove(key)?.apply()
    }

    companion object {


        //Screen Measurements
        val screenDensity: Float
            get() = Resources.getSystem().displayMetrics.density

        val screenHeight: Int
            get() = Resources.getSystem().displayMetrics.heightPixels

        val screenWidth: Int
            get() = Resources.getSystem().displayMetrics.widthPixels
    }
    //Shared Preferences Functions
}