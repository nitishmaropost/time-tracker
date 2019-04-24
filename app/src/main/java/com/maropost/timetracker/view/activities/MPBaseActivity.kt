package com.maropost.timetracker.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.maropost.timetracker.R
import com.maropost.timetracker.view.adapters.MenuAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import java.util.*


open class MPBaseActivity : AppCompatActivity(),DuoMenuView.OnMenuClickListener {

    private var mMenuAdapter: MenuAdapter? = null
    private var mTitles = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTitles = ArrayList(Arrays.asList(*resources.getStringArray(R.array.menuOptions)))
        setSupportActionBar(toolbar)
        setSearchListener()
        // Handle menu actions
        handleMenu()
        // Handle drawer actions
        handleDrawer()
    }

    /**
     * Navigation menu items
     */
    private fun handleMenu() {
        mMenuAdapter = MenuAdapter(mTitles)
        mDuoMenuView.setOnMenuClickListener(this)
        mDuoMenuView.adapter = mMenuAdapter
    }

    /**
     * Navigation settings and data placement
     */
    private fun handleDrawer() {
        val duoDrawerToggle = DuoDrawerToggle(
            this,
            mDrawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mDrawerLayout.setDrawerListener(duoDrawerToggle)
        duoDrawerToggle.syncState()
    }

    /**
     * Listen to search events
     */
    private fun setSearchListener() {
        search_view.setVoiceSearch(false)

        /**
         * Detect on text changed for search view
         */
        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        /**
         * Detect search view opened or closed
         */
        search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
            }

            override fun onSearchViewClosed() {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.action_search)
        search_view.setMenuItem(item)
        return true
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
     * Intercept on back click
     */
    override fun onBackPressed() {
        if (getFragmentCount() == 1 && !search_view.isSearchOpen)
            finish()
        else if (search_view.isSearchOpen)
            search_view.closeSearch()
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
     /*   val snackBar = Snackbar.make(mainContainer, "Access denied!!", Snackbar.LENGTH_SHORT)
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
        action.text = ""*/
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
     * Set Toolbar right icon
     */
    fun setToolbarRightIcon(resId: Int) {
        imgToolbarRightIcon.setImageResource(resId)
    }



    /**
     * Control navigation drawer visibility
     */
    fun showNavigationDrawer(allow: Boolean) {
        if (allow)
            mDrawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_UNLOCKED)
        else
            mDrawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * Set progress bar display
     */
    fun showProgressBar(isAllow: Boolean) {
        if (isAllow)
            progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

  override fun onOptionClicked(position: Int, objectClicked: Any?) {
      mMenuAdapter?.setViewSelected(position, true)
      mDrawerLayout.closeDrawer()
  }

    override fun onHeaderClicked() {
    }

    override fun onFooterClicked() {
    }
}





