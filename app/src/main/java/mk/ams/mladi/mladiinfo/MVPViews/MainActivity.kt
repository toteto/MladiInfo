package mk.ams.mladi.mladiinfo.MVPViews

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SwitchCompat
import android.view.Gravity
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity_layout.*
import mk.ams.mladi.mladiinfo.*
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.MVPContracts.MVPActivity
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.MVPPresenters.InternetConnectionViewHandler
import mk.ams.mladi.mladiinfo.MVPPresenters.MainPresenter
import mk.ams.mladi.mladiinfo.notifications.NotificationJobService
import mk.ams.mladi.mladiinfo.notifications.getNotificationPreferences


class MainActivity : MVPActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {
  private val overviewFragment: Fragment?
    get() = supportFragmentManager.findFragmentById(R.id.overviewFragment)
  private var categoryFragment: Fragment? = null
    get() = supportFragmentManager.findFragmentById(R.id.mainActivity_fragmentContainer)
  private var internetConnectionViewHandler: InternetConnectionViewHandler? = null

  override fun createPresenter(): MainContract.Presenter = MainPresenter(MladiInfoApiClient(this).client)

  override fun showOverview() {
    if (overviewFragment != null) {
      val transaction = supportFragmentManager.beginTransaction()
      transaction.show(overviewFragment)
      if (categoryFragment != null && categoryFragment!!.isAdded) {
        transaction.remove(categoryFragment)
      }
      categoryFragment = null
      transaction.commit()
      navigationView?.setCheckedItem(R.id.starting_page)
      setTitle(R.string.app_name)
    }
  }

  override fun showCategory(category: NAV_ITEMS) {
    // Elvis (?:) operator is used to ge the parent category if the selected "category" is in fact a subcategory.
    val tag = (category.parentCategory?.id ?: category.id).toString()
    val transaction = supportFragmentManager.beginTransaction()
        .hide(overviewFragment)
    if (categoryFragment?.tag != tag) {
      // if the request fragment is different from the one on screen
      val newCategoryFragment = CategoryFragment.newInstance(category)
      transaction.replace(R.id.mainActivity_fragmentContainer, newCategoryFragment, tag)
    }
    transaction.commit()
    // Check if there is already fragment in place for this category
    navigationView?.setCheckedItem(category.parentCategory?.id ?: category.id)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity_layout)
    setSupportActionBar(toolbar)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val notificationSwitch = navigationView.menu.findItem(R.id.switch_enable_navigation)?.actionView as SwitchCompat
    notificationSwitch.isChecked = getNotificationPreferences().areNotificationsEnabled()
    notificationSwitch.setOnCheckedChangeListener { btn, b -> NotificationJobService.enableNotifications(this, b) }
    navigationView.setNavigationItemSelectedListener {
      onCategorySelected(NAV_ITEMS.getItemById(it.itemId) ?: NAV_ITEMS.STARTING_PAGE)
      drawerLayout.closeDrawer(navigationView)
      true
    }
  }

  override fun attachBaseContext(newBase: Context?) {
    super.attachBaseContext(LocaleHelper.onAttach(newBase))
  }

  override fun onCategorySelected(category: NAV_ITEMS) {
    presenter.onCategoryItemSelected(category)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      drawerLayout.openDrawer(Gravity.START)
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    val handledByPresenter = presenter.onBackPressed()
    if (!handledByPresenter) {
      super.onBackPressed()
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(presenter.saveState(outState))
  }

  override fun onResume() {
    super.onResume()
    internetConnectionViewHandler = InternetConnectionViewHandler(noInternetConnectionView)
    registerReceiver(internetConnectionViewHandler, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
  }

  override fun onDestroy() {
    super.onDestroy()
    if (internetConnectionViewHandler != null) {
      unregisterReceiver(internetConnectionViewHandler)
    }
  }

  override fun openMladiInfoFacebook() = tryOpenFacebook(getString(R.string.facebook_link))

  override fun openMladiInfoYoutube() = openWebsite(getString(R.string.youtube_link))

  override fun callAms() = dialPhone(getString(R.string.ams_phone_number))

  override fun visitAmsWebsite() = openWebsite(getString(R.string.abs_website_url))

  override fun contactDeveloper() = openMailClient(getString(R.string.developer_mail))

  override fun openGitHubPage() = openWebsite(getString(R.string.github_link))
}



