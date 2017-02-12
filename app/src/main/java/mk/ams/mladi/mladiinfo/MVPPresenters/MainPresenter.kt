package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.NAV_ITEMS

class MainPresenter(val client: MladiInfoApiInterface) : MainContract.Presenter() {
  var currentCategory: NAV_ITEMS = NAV_ITEMS.STARTING_PAGE
  override fun onCategoryItemSelected(item: NAV_ITEMS) {
    when (item) {
      NAV_ITEMS.FACEBOOK -> getView()?.openMladiInfoFacebook()
      NAV_ITEMS.YOUTUBE -> getView()?.openMladiInfoYoutube()
      NAV_ITEMS.CALL_AMS -> getView()?.callAms()
      NAV_ITEMS.VISIT_AMS_WEBSITE -> getView()?.visitAmsWebsite()
      else ->
        if (item != currentCategory) {
          currentCategory = item
          updateViewCategory()
        }
    }
  }

  private fun updateViewCategory() {
    val view = getView()
    if (view != null) {
      if (currentCategory == NAV_ITEMS.STARTING_PAGE) {
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

  override fun onBackPressed(): Boolean {
    if (currentCategory == NAV_ITEMS.STARTING_PAGE) {
      return false
    } else {
      onCategoryItemSelected(NAV_ITEMS.STARTING_PAGE)
      return true
    }
  }
}