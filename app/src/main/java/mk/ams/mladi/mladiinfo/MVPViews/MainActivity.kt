package mk.ams.mladi.mladiinfo.MVPViews

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SwitchCompat
import android.view.Gravity
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity_layout.*
import mk.ams.mladi.mladiinfo.*
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.MVPContracts.MVPActivity
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.MVPPresenters.MainPresenter
import mk.ams.mladi.mladiinfo.notifications.NotificationJobService
import mk.ams.mladi.mladiinfo.notifications.getNotificationPreferences


class MainActivity : MVPActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

  val overviewFragment: OverviewFragment by lazy {
    supportFragmentManager.findFragmentByTag(OVERVIEW_FRAGMENT_TAG) as OverviewFragment? ?: OverviewFragment()
  }

  companion object {
    private val OVERVIEW_FRAGMENT_TAG = "overview"
  }

  override fun createPresenter(): MainContract.Presenter = MainPresenter(MladiInfoApiClient(this).client)

  override fun showOverview() {
    if (overviewFragment.isAdded.not()) {
      supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragmentContainer, overviewFragment,
          OVERVIEW_FRAGMENT_TAG).commit()
      navigationView?.setCheckedItem(R.id.starting_page)
    }
  }

  override fun showCategory(category: NAV_ITEMS) {
    // Elvis (?:) operator is used to ge the parent category if the selected "category" is in fact a subcategory.
    supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragmentContainer,
        CategoryFragment.newInstance(category), category.parentCategory?.name ?: category.name).commit()
    navigationView.setCheckedItem(category.parentCategory?.id ?: category.id)
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
      if (it.itemId == R.id.switch_enable_navigation) {
        /* fixme */
        val n = NotificationJobService.buildNotification(this, listOf(Pair(R.id.scholarships, 1), Pair(R.id.trainings, 2)))
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(123, n)
      }
      onCategorySelected(NAV_ITEMS.getItemById(it.itemId) ?: NAV_ITEMS.STARTING_PAGE)
      drawerLayout.closeDrawer(navigationView)
      true
    }
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

  override fun openMladiInfoFacebook() = tryOpenFacebook(getString(R.string.facebook_link))

  override fun openMladiInfoYoutube() = openWebsite(getString(R.string.youtube_link))

  override fun callAms() = dialPhone(getString(R.string.ams_phone_number))

  override fun visitAmsWebsite() = openWebsite(getString(R.string.abs_website_url))
}



