package mk.ams.mladi.mladiinfo.MVPPresenters

import android.os.Bundle
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
      NAV_ITEMS.CONTACT_DEVELOPER -> getView()?.contactDeveloper()
      NAV_ITEMS.OPEN_GITHUB_PAGE -> getView()?.openGitHubPage()
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

  override fun attachView(view: MainContract.View, savedInstanceState: Bundle?) {
    super.attachView(view, savedInstanceState)
    if (savedInstanceState != null) {
      loadState(savedInstanceState)
    }
    updateViewCategory()
  }

  override fun saveState(outState: Bundle): Bundle {
    outState.putInt(CURRENT_SUBCATEGORY, currentCategory.id)
    return outState
  }

  override fun loadState(state: Bundle) {
    val subcategory = NAV_ITEMS.getItemById(state.getInt(CURRENT_SUBCATEGORY))
    if (subcategory != null) {
      currentCategory = subcategory
    }
  }

  override fun detachView() {
    super.detachView()
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