package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.DataProviders.methodNoName
import mk.ams.mladi.mladiinfo.MVPContracts.OverviewContract

class OverviewPresenter(val client: MladiInfoApiInterface) : OverviewContract.Presenter() {
  override fun loadData(forceUpdate: Boolean) {
    getView()?.showProgress(true)
    val cacheControlHeader = if (forceUpdate) "no-cache" else null
    client.getScholarships(cacheControlHeader).methodNoName({ getView()?.showScholarships(it.slice(0 until 4)) }, {})
    client.getScholarships(cacheControlHeader).methodNoName({ getView()?.showScholarships(it.slice(0 until 4)) }, {})
    client.getWorkPostings().methodNoName({
      val workPostings = it.partition { it.workType == "Internship" }
      getView()?.showInternships(workPostings.first.slice(0 until 4))
      getView()?.showEmployments(workPostings.second.slice(0 until 4))
    }, {})
  }

  override fun attachView(view: OverviewContract.View) {
    super.attachView(view)
    loadData(false)
  }
}