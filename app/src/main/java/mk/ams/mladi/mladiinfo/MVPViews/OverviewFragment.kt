package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.overview_fragment_layout.*
import kotlinx.android.synthetic.main.overview_fragment_layout.view.*
import mk.ams.mladi.mladiinfo.DataModels.Article
import mk.ams.mladi.mladiinfo.DataModels.Scholarship
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.MVPContracts.MVPFragment
import mk.ams.mladi.mladiinfo.MVPContracts.OverviewContract
import mk.ams.mladi.mladiinfo.MVPPresenters.OverviewPresenter
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import mk.ams.mladi.mladiinfo.OverviewAdapter
import mk.ams.mladi.mladiinfo.R

class OverviewFragment : MVPFragment<OverviewContract.View, OverviewContract.Presenter>(), OverviewContract.View {
  private var numOfItems = 3
  override fun getLayoutId(): Int = R.layout.overview_fragment_layout

  val itemsAdapter: OverviewAdapter by lazy { OverviewAdapter(activity) }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (view != null) {
      itemsAdapter.onCategoryHeaderClickListener = object : OverviewAdapter.OnCategoryHeaderClickListener {
        override fun onCategoryClicked(navItem: NAV_ITEMS) {
          (activity as MainActivity).onCategorySelected(navItem)
        }
      }
      view.srlRefresh.setOnRefreshListener { presenter.loadData(true) }
      view.rvItems.layoutManager = LinearLayoutManager(activity)
      view.rvItems.adapter = itemsAdapter

      activity.title = getString(R.string.app_name)
    }
  }

  override fun showLoading(show: Boolean) {
    srlRefresh.isRefreshing = show
  }

  override fun showError(message: String) {
    throw UnsupportedOperationException("not implemented")
  }

  override fun showScholarships(list: List<Scholarship>) {
    itemsAdapter.bindScholarships(list.getFirstN(numOfItems))
  }

  override fun showInternships(list: List<Work>) {
    itemsAdapter.bindInternships(list.getFirstN(numOfItems))
  }

  override fun showEmployments(list: List<Work>) {
    itemsAdapter.bindEmployments(list.getFirstN(numOfItems))
  }

  override fun showSeminars(list: List<Training>) {
    itemsAdapter.bindSeminars(list.getFirstN(numOfItems))
  }

  override fun showConferences(list: List<Training>) {
    itemsAdapter.bindConferences(list.getFirstN(numOfItems))
  }

  override fun showTrendingArticles(it: List<Article>) {
    itemsAdapter.bindTrendingArticles(it.getFirstN(10))
  }

  override fun createPresenter(): OverviewContract.Presenter = OverviewPresenter(MladiInfoApiClient(activity).client)

  override fun setNumberOfItemsToShow(n: Int) {
    numOfItems = n
  }
}