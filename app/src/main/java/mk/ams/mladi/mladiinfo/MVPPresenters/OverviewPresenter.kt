package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.OverviewContract

class OverviewPresenter(val client: MladiInfoApiInterface) : OverviewContract.Presenter() {
  override fun loadData(forceUpdate: Boolean) {
    val cacheControlHeader = if (forceUpdate) "no-cache" else null

    // Scholarships
    enqueueCall(client.getScholarships(cacheControlHeader), { getView()?.showScholarships(it) })

    // Work (Internship + employment)
    enqueueCall(client.getWorkPostings(cacheControlHeader), {
      val workPostings = it.partition { it.workType == "Internship" }
      getView()?.showInternships(workPostings.first)
      getView()?.showEmployments(workPostings.second)
    })

    // Training (Seminars + conferences)
    enqueueCall(client.getTraining(cacheControlHeader), {
      val pair = it.partition { it.type == Training.TYPE.CONFERENCE.value }
      getView()?.showConferences(pair.first)
      getView()?.showSeminars(pair.second)
    })

    // Trending
    enqueueCall(client.getArticles(), {getView()?.showTrendingArticles(it)})
  }
}