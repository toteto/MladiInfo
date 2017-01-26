package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.OverviewContract

class OverviewPresenter(val client: MladiInfoApiInterface) : OverviewContract.Presenter() {
  override fun loadData(forceUpdate: Boolean) {
    val cacheControlHeader = if (forceUpdate) "no-cache" else null

    enqueueCall(client.getScholarships(cacheControlHeader), { getView()?.showScholarships(it.slice(0 until 4)) })
    enqueueCall(client.getWorkPostings(cacheControlHeader), {
      val workPostings = it.partition { it.workType == "Internship" }
      getView()?.showInternships(workPostings.first.slice(0 until 4))
      getView()?.showEmployments(workPostings.second.slice(0 until 4))
    })
  }
}