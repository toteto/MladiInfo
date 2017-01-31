package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity_layout.*
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.MVPContracts.MVPActivity
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract.CATEGORY_ITEM
import mk.ams.mladi.mladiinfo.MVPPresenters.MainPresenter
import mk.ams.mladi.mladiinfo.R

class MainActivity : MVPActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {
  val overviewFragment: OverviewFragment by lazy {
    supportFragmentManager.findFragmentByTag(OVERVIEW_FRAGMENT_TAG) as OverviewFragment? ?: OverviewFragment()
  }

  companion object {
    private val OVERVIEW_FRAGMENT_TAG = "overview"
    val CATEGORY_MAPPING = mapOf(
        R.id.trainings to CATEGORY_ITEM.TRAININGS,
        R.id.educational_institutions to CATEGORY_ITEM.EDUCATIONAL_INSTITUTIONS,
        R.id.work to CATEGORY_ITEM.WORKS,
        R.id.student_discounts to CATEGORY_ITEM.STUDENT_DISCOUNTS,
        R.id.organizations to CATEGORY_ITEM.ORGANIZATIONS)
  }

  override fun createPresenter(): MainContract.Presenter = MainPresenter(MladiInfoApiClient(this).client)

  override fun showOverview() {
    if (overviewFragment.isAdded.not()) {
      supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragmentContainer, overviewFragment,
          OVERVIEW_FRAGMENT_TAG).commit()
    }
  }

  override fun showCategory(category: CATEGORY_ITEM) {
    supportFragmentManager.beginTransaction().replace(R.id.mainActivity_fragmentContainer,
        CategoryFragment.newInstance(category), category.name).commit()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity_layout)
    setSupportActionBar(toolbar)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    navigationView.setNavigationItemSelectedListener {
      presenter.onCategoryItemSelected(CATEGORY_MAPPING[it.itemId] ?: CATEGORY_ITEM.STARTING_PAGE)
      true
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      drawerLayout.openDrawer(Gravity.START)
      return true
    }
    return super.onOptionsItemSelected(item)
  }
}



