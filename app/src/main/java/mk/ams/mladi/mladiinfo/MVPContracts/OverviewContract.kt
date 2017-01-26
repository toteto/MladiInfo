package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.DataModels.Scholarship
import mk.ams.mladi.mladiinfo.DataModels.Work

interface OverviewContract {
  interface View : MVPContract.View {
    fun showProgress(show: Boolean)
    fun showScholarships(list: List<Scholarship>)
    fun showInternships(list: List<Work>)
    fun showEmployments(list: List<Work>)
  }

  abstract class Presenter : MVPPresenter<View>() {
    abstract fun loadData(forceUpdate: Boolean)
  }
}