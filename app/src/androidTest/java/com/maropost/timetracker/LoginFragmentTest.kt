package com.maropost.timetracker

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.util.Patterns
import com.maropost.timetracker.application.MyApplication
import junit.framework.Assert.assertEquals
import org.junit.Test
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat


class LoginFragmentTest {

    @Test
    @Throws(Exception::class)
    fun testLoginCredentials() {
        var email = ""
        var password = ""

        //empty email validation
        if (TextUtils.isEmpty(email))
            assertEquals("", email)

        //empty password validation
        if (TextUtils.isEmpty(password))
            assertEquals("", password)

        //email pattern validation
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            assertEquals("true", email)

        //internet connectivity check
        val permissionCheck =
            ContextCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.ACCESS_NETWORK_STATE)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val cm = MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnected!!
            if (isConnected)
                assertEquals(true, isConnected)
        }
    }
}