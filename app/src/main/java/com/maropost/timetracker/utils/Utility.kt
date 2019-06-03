package com.maropost.timetracker.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import android.net.ConnectivityManager
import com.maropost.timetracker.application.MyApplication

class Utility()  {
    var alertDialog : AlertDialog ?= null

    companion object {
        private var instance: Utility? = null
        fun getInstance() : Utility {
            if (instance == null)
                instance = Utility()
            return instance as Utility
        }
    }

    enum class WindowState {
        ADJUST_RESIZE,
        ADJUST_PAN
    }


    /**
     * Standard toast method
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    /**
     * Standard method to display a prompt
     */
    fun showAlertDialog(context: Context,
                        title: String,
                        message: String,
                        positiveButtonText: String,
                        negativeButtonText: String,
                        isCancellable:Boolean,
                        onPositiveClickEvent: DialogInterface.OnClickListener?,
                        onNegativeClickEvent: DialogInterface.OnClickListener?) {
        try {
            if(alertDialog == null) {
                val alertBuilder = AlertDialog.Builder(context)
                // Set dialog title
                if(!TextUtils.isEmpty(title))
                    alertBuilder.setTitle(title)
                // Set dialog message
                if(!TextUtils.isEmpty(message))
                    alertBuilder.setMessage(message)
                if(onPositiveClickEvent != null && !TextUtils.isEmpty(positiveButtonText))
                    alertBuilder.setPositiveButton(positiveButtonText, onPositiveClickEvent)
                if(onNegativeClickEvent != null && !TextUtils.isEmpty(negativeButtonText))
                    alertBuilder.setNegativeButton(negativeButtonText, onNegativeClickEvent)
                alertBuilder.setCancelable(isCancellable)
                alertDialog = alertBuilder.create()
                alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                alertDialog!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if(alertDialog != null && alertDialog!!.isShowing)
                alertDialog!!.dismiss()
            alertDialog = null
        }
    }

    /**
     * Dismiss the alert if opened
     */
    fun dismissAlertDialog(){
        if(alertDialog != null){
            alertDialog!!.dismiss()
            alertDialog = null
        }
    }

    /**
     * Common method to print logs throughout the application
     */
    fun printLog(tag: String, message: String){
        Log.d(tag, message)
    }

    /**
     * Check if the email entered is valid
     */
    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    /**
     * Show/Hide soft keyboard
     */
    fun setSoftKeyboardState(context: Context,view: View,isAllow: Boolean){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(isAllow)
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT) // open
        else imm.hideSoftInputFromWindow(view.windowToken, 0)   // close
    }

    /**
     * Set window state for each screen if required
     */
    fun setWindowState(activity: Activity, windowState: WindowState){
        when(windowState){
            WindowState.ADJUST_RESIZE -> activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            WindowState.ADJUST_PAN -> activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }

    /**
     * Convert date to epoch time
     */
    @SuppressLint("SimpleDateFormat")
    fun getEpochTime(date: String) : String {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        var d: Date? = null
        try {
            d = input.parse(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return output.format(d)
    }

     fun isNetworkConnected(): Boolean {
        val cm = MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null
    }

    fun convertDateToMillis(date: String): Long {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        try {
            val mDate = sdf.parse(date)
            return mDate.time
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

     fun getFormattedDate(calendar: Calendar):String{
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.format(calendar.time)
    }
}



