package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.DataModels.Scholarship
import mk.ams.mladi.mladiinfo.DataModels.Work

interface OverviewContract {
  interface View : MvpLceContract.LCEView {
    fun showScholarships(list: List<Scholarship>)
    fun showInternships(list: List<Work>)
    fun showEmployments(list: List<Work>)
  }

  abstract class Presenter : MvpLceContract.LCEPresenter<View>() {
  }
}