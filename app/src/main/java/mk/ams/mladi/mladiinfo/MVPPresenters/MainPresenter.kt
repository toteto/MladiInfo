package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract.CATEGORY_ITEM

class MainPresenter(val client: MladiInfoApiInterface) : MainContract.Presenter() {
  var currentCategory: CATEGORY_ITEM = CATEGORY_ITEM.STARTING_PAGE
  override fun onCategoryItemSelected(item: CATEGORY_ITEM) {
    if (item != currentCategory) {
      currentCategory = item
      updateViewCategory()
    }

  }

  private fun updateViewCategory() {
    val view = getView()
    if (view != null) {
      if (currentCategory == CATEGORY_ITEM.STARTING_PAGE) {
        view.showOverview()
      } else {
        view.showCategory(currentCategory)
      }
    }
  }

  override fun attachView(view: MainContract.View) {
    super.attachView(view)
    updateViewCategory()
  }

}