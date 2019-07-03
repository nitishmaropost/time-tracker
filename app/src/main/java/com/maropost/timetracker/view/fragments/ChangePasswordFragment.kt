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
import com.maropost.timetracker.viewmodel.ChangePasswordViewModel
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.login_fragment.*

class ChangePasswordFragment : MPBaseFragment() {
    private var mView: View? = null
    private var changePasswordViewModel: ChangePasswordViewModel? = null
    private lateinit var handler: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null)
            mView = inflater.inflate(R.layout.fragment_change_password, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (changePasswordViewModel == null) {
            changePasswordViewModel = ChangePasswordViewModel()
            handler = Handler()
            changePasswordViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel::class.java)
            observeLiveData()
            initialiseListener()
        }
        showNavigationDrawer(false)
        showToolbar(false)
    }

    /**
     * Setup listeners
     */
    private fun initialiseListener() {
        btnSubmitPassword.setOnClickListener {
            validateChangePasswordFields()
        }

    }

    /**
     * Observe live data from View Model
     */
    private fun observeLiveData() {
        // Validation status observer
        changePasswordViewModel?.validationStatus?.observe(this, Observer { validationStatus ->
            when (validationStatus) {

                ChangePasswordViewModel.ValidationStatus.INCOMPLETE_OLD_PASSWORD -> edtOldPassword.error =
                    getString(R.string.enter_password)
                ChangePasswordViewModel.ValidationStatus.INCOMPLETE_NEW_PASSWORD -> edtNewPassword.error =
                    getString(R.string.enter_password)
                ChangePasswordViewModel.ValidationStatus.INCOMPLETE_CONFIRM_PASSWORD -> edtConfirmPassword.error =
                    getString(R.string.enter_password)
                ChangePasswordViewModel.ValidationStatus.PASSWORDS_UNMATCHED -> edtConfirmPassword.error =
                    getString(R.string.password_unmatched)
                ChangePasswordViewModel.ValidationStatus.VALID -> consumeChangePasswordApi()
            }
        })

        // Change Password Api status observer
        changePasswordViewModel?.changePasswordApiStatus?.observe(this, Observer { loginApiStatus ->
            when (loginApiStatus) {
                ChangePasswordViewModel.ChangePasswordApiStatus.SUCCESS -> {
                    lottieView.playAnimation()
                    handler.postDelayed({ replaceFragment(HomeFragment(), true) }, 1500)
                }
                ChangePasswordViewModel.ChangePasswordApiStatus.FAILURE -> {
                }
            }
        })

        // Change Password failure observer
        changePasswordViewModel?.changePasswordFailedResponse?.observe(this, Observer { changePasswordFailedResponse ->
            if (!TextUtils.isEmpty(changePasswordFailedResponse))
                showSnackAlert(changePasswordFailedResponse)
            else showSnackAlert("Error")
        })
    }

    /**
     * Check for empty password fields
     */
    fun validateChangePasswordFields() {
        changePasswordViewModel?.validatePassword(
            edtOldPassword.text.toString().trim(),
            edtNewPassword.text.toString().trim(),
            edtConfirmPassword.text.toString().trim()
        )
    }

    /**
     * Hit the login API
     */
    private fun consumeChangePasswordApi() {
        handler.postDelayed({
            changePasswordViewModel?.performChangePasswordOperation(
                edtOldPassword.text.toString().trim(),
                edtNewPassword.text.toString().trim(),
                "0"
            )
        }, 1000)

    }

}

