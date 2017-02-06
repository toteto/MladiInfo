package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity_layout.*
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.MVPContracts.MVPActivity
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.MVPPresenters.MainPresenter
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import mk.ams.mladi.mladiinfo.R

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

    navigationView.setNavigationItemSelectedListener {
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
}



