package mk.ams.mladi.mladiinfo.MVPPresenters

import android.os.Bundle
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.NAV_ITEMS

/** This is where the magic happens. This presenter handles the main functionality of the app,
 * like what page should be displayed.*/
class MainPresenter : MainContract.Presenter() {
  var currentPage: NAV_ITEMS = NAV_ITEMS.STARTING_PAGE
  override fun onCategoryItemSelected(item: NAV_ITEMS) {
    when (item) {
      NAV_ITEMS.FACEBOOK -> getView()?.openMladiInfoFacebook()
      NAV_ITEMS.YOUTUBE -> getView()?.openMladiInfoYoutube()
      NAV_ITEMS.CALL_AMS -> getView()?.callAms()
      NAV_ITEMS.VISIT_AMS_WEBSITE -> getView()?.visitAmsWebsite()
      NAV_ITEMS.CONTACT_DEVELOPER -> getView()?.contactDeveloper()
      NAV_ITEMS.OPEN_GITHUB_PAGE -> getView()?.openGitHubPage()
      else ->
        if (item != currentPage) {
          currentPage = item
          updateViewCategory()
        }
    }
  }

  /** Will update the page on the view based on the [currentPage].*/
  private fun updateViewCategory() {
    val view = getView()
    if (view != null) {
      if (currentPage == NAV_ITEMS.STARTING_PAGE) {
        view.showOverview()
      } else {
        view.showCategory(currentPage)
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
    outState.putInt(CURRENT_SUBCATEGORY, currentPage.id)
    return outState
  }

  override fun loadState(state: Bundle) {
    val subcategory = NAV_ITEMS.getItemById(state.getInt(CURRENT_SUBCATEGORY))
    if (subcategory != null) {
      currentPage = subcategory
    }
  }

  override fun detachView() {
    super.detachView()
  }

  /** Handling of back navigation. Shouldn't be done like this, but for this simple app is fine. */
  override fun onBackPressed(): Boolean {
    if (currentPage == NAV_ITEMS.STARTING_PAGE) {
      return false
    } else {
      onCategoryItemSelected(NAV_ITEMS.STARTING_PAGE)
      return true
    }
  }
}