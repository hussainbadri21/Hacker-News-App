package com.example.hussain.hny.defaults


import android.net.Uri
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Transformation
import java.util.ArrayList


interface ArchitecturalTemplate {

    val isNetworkConnected: Boolean
    /**
     * Initialize essential components and
     * Handle intents
     */
    fun onCreate()


    /**
     * Initializes Shared Preferences, retrofit wrapper
     * and configure analytics
     */
    fun onPageStart()


    /**
     * For custom initializations
     */
    fun customInitializer()


    /**
     * Configure Database's
     */
    fun configureDB()


    /**
     * Configure API Clients
     */
    fun configureAPIClient()




    /**
     * Define building blocks such as
     * setup and initialize firebase,
     * update check, listeners, fragments
     * and adapters
     */
    fun defineUIComponentsAndAdapters()


    //Override and define what needs to be reloaded
    fun reload()


    @ColorInt
    fun getColorVal(@ColorRes resourceID: Int): Int


    /**
     * @param view-TextView where text is being set
     * @param msg           - text to be set
     */
    fun setText(view: TextView, msg: String?)

    /**
     * @param view-TextView where text is being set
     * @param msg           - text to be set
     * @param visiblity     - visiblity on base of String? being empty or null
     */
    fun setText(view: TextView, msg: String?, visiblity: Int)

    /**
     * @param view-TextView where text is being set
     * @param msg           - text to be set
     * @param viewtohide    - View to hide
     * @param visiblity     - visiblity on base of String? being empty or null
     */
    fun setText(view: TextView, msg: String?, viewtohide: View, visiblity: Int)


    /**
     * @param view-TextView where text is being set
     * @param msg           - text to be set
     */
    fun setText(view: TextView, msg: SpannableStringBuilder)

    /**
     * @param view-TextView where text is being set
     * @param msg           - text to be set
     * @param visiblity     - visiblity on base of String? being empty or null
     */
    fun setText(view: TextView, msg: SpannableStringBuilder, visiblity: Int)

    /**
     * @param view-TextView where text is being set
     * @param msg           - text to be set
     * @param viewtohide    - View to hide
     * @param visiblity     - visiblity on base of String? being empty or null
     */
    fun setText(view: TextView, msg: SpannableStringBuilder, viewtohide: View, visiblity: Int)

    //Picasso

    /**
     * @param img_url     - ImageURL to be added
     * @param error       - error image ResourceID
     * @param placeholder - placeholder image ResourceID
     * @param view        - Target View
     */
    fun picasso(img_url: String?, error: Int, placeholder: Int, view: ImageView)

    fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView)

    /**
     * @param img_url        - ImageURL to be added
     * @param error          - error image ResourceID
     * @param placeholder    - placeholder image ResourceID
     * @param view           - Target View
     * @param transformation - transformation
     */
    fun picasso(img_url: String?, error: Int, placeholder: Int, view: ImageView, transformation: Transformation)

    fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, transformation: Transformation)

    /**
     * @param img_url     - ImageURL to be added
     * @param error       - error image ResourceID
     * @param placeholder - placeholder image ResourceID
     * @param view        - Target View
     * @param visibility  - visiblity if img_url is null or empty
     */
    fun picasso(img_url: String?, error: Int, placeholder: Int, view: ImageView, visibility: Int)

    fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, visibility: Int)

    /**
     * @param img_url        - ImageURL to be added
     * @param error          - error image ResourceID
     * @param placeholder    - placeholder image ResourceID
     * @param view           - Target View
     * @param visibility     - visiblity if img_url is null or empty
     * @param transformation - transformation
     */
    fun picasso(img_url: String?, error: Int, placeholder: Int, view: ImageView, visibility: Int, transformation: Transformation)

    fun picasso(img_url: Uri, error: Int, placeholder: Int, view: ImageView, visibility: Int, transformation: Transformation)

    /**
     * @param img_url- ImageURL to be added
     * @param view     - Target View
     */
    fun picasso(img_url: String?, view: ImageView)

    fun picasso(img_url: Uri, view: ImageView)

    fun picasso(img_url: String?, view: ImageView, transformation: Transformation)

    fun picasso(img_url: Uri, view: ImageView, transformation: Transformation)

    /**
     * @param img_url-   ImageURL to be added
     * @param view       - Target View
     * @param visibility - visiblity if img_url is null or empty
     */
    fun picasso(img_url: String?, view: ImageView, visibility: Int)

    fun picasso(img_url: Uri, view: ImageView, visibility: Int)

    fun picasso(img_url: String?, view: ImageView, visibility: Int, transformation: Transformation)

    fun picasso(img_url: Uri, view: ImageView, visibility: Int, transformation: Transformation)

    /**
     * @param img_url      -   ImageURL to be added
     * @param view         - Target View
     * @param resourceID   - ResourceID for error or placeholder
     * @param isErrorImage - 0 for Placeholder, 1 for Error Image
     */
    fun picasso(img_url: String?, view: ImageView, resourceID: Int, isErrorImage: Boolean)

    fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean)

    fun picasso(img_url: String?, view: ImageView, resourceID: Int, isErrorImage: Boolean, transformation: Transformation)

    fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, transformation: Transformation)

    /**
     * @param img_url      -   ImageURL to be added
     * @param view         - Target View
     * @param resourceID   - ResourceID for error or placeholder
     * @param isErrorImage - 0 for Placeholder, 1 for Error Image
     * @param visibility   - visiblity if img_url is null or empty
     */
    fun picasso(img_url: String?, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int)

    fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int)

    fun picasso(img_url: String?, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int, transformation: Transformation)

    fun picasso(img_url: Uri, view: ImageView, resourceID: Int, isErrorImage: Boolean, visibility: Int, transformation: Transformation)

    fun glide(img_url: String?, view: ImageView)

    fun glide(img_url: String?, view: ImageView, visiblity: Int)

    /**
     * Default toast
     *
     * @param msg - Message which needs to be displayed as Toast
     */
    fun toast(msg: String?)

    fun error_toast()

    fun toast(resourceID: Int)


    /**
     * Timed toast
     *
     * @param msg  - Message which needs to be displayed as Toast
     * @param time - Duration of Toast
     */
    fun toast(msg: String?, time: Int)


    /**
     * Default log
     *
     * @param msg - String? to be logged
     */
    fun log(msg: String?)


    /**
     * Custom log
     *
     * @param tag - Custom TAG for logs
     * @param msg - String? to be logged
     */
    fun log(tag: String?, msg: String?)


    /**
     * Exception log
     *
     * @param e - Typ of Exception
     */
    fun log(e: Exception)


    /**
     * Check for null String? and sanitize it
     *
     * @param str - String? to check for null
     * @return null if String? is null else return sanitized String?
     */
    fun verifyString(str: String?): Boolean

    /**
     * Check for null String? and sanitize it
     *
     * @param str - String? to check for null
     * @return null if String? is null else return sanitized String?
     */
    fun verifyString(str: SpannableStringBuilder): Boolean

    /**
     * @param str- String? to check for null and inserting in a view
     * @return empty if String? is null else return sanitized String?
     */
    fun sanitizeString(str: String?): String?

    /**
     * @param str- String? to check for null and inserting in a view
     * @return empty if String? is null else return sanitized String?
     */
    fun sanitizeString(str: SpannableStringBuilder): SpannableStringBuilder

    /**
     * Check for null String? and sanitize it, handle view visibility if String? is null
     *
     * @param str        - String? to check for null
     * @param view       - View to handle if String? is null
     * @param visibility - Visiblity of view
     * @return null if String? is null else return sanitized String?
     */
    fun sanitizeString(str: String?, view: View, visibility: Int): String?

    /**
     * Check for null String? and sanitize it, handle view visibility if String? is null
     *
     * @param str        - String? to check for null
     * @param view       - View to handle if String? is null
     * @param visibility - Visiblity of view
     * @return null if String? is null else return sanitized String?
     */
    fun sanitizeString(str: SpannableStringBuilder, view: View, visibility: Int): SpannableStringBuilder


    /**
     * Null check for ArrayList
     *
     * @param object ArrayList to check for null
     * @return null if array is null else return array
     */
    fun checkArray(`object`: ArrayList<*>): Boolean


    //Shared Preferences Functions

    /**
     * Getter for String? value from shared preferences
     *
     * @param key to retrieve corresponding String? value from Shared Preferences
     * @return String? obtained from shared preferences if not null else default value
     */
    fun getStringFromSharedPreferences(key: String?): String?


    /**
     * Getter for int value from shared preferences
     *
     * @param key to retrieve corresponding Integer value from Shared Preferences
     * @return int obtained from shared preferences if not null else default value
     */
    fun getIntFromSharedPreferences(key: String?): Int


    /**
     * Getter for boolean value from shared preferences
     *
     * @param key to retrieve corresponding Boolean value from Shared Preferences
     * @return boolean obtained from shared preferences if not null else default value
     */
    fun getBooleanFromSharedPreferences(key: String?): Boolean?


    /**
     * Custom Getter for String? value from shared preferences
     *
     * @param key    to retrieve corresponding String? value from Shared Preferences
     * @param common default value if shared preferences is null or key does not exist
     * @return String? obtained from shared preferences if not null else common
     */
    fun getStringFromSharedPreferences(key: String?, common: String?): String?


    /**
     * Custom Getter for int value from shared preferences
     *
     * @param key    to retrieve corresponding Integer value from Shared Preferences
     * @param common default value if shared preferences is null or key does not exist
     * @return int obtained from shared preferences if not null else common
     */
    fun getIntFromSharedPreferences(key: String?, common: Int): Int


    /**
     * Custom Getter for boolean value from shared preferences
     *
     * @param key    to retrieve corresponding Boolean value from Shared Preferences
     * @param common default value if shared preferences is null or key does not exist
     * @return boolean obtained from shared preferences if not null else common
     */
    fun getBooleanFromSharedPreferences(key: String?, common: Boolean?): Boolean?


    /**
     * Setter for String? value for shared preferences
     *
     * @param key   to set String? value for Shared Preferences
     * @param value to be stored in Shared Preferences
     */
    fun storeStringInSharedPreferences(key: String?, value: String?)


    /**
     * Setter for integer value for shared preferences
     *
     * @param key   to set int value for Shared Preferences
     * @param value to be stored in Shared Preferences
     */
    fun storeIntInSharedPreferences(key: String?, value: Int)


    /**
     * Setter for boolean value for shared preferences
     *
     * @param key   to set boolean value for Shared Preferences
     * @param value to be stored in Shared Preferences
     */
    fun storeBooleanInSharedPreferences(key: String?, value: Boolean?)


    /**
     * To clear shared preference
     *
     * @param key whose value needs to be cleared
     */
    fun removeFromSharedPreferences(key: String?)
    //Shared Preferences Functions
}
