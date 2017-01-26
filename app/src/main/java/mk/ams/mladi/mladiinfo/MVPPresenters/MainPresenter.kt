package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract.CATEGORY_MENU_ITEM

class MainPresenter(val client: MladiInfoApiInterface) : MainContract.Presenter() {
  var currentCategory: CATEGORY_MENU_ITEM = CATEGORY_MENU_ITEM.STARTING_PAGE
  override fun onCategoryItemSelected(item: CATEGORY_MENU_ITEM) {
    if (item != currentCategory) {
      currentCategory = item
    }
  }

  private fun updateViewcategory() {
    val view = getView()
    if (view != null) {
      if (currentCategory == CATEGORY_MENU_ITEM.STARTING_PAGE) {
        view.showOverview()
      } else {
        throw NotImplementedError("Not implemented")
      }
    }
  }

  override fun attachView(view: MainContract.View) {
    super.attachView(view)
    updateViewcategory()
  }

}