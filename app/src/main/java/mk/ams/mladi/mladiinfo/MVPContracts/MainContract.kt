package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.NAV_ITEMS

interface MainContract {
  interface View : MVPContract.View {
    fun showOverview()
    fun showCategory(category: NAV_ITEMS)
  }

  abstract class Presenter : MVPPresenter<View>() {
    abstract fun onCategoryItemSelected(item: NAV_ITEMS)
  }
}