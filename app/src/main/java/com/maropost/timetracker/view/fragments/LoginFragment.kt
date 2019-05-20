package com.maropost.timetracker.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.timetracker.R
import com.maropost.timetracker.utils.Utility
import com.maropost.timetracker.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : MPBaseFragment() {

    private var mView : View?= null
    private var loginViewModel : LoginViewModel ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.login_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(loginViewModel == null) {
            loginViewModel = LoginViewModel()
            loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
            observeLiveData()
            initialiseListeners()
        }
        showNavigationDrawer(false)
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
                    showProgressBar(false)
                    Utility.getInstance().showToast(activity!!, "Success")
                    replaceFragment(HomeFragment(),true)
                }
                LoginViewModel.LoginApiStatus.FAILURE -> {
                    showProgressBar(false)
                    Utility.getInstance().showToast(activity!!,"Failure")
                }
            }
        })

        // Login failure observer
        loginViewModel?.loginFailedResponse?.observe(this, Observer { loginFailedResponse ->
            //showSnackAlert(loginFailedResponse)
        })
    }

    /**
     * Check for empty email and password fields along with valid email content
     */
    private fun validateLoginFields() {
        loginViewModel?.validateLoginDetails(edtUsername.text.toString().trim(),edtPassword.text.toString().trim())
    }

    /**
     * Hit the login API
     */
    private fun consumeLoginApi() {
        showProgressBar(true)
        loginViewModel?.performLoginOperation(edtUsername.text.toString().trim(),edtPassword.text.toString().trim())
    }
}