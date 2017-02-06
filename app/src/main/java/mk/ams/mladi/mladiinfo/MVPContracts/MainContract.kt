package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.NAV_ITEMS

interface MainContract {
  interface View : MVPContract.View {
    fun showOverview()
    fun showCategory(category: NAV_ITEMS)
    fun onCategorySelected(category: NAV_ITEMS)
  }

  abstract class Presenter : MVPPresenter<View>() {
    abstract fun onCategoryItemSelected(item: NAV_ITEMS)
    /** Activated when the user click the back button.
     * @return true if the action was handled by this presenter. False otherwise.*/
    abstract fun onBackPressed(): Boolean
  }
}