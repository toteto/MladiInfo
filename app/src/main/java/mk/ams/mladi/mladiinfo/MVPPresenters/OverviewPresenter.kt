package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.OverviewContract
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.notifications.LastArticleReadStore

class OverviewPresenter(val client: MladiInfoApiInterface, val lastArticleReadStore: LastArticleReadStore) : OverviewContract.Presenter() {
  override fun loadData(forceUpdate: Boolean) {
    val cacheControlHeader = if (forceUpdate) "no-cache" else null

    // Scholarships
    enqueueCall(client.getScholarships(cacheControlHeader), {
      lastArticleReadStore.storeLastArticleDate(R.id.scholarships, it.first().getParsedDate())
      getView()?.showScholarships(it)
    })

    // Work (Internship + employment)
    enqueueCall(client.getWorkPostings(cacheControlHeader), {
      val pair = it.partition { it.workType == "Internship" }
      lastArticleReadStore.storeLastArticleDate(R.id.internships, pair.first.first().getParsedDate())
      getView()?.showInternships(pair.first)
      lastArticleReadStore.storeLastArticleDate(R.id.employments, pair.second.first().getParsedDate())
      getView()?.showEmployments(pair.second)
    })

    // Training (Seminars + conferences)
    enqueueCall(client.getTraining(cacheControlHeader), {
      val pair = it.partition { it.type == Training.TYPE.CONFERENCE.value }
      lastArticleReadStore.storeLastArticleDate(R.id.conferences, pair.first.first().getParsedDate())
      getView()?.showConferences(pair.first)
      lastArticleReadStore.storeLastArticleDate(R.id.seminars, pair.second.first().getParsedDate())
      getView()?.showSeminars(pair.second)
    })

    // Trending
    enqueueCall(client.getArticles(), { getView()?.showTrendingArticles(it) })
  }
}