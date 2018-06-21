package com.example.hussain.hny.defaults

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.crashlytics.android.Crashlytics
import com.example.hussain.hny.R
import com.example.hussain.hny.utils.RetrofitWrapper
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

import java.util.ArrayList
import retrofit2.Retrofit

@SuppressLint("Registered")
open class ActivityArchitecture : AppCompatActivity(), ArchitecturalTemplate {

    var TAG = "DefaultActivity"
    var shared_preferences: SharedPreferences? = null
    var activity: Activity = this
    var shared_preferences_editor: SharedPreferences.Editor?=null
    var mContext: Context? = null
    var retrofit: Retrofit?=null

    public val Context.picasso: Picasso
        get() = Picasso.get()

    override val isNetworkConnected: Boolean
        get() {
            if (mContext != null) {
                val cm = mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return cm?.activeNetworkInfo != null
            }
            return false
        }

    override fun onCreate() {
        onPageStart()
        defineUIComponentsAndAdapters()
    }

    override fun onPageStart() {
        mContext = applicationContext
        if (verifyString(this.javaClass.simpleName))
            TAG = this.javaClass.simpleName
        configureAPIClient()
        customInitializer()
        resolveIntent()

    }

    override fun configureDB() {
        shared_preferences = applicationContext.getSharedPreferences("Chalkst_Pref", Context.MODE_PRIVATE)
        shared_preferences_editor = shared_preferences!!.edit()
    }

    override fun configureAPIClient() {
        configureDB()
        retrofit = RetrofitWrapper.companion.getRetrofitRequest(applicationContext, shared_preferences!!, true)

    }


    override fun customInitializer() {

    }


    override fun defineUIComponentsAndAdapters() {

    }

    //override this and resolve intents
    fun resolveIntent() {

    }

    //Override and define what needs to be reloaded
    override fun reload() {

    }

    @ColorInt
    override fun getColorVal(@ColorRes resourceID: Int): Int {
        return ContextCompat.getColor(mContext!!, resourceID)
    }


    override fun setText(view: TextView, msg: String) {
        view.text = sanitizeString(msg)
    }

    override fun setText(view: TextView, msg: String, visiblity: Int) {
        view.text = sanitizeString(msg, view, visiblity)
    }

    override fun setText(view: TextView, msg: String, viewtohide: View, visiblity: Int) {
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


    override fun picasso(img_url: String, error: Int, placeholder: Int, view: ImageView) {
        if (verifyString(img_url))
            picasso.load(img_url).error(error).placeholder(placeholder).into(view)
        else
            picasso.load(placeholder).error(error).placeholder(placeholder).into(view)
    }

    override fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView) {
        if (img_url != null)
            picasso.load(img_url).error(error).placeholder(placeholder).into(view)
        else
            picasso.load(placeholder).error(error).placeholder(placeholder).into(view)
    }

    override fun picasso(img_url: String, error: Int, placeholder: Int, view: ImageView, transformation: Transformation) {
        if (verifyString(img_url))
            picasso.load(img_url).error(error).placeholder(placeholder).transform(transformation).into(view)
        else
            picasso.load(placeholder).error(error).placeholder(placeholder).transform(transformation).into(view)
    }

    override fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, transformation: Transformation) {
        if (img_url != null)
            picasso.load(img_url).error(error).placeholder(placeholder).transform(transformation).into(view)
        else
            picasso.load(placeholder).error(error).placeholder(placeholder).transform(transformation).into(view)
    }

    override fun picasso(img_url: String, error: Int, placeholder: Int, view: ImageView, visibility: Int) {
        if (verifyString(img_url)) {
            picasso.load(img_url).error(error).placeholder(placeholder).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, visibility: Int) {
        if (img_url != null) {
            picasso.load(img_url).error(error).placeholder(placeholder).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String, error: Int, placeholder: Int, view: ImageView, visibility: Int, transformation: Transformation) {
        if (verifyString(img_url)) {
            picasso.load(img_url).error(error).placeholder(placeholder).transform(transformation).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, visibility: Int, transformation: Transformation) {
        if (img_url != null) {
            picasso.load(img_url).error(error).placeholder(placeholder).transform(transformation).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String, view: ImageView) {
        if (verifyString(img_url))
            picasso.load(img_url).into(view)
    }

    override fun picasso(img_url: Uri, view: ImageView) {
        if (img_url != null)
            picasso.load(img_url).into(view)
    }

    override fun picasso(img_url: String, view: ImageView, transformation: Transformation) {
        if (verifyString(img_url))
            picasso.load(img_url).transform(transformation).into(view)
    }

    override fun picasso(img_url: Uri, view: ImageView, transformation: Transformation) {
        if (img_url != null)
            picasso.load(img_url).transform(transformation).into(view)
    }

    override fun picasso(img_url: String, view: ImageView, visibility: Int) {
        if (verifyString(img_url)) {
            picasso.load(img_url).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, visibility: Int) {
        if (img_url != null) {
            picasso.load(img_url).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String, view: ImageView, visibility: Int, transformation: Transformation) {
        if (verifyString(img_url)) {
            picasso.load(img_url).transform(transformation).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, visibility: Int, transformation: Transformation) {
        if (img_url != null) {
            picasso.load(img_url).transform(transformation).into(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String, view: ImageView, resourceID: Int, isErrorImage: Boolean) {
        if (verifyString(img_url)) {
            if (isErrorImage) {
                picasso.load(img_url).error(resourceID).into(view)
            } else {
                picasso.load(img_url).placeholder(resourceID).into(view)
            }
        } else {
            picasso.load(resourceID).into(view)
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean) {
        if (img_url != null) {
            if (isErrorImage) {
                picasso.load(img_url).error(resourceID).into(view)
            } else {
                picasso.load(img_url).placeholder(resourceID).into(view)
            }
        } else {
            picasso.load(resourceID).into(view)
        }
    }

    override fun picasso(img_url: String, view: ImageView, resourceID: Int, isErrorImage: Boolean, transformation: Transformation) {
        if (verifyString(img_url)) {
            if (isErrorImage) {
                picasso.load(img_url).transform(transformation).error(resourceID).into(view)
            } else {
                picasso.load(img_url).placeholder(resourceID).transform(transformation).into(view)
            }
        } else {
            picasso.load(resourceID).transform(transformation).into(view)
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, transformation: Transformation) {
        if (img_url != null) {
            if (isErrorImage) {
                picasso.load(img_url).transform(transformation).error(resourceID).into(view)
            } else {
                picasso.load(img_url).placeholder(resourceID).transform(transformation).into(view)
            }
        } else {
            picasso.load(resourceID).transform(transformation).into(view)
        }
    }

    override fun picasso(img_url: String, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int) {
        if (verifyString(img_url)) {
            if (isErrorImage) {
                picasso.load(img_url).error(resourceID).into(view)
            } else {
                picasso.load(img_url).placeholder(resourceID).into(view)
            }
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int) {
        if (img_url != null) {
            if (isErrorImage) {
                picasso.load(img_url).error(resourceID).into(view)
            } else {
                picasso.load(img_url).placeholder(resourceID).into(view)
            }
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: String, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int, transformation: Transformation) {
        if (verifyString(img_url)) {
            if (isErrorImage) {
                picasso.load(img_url).error(resourceID).transform(transformation).into(view)
            } else {
                picasso.load(img_url).placeholder(resourceID).transform(transformation).into(view)
            }
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int, transformation: Transformation) {
        if (img_url != null) {
            if (isErrorImage) {
                picasso.load(img_url).error(resourceID).transform(transformation).into(view)
            } else {
                picasso.load(img_url).placeholder(resourceID).transform(transformation).into(view)
            }
            view.visibility = View.VISIBLE
        } else {
            view.visibility = visibility
        }
    }

    override fun glide(img_url: String, view: ImageView) {
        if (verifyString(img_url)) {
            Glide.with(mContext!!).load(img_url).into(view)
        }
    }

    override fun glide(img_url: String, view: ImageView, visiblity: Int) {
        if (verifyString(img_url)) {
            Glide.with(mContext!!).load(img_url).into(view)
            view.visibility = View.VISIBLE
        } else
            view.visibility = visiblity
    }

    fun hideKeyboard() {
        try {
            val view = findViewById<View>(android.R.id.content)
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (e: Exception) {
            log(e)
        }

    }

    override fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resourceID: Int) {
        Toast.makeText(this, mContext!!.resources.getString(resourceID), Toast.LENGTH_SHORT).show()
    }

    override fun toast(msg: String, time: Int) {
        Toast.makeText(this, msg, time).show()
    }

    override fun error_toast() {
        toast(R.string.error_toast_msg)
    }

    override fun log(msg: String) {
        if (msg == null) {
            Log.d(TAG, "NULL")
        } else {
            Log.d(TAG, msg)
        }
    }

    override fun log(tag: String, msg: String) {
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
            Crashlytics.logException(e)
        }
    }

    override fun verifyString(str: String): Boolean {
        return str != null && str.trim { it <= ' ' } != ""
    }

    override fun verifyString(str: SpannableStringBuilder): Boolean {
        return str != null && str != SpannableStringBuilder("")
    }

    override fun sanitizeString(str: String): String {
        return if (str != null && str.trim { it <= ' ' } != "") str else ""
    }

    override fun sanitizeString(str: SpannableStringBuilder): SpannableStringBuilder {
        return if (str != null && str != SpannableStringBuilder("")) str else SpannableStringBuilder("")
    }

    override fun sanitizeString(str: String, view: View, visibility: Int): String {
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
    //Get Intent Function

    /**
     * Getter for string value from intent
     *
     * @param key to retrieve corresponding String value from intent
     * @return null if intent is null or return string obtained from intent
     */
    fun getIntentString(key: String): String? {
        return if (intent != null && intent.getStringExtra(key) != null && intent.getStringExtra(key) != "") {
            intent.getStringExtra(key)
        } else
            null
    }

    /**
     * Getter for int value from intent
     *
     * @param key    to retrieve corresponding Integer value from intent
     * @param common default value if intent is null or key does not exist
     * @return int obtained from intent
     */
    fun getIntentInt(key: String, common: Int): Int {
        return intent.getIntExtra(key, common)
    }

    /**
     * Getter for boolean value from intent
     *
     * @param key    to retrieve corresponding Boolean value from intent
     * @param common default value if intent is null or key does not exist
     * @return boolean obtained from intent
     */
    fun getIntentBool(key: String, common: Boolean?): Boolean? {
        return intent.getBooleanExtra(key, common!!)
    }
    //Get Intent Function


    //Shared Preferences Functions
    override fun getStringFromSharedPreferences(key: String): String {
        return if (shared_preferences != null)
            shared_preferences!!.getString(key, "")
        else
            ""
    }


    override fun getIntFromSharedPreferences(key: String): Int {
        return if (shared_preferences != null)
            shared_preferences!!.getInt(key, 0)
        else
            0
    }


    override fun getBooleanFromSharedPreferences(key: String): Boolean? {
        return shared_preferences != null && shared_preferences!!.getBoolean(key, false)
    }


    override fun getStringFromSharedPreferences(key: String, common: String): String {
        return if (shared_preferences != null)
            shared_preferences!!.getString(key, common)
        else
            common
    }


    override fun getIntFromSharedPreferences(key: String, common: Int): Int {
        return if (shared_preferences != null)
            shared_preferences!!.getInt(key, common)
        else
            common
    }


    override fun getBooleanFromSharedPreferences(key: String, common: Boolean?): Boolean? {
        return if (shared_preferences != null)
            shared_preferences!!.getBoolean(key, common!!)
        else
            common
    }

    override fun storeStringInSharedPreferences(key: String, value: String) {
        if (shared_preferences != null)
            shared_preferences_editor?.putString(key, value)?.apply()
    }

    override fun storeIntInSharedPreferences(key: String, value: Int) {
        if (shared_preferences != null)
            shared_preferences_editor?.putInt(key, value)?.apply()
    }

    override fun storeBooleanInSharedPreferences(key: String, value: Boolean?) {
        if (shared_preferences != null)
            shared_preferences_editor?.putBoolean(key, value!!)?.apply()
    }

    override fun removeFromSharedPreferences(key: String) {
        if (shared_preferences != null)
            shared_preferences_editor?.remove(key)?.apply()
    }

    companion object {

        val screenDensity: Float
            get() = Resources.getSystem().displayMetrics.density

        val screenHeight: Int
            get() = Resources.getSystem().displayMetrics.heightPixels

        val screenWidth: Int
            get() = Resources.getSystem().displayMetrics.widthPixels
    }


    //Shared Preferences Functions

}