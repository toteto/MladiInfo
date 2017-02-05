package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.NAV_ITEMS

class MainPresenter(val client: MladiInfoApiInterface) : MainContract.Presenter() {
  var currentCategory: NAV_ITEMS = NAV_ITEMS.STARTING_PAGE
  override fun onCategoryItemSelected(item: NAV_ITEMS) {
    if (item != currentCategory) {
      currentCategory = item
      updateViewCategory()
    }
  }

  private fun updateViewCategory() {
    val view = getView()
    if (view != null) {
      if (currentCategory == NAV_ITEMS.STARTING_PAGE) {
        view.showOverview()
      } else {
        view.showCategory(currentCategory.parentCategory ?: currentCategory)
      }
    }
  }

  override fun attachView(view: MainContract.View) {
    super.attachView(view)
    updateViewCategory()
  }

}