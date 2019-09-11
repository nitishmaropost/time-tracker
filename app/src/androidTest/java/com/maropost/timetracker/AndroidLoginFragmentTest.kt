package com.maropost.timetracker

import android.content.Context
import android.os.Looper
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import android.util.Log
import com.maropost.timetracker.view.fragments.LoginFragment
import com.maropost.timetracker.viewmodel.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.logging.Handler

@RunWith(AndroidJUnit4::class)
@SmallTest
class AndroidLoginFragmentTest {
    private lateinit var loginFragment: LoginFragment

    @Before
    fun createLoginFragment() {
        loginFragment = LoginFragment()
    }

    @Test
    fun loginValidation() {
        loginFragment.apply {
            val email = "abc@maropost.com"
            val password = "abc@maropost.com"

            validateLoginFields()
            // assertEquals("VALID","INVALID")

        }

    }
}