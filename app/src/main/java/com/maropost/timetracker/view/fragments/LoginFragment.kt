package com.maropost.timetracker.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.timetracker.R
import com.maropost.timetracker.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : MPBaseFragment() {

    private var mView : View?= null
    private var loginViewModel : LoginViewModel ?= null
    private lateinit var handler : Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.login_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(loginViewModel == null) {
            loginViewModel = LoginViewModel()
            handler = Handler()
            loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
            observeLiveData()
            initialiseListeners()
        }
        lockNavigationDrawer(true)
        showToolbar(false)
    }

    private fun initialiseListeners() {
        txtRegister.setOnClickListener{
            //replaceFragment(RegisterFragment(),true)
        }
        relDone.setOnClickListener{
            validateLoginFields()
        }
    }

    /**
     * Observe live data from View Model
     */
    private fun observeLiveData() {
        // Validation status observer
        loginViewModel?.validationStatus?.observe(this, Observer { validationStatus ->
            when(validationStatus){
                LoginViewModel.ValidationStatus.INCOMPLETE_EMAIL    -> edtUsername.error = getString(R.string.enter_email)
                LoginViewModel.ValidationStatus.INCOMPLETE_PASSWORD -> edtPassword.error = getString(R.string.enter_password)
                LoginViewModel.ValidationStatus.INVALID_EMAIL       -> edtUsername.error = getString(R.string.enter_valid_email)
                LoginViewModel.ValidationStatus.VALID               -> consumeLoginApi()
            }
        })

        // Login Api status observer
        loginViewModel?.loginApiStatus?.observe(this, Observer { loginApiStatus ->
            when(loginApiStatus){
                LoginViewModel.LoginApiStatus.SUCCESS -> {
                    setViewVisibilty(false,false,true)
                    lottieView.playAnimation()
                    handler.postDelayed({replaceFragment(HomeFragment(),true)},1500)
                }
                LoginViewModel.LoginApiStatus.FAILURE -> {
                    setViewVisibilty(true,false,false)
                }
            }
        })

        // Login failure observer
        loginViewModel?.loginFailedResponse?.observe(this, Observer { loginFailedResponse ->
            if(!TextUtils.isEmpty(loginFailedResponse))
            showSnackAlert(loginFailedResponse)
            else  showSnackAlert("Error")
        })
    }

    /**
     * Check for empty email and password fields along with valid email content
     */
    fun validateLoginFields() {
        loginViewModel?.validateLoginDetails(edtUsername.text.toString().trim(),edtPassword.text.toString().trim())
    }

    /**
     * Hit the login API
     */
    private fun consumeLoginApi() {
        setViewVisibilty(false,true,false)
        handler.postDelayed({loginViewModel?.performLoginOperation(edtUsername.text.toString().trim(),edtPassword.text.toString().trim())},1000)

    }

    /**
     * Set views visibility on service hit
     */
    private fun setViewVisibilty(showDone: Boolean, showProgress: Boolean, showSuccess: Boolean){
        when {
            showDone -> {
                relDone.visibility = View.VISIBLE
                relProgress.visibility = View.GONE
                relSuccess.visibility = View.GONE
            }
            showProgress -> {
                relDone.visibility = View.GONE
                relProgress.visibility = View.VISIBLE
                relSuccess.visibility = View.GONE
            }
            showSuccess -> {
                relDone.visibility = View.GONE
                relProgress.visibility = View.GONE
                relSuccess.visibility = View.VISIBLE
            }
        }
    }

}