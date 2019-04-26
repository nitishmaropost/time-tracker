package com.maropost.timetracker.view.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.maropost.timetracker.R
import com.maropost.timetracker.application.MyApplication
import com.maropost.timetracker.pojomodels.NavigationItem
import com.maropost.timetracker.view.adapters.NavigationAdapter
import com.maropost.timetracker.view.adapters.NavigationAdapterCallbacks
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.menu_left_drawer.*
import java.util.*
import kotlin.collections.ArrayList


open class MPBaseActivity : AppCompatActivity(), NavigationAdapterCallbacks {

    private var slidingRootNav: SlidingRootNav? = null
    private var navigationAdapter:NavigationAdapter ? = null
    private var itemList  = ArrayList<NavigationItem>()

    enum class TransactionType {
        REPLACE, ADD
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        loadNavigationData()
        setupRecyclerView()
        initialiseListener()

    }

    private fun setupRecyclerView(){
        // Creates a vertical Layout Manager
        val navigationItem = NavigationItem()
        navigationItem.itemName = "Home"
        navigationItem.itemImage = R.drawable.ic_calendar
        itemList.add(navigationItem)

        val navigationItem2 = NavigationItem()
        navigationItem2.itemName = "Calendar"
        navigationItem2.itemImage = R.drawable.ic_calendar
        itemList.add(navigationItem2)

        val navigationItem3 = NavigationItem()
        navigationItem3.itemName = "Logout"
        navigationItem3.itemImage = R.drawable.ic_calendar
        itemList.add(navigationItem3)


        recyclerNavigation.layoutManager = LinearLayoutManager(this)
        navigationAdapter = NavigationAdapter(itemList, this,this)
        recyclerNavigation.adapter = navigationAdapter
    }

    private fun loadNavigationData(){

        slidingRootNav = SlidingRootNavBuilder(this)
            .withToolbarMenuToggle(toolbar)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(false)
            .withMenuLayout(R.layout.menu_left_drawer)
            .inject()

        Glide
            .with(this)
            .load(R.drawable.default_profile_pic)
            .apply(RequestOptions.circleCropTransform())
            .into(navigationImageView)

        navigationImageView.setOnClickListener{
            Log.e("navigationImageView","")
        }


    }



    private fun initialiseListener() {
       imgToolbarRightIcon.setOnClickListener{
           val c = Calendar.getInstance()
           val year = c.get(Calendar.YEAR)
           val month = c.get(Calendar.MONTH)
           val day = c.get(Calendar.DAY_OF_MONTH)
           val datePickerDialog = DatePickerDialog(this,
               DatePickerDialog.OnDateSetListener { datePicker, mYear, mMonth, mDay ->
                   MyApplication.getInstance().setCalenderDetails(mYear,mMonth,mDay)
               }, year, month, day
           )
           datePickerDialog.show()
       }
    }


    /**
     * Set toolbar visibility
     */
    fun showToolbar(allow: Boolean) {
        if (allow)
            toolbarFrame.visibility = View.VISIBLE
        else
            toolbarFrame.visibility = View.GONE
    }

    /**
     * Intercept on back click
     */
    override fun onBackPressed() {
        if (getFragmentCount() == 1)
            finish()
        else
            super.onBackPressed()
    }

    /**
     * Get back stack fragment count
     */
    private fun getFragmentCount(): Int {
        return supportFragmentManager.backStackEntryCount
    }

    /**
     * Get the current displaying fragment
     */
  /*  fun getCurrentFragment(): Fragment {
        return supportFragmentManager.findFragmentById(R.id.container)!!
    }
*/
    /**
     * Display a snack message
     */
    fun showSnackAlert(message: String) {
        try {
            Snackbar.make(mainContainer, message, Snackbar.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Display permission denied alert
     */
    fun showPermissionSnackAlert() {
        val snackBar = Snackbar.make(mainContainer, "Access denied!!", Snackbar.LENGTH_SHORT)
                .setAction("t") {
                    val i = Intent()
                    i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    i.addCategory(Intent.CATEGORY_DEFAULT)
                    i.data = Uri.parse("package:$packageName")
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    startActivity(i)
                }
        snackBar.show()
        val snackView = snackBar.view
        val action = snackView.findViewById(R.id.snackbar_action) as Button
        action.text = ""
    }

    /**
     * Replace current fragment with new one
     * addToBackStack - true/false keep it in back stack or not
     */
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        inflateFragment(fragment, addToBackStack, TransactionType.REPLACE)
    }

    /**
     * Replace current fragment with new one along with Shared Element Transaction
     * addToBackStack - true/false keep it in back stack or not
     */
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, view: View) {
        inflateFragment(fragment, addToBackStack, TransactionType.REPLACE, view)
    }

    /**
     * Set your fragment layout in the container
     */
    private fun inflateFragment(fragment: Fragment, addToBackStack: Boolean, transactionType: TransactionType) {
        val transaction = supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.simpleName
        if (addToBackStack)
            transaction.addToBackStack(tag)
        when (transactionType) {
            TransactionType.REPLACE -> {
                transaction.replace(R.id.mainContainer, fragment, tag)
            }
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * Set your fragment layout in the container - this method used for transition element animation
     * Could also be used if any other animation needs to be implemented. Apply check based on animation enum type
     */
    private fun inflateFragment(fragment: Fragment, addToBackStack: Boolean, transactionType: TransactionType, view: View?) {
        val transaction = supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.simpleName
        if (view != null)
            transaction.addSharedElement(view, ViewCompat.getTransitionName(view)!!)
        if (addToBackStack)
            transaction.addToBackStack(tag)
        when (transactionType) {
            TransactionType.REPLACE -> {
                transaction.replace(R.id.mainContainer, fragment, tag)
            }
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * Pop current displaying fragment
     */
    fun popCurrentFragment() {
        try {
            supportFragmentManager.popBackStack()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Pop all fragments from stack
     */
    fun popAllFragments() {
        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            try {
                fm.popBackStack()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Remove fragment with tag in back stack
     */
    fun popFragmentByTag(tag: String) {
        try {
            supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Control navigation drawer visibility
     */
    fun showNavigationDrawer(allow: Boolean) {
       /* if (allow)
            mDrawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_UNLOCKED)
        else
            mDrawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_LOCKED_CLOSED)*/
    }

    /**
     * Set progress bar display
     */
    fun showProgressBar(isAllow: Boolean) {
        if (isAllow)
            progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

}





