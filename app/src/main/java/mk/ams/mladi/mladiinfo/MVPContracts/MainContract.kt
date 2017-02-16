package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle
import mk.ams.mladi.mladiinfo.NAV_ITEMS

interface MainContract {
  interface View : MVPContract.View {
    fun showOverview()
    fun showCategory(category: NAV_ITEMS)
    fun onCategorySelected(category: NAV_ITEMS)
    fun openMladiInfoFacebook()
    fun openMladiInfoYoutube()
    fun callAms()
    fun visitAmsWebsite()
    fun contactDeveloper()
    fun openGitHubPage()
  }

  abstract class Presenter : MVPPresenter<View>() {
    companion object {
      val CURRENT_SUBCATEGORY = "subcategory"
    }

    abstract fun onCategoryItemSelected(item: NAV_ITEMS)
    /** Activated when the user click the back button.
     * @return true if the action was handled by this presenter. False otherwise.*/
    abstract fun onBackPressed(): Boolean

    /** Save the state of this presenter in the provided bundle, and return it back. */
    abstract fun saveState(outState: Bundle): Bundle
    /** Load the state for this presenter from the provided bundle. */
    abstract fun loadState(state: Bundle)
  }
}