package com.maropost.timetracker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maropost.timetracker.R
import com.maropost.timetracker.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : MPBaseFragment() {

    private var mView : View?= null
    private val loginViewModel = LoginViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mView == null)
            mView = inflater.inflate(R.layout.login_fragment, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavigationDrawer(false)
        showToolbar(false)
        initialiseListeners()
    }

    private fun initialiseListeners() {
        txtRegister.setOnClickListener{
            //replaceFragment(RegisterFragment(),true)

        }
        relDone.setOnClickListener{
            loginViewModel.performLoginOperation("","")
        }
    }
}