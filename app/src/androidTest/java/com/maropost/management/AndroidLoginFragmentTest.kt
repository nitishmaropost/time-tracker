package com.maropost.management

import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.maropost.management.time.view.fragments.LoginFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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