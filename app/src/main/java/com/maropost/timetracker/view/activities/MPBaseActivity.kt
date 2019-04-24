package com.maropost.timetracker.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.Button
import com.maropost.enterprise.pojomodels.MenuChildModel
import com.maropost.enterprise.pojomodels.MenuModel
import com.maropost.timetracker.R
import com.maropost.timetracker.view.adapters.ExpandableListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


open class MPBaseActivity : AppCompatActivity() {
    private var expandableListAdapter: ExpandableListAdapter? = null
    private var listDataHeader = ArrayList<MenuModel>()
    private var listDataChild = HashMap<String, ArrayList<MenuChildModel>>()
    private var listChild = ArrayList<MenuChildModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupNavigationView()
    }

    /**
     * Navigation settings and data placement
     */
    private fun setupNavigationView() {
        mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        prepareMenuData()
        populateExpandableList()
        initialiseNavigationItemItemListeners()
        imgToolbarLeftIcon.setOnClickListener { mDrawerLayout.openDrawer(Gravity.START) }
    }



    enum class TransactionType {
        REPLACE, ADD
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
     * Get back stack fragment count
     */
    private fun getFragmentCount(): Int {
        return supportFragmentManager.backStackEntryCount
    }

    /**
     * Get the current displaying fragment
     */
    fun getCurrentFragment(): Fragment {
        return supportFragmentManager.findFragmentById(R.id.container)!!
    }

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
        val action = snackView.findViewById(android.support.design.R.id.snackbar_action) as Button
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
     * Set toolbar title
     */
    fun setTitle(title: String) {
        toolbar.title = ""
        toolbarTitle.text = title
    }

    /**
     * Set Toolbar left icon
     */
    fun setToolbarLeftIcon(resId: Int) {
        imgToolbarLeftIcon.setImageResource(resId)
    }

    /**
     * Set Toolbar right icon
     */
    fun setToolbarRightIcon(resId: Int) {
        imgToolbarRightIcon.setImageResource(resId)
    }

    /**
     * Set Toolbar filter icon
     */
    fun setToolbarFilterIcon(resId: Int) {
        imgToolbarFilterIcon.setImageResource(resId)
    }


    /**
     * Control navigation drawer visibility
     */
    fun showNavigationDrawer(allow: Boolean) {
        if (allow)
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        else
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * Set progress bar display
     */
    fun showProgressBar(isAllow: Boolean) {
        if (isAllow)
            progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    /*
    * Expandable List menu */
    private fun prepareMenuData() {
        // Adding Group data
        val groupMenuFirst = MenuModel()
        groupMenuFirst.groupName = getString(R.string.nav_home)
        groupMenuFirst.groupIcon = R.drawable.ic_home
        groupMenuFirst.groupArrowStatus = MenuModel.GroupStatus.COLLAPSED
        listDataHeader.add(groupMenuFirst)

        val groupMenuSecond = MenuModel()
        groupMenuSecond.groupName = getString(R.string.nav_calendar)
        groupMenuSecond.groupIcon = R.drawable.ic_calendar_o
        groupMenuSecond.groupArrowStatus = MenuModel.GroupStatus.COLLAPSED
        listDataHeader.add(groupMenuSecond)

        val groupMenuThird = MenuModel()
        groupMenuThird.groupName = getString(R.string.nav_logout)
        groupMenuThird.groupIcon = R.drawable.ic_sign_out
        groupMenuThird.groupArrowStatus = MenuModel.GroupStatus.COLLAPSED
        listDataHeader.add(groupMenuThird)
    }

    /**
     * Populate list items in Navigation expandable list view
     */
    private fun populateExpandableList() {
        expandableListAdapter = ExpandableListAdapter(this, listDataHeader, listDataChild)
        expandableListView?.setAdapter(expandableListAdapter)
    }

    /**
     * Navigation item group click events
     */
    private fun initialiseNavigationItemItemListeners() {
        // List view Group click listener
        expandableListView.setOnGroupClickListener { parent, view, groupPosition, id ->
            false
        }

        // List view Group expanded listener
        expandableListView.setOnGroupExpandListener { groupPosition ->

            val menuModel = listDataHeader[groupPosition]
            menuModel.groupArrowStatus = MenuModel.GroupStatus.EXPANDED
            listDataHeader[groupPosition] = menuModel
            expandableListAdapter?.notifyDataSetChanged()
        }

        // List view Group collapsed listener
        expandableListView.setOnGroupCollapseListener { groupPosition ->

            val menuModel = listDataHeader[groupPosition]
            menuModel.groupArrowStatus = MenuModel.GroupStatus.COLLAPSED
            listDataHeader[groupPosition] = menuModel
            expandableListAdapter?.notifyDataSetChanged()
        }

        //ListView on group click listener
        expandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            when (listDataHeader[groupPosition].groupName!!) {
                /*resources.getString(R.string.nav_home) -> {
                    SharedPreferenceHelper.getInstance()
                            .getSharedPreference(MaropostApplication.getInstance(),
                                    "PREF")
                    replaceFragment(LoginFragment(), false)
                }
                resources.getString(R.string.nav_calendar) -> {
                    mDrawerLayout.closeDrawer(Gravity.START)
                    replaceFragment(HomeFragment(), true)
                }
                resources.getString(R.string.nav_logout) -> {
                }*/
            }
            false
        }
    }
}





