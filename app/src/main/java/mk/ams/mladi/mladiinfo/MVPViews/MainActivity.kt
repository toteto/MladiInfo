package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity_layout.*
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.MVPContracts.MVPActivity
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract.CATEGORY_MENU_ITEM
import mk.ams.mladi.mladiinfo.MVPPresenters.MainPresenter
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.ViewModels.Category

class MainActivity : MVPActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {
  val overviewFragment: OverviewFragment? by lazy { OverviewFragment() }

  companion object {
    val CATEGORY_MAPPING = mapOf(
        R.id.trainings to CATEGORY_MENU_ITEM.TRAININGS,
        R.id.educational_institutions to CATEGORY_MENU_ITEM.EDUCATIONAL_INSTITUTIONS,
        R.id.work to CATEGORY_MENU_ITEM.WORKS,
        R.id.student_discounts to CATEGORY_MENU_ITEM.STUDENT_DISCOUNTS,
        R.id.organizations to CATEGORY_MENU_ITEM.ORGANIZATIONS)
  }

  override fun createPresenter(): MainContract.Presenter = MainPresenter(MladiInfoApiClient(this).client)

  override fun showOverview() {
    supportFragmentManager.beginTransaction().add(R.id.mainActivity_fragmentContainer, overviewFragment).commit()
  }

  override fun showCategory(addSubCategory: Category) {
    throw UnsupportedOperationException("not implemented")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity_layout)
    setSupportActionBar(toolbar)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    navigationView.setNavigationItemSelectedListener {
      presenter.onCategoryItemSelected(CATEGORY_MAPPING[it.itemId] ?: CATEGORY_MENU_ITEM.STARTING_PAGE)
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



