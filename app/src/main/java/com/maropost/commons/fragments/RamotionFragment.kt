package com.maropost.commons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.maropost.timetracker.R
import com.ramotion.circlemenu.CircleMenuView
import kotlinx.android.synthetic.main.fragment_ramotion.*


class RamotionFragment : MPBaseFragment() {
    private var mView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (mView == null)
            mView = inflater.inflate(R.layout.fragment_ramotion, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lockNavigationDrawer(false)
        showToolbar(false)
        initialiseListeners()
        setTitleAnimation()
    }

    private fun setTitleAnimation() {
        val bottomUp = AnimationUtils.loadAnimation(
            context,
            R.anim.top_down
        )
        txtTitle.startAnimation(bottomUp)
        txtTitle.visibility = View.VISIBLE
    }

    private fun initialiseListeners() {

        circle_menu.eventListener = object : CircleMenuView.EventListener() {
            override fun onMenuOpenAnimationStart(view: CircleMenuView) {
                imgHand.visibility = View.GONE
            }

            override fun onMenuCloseAnimationEnd(view: CircleMenuView) {
                imgHand.visibility = View.VISIBLE
            }

            override fun onButtonClickAnimationEnd(view: CircleMenuView, index: Int) {
                Log.d("D", "onButtonClickAnimationEnd| index: $index")
            }

        }
    }
}

