package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.overview_fragment_layout.*
import kotlinx.android.synthetic.main.overview_fragment_layout.view.*
import mk.ams.mladi.mladiinfo.DataModels.Scholarship
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.MVPContracts.MVPFragment
import mk.ams.mladi.mladiinfo.MVPContracts.OverviewContract
import mk.ams.mladi.mladiinfo.MVPPresenters.OverviewPresenter
import mk.ams.mladi.mladiinfo.OverviewAdapter
import mk.ams.mladi.mladiinfo.R

class OverviewFragment : MVPFragment<OverviewContract.View, OverviewContract.Presenter>(), OverviewContract.View {
  val itemsAdapter: OverviewAdapter by lazy { OverviewAdapter(activity) }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.overview_fragment_layout, container, false)

    view.srlRefresh.setOnRefreshListener { presenter.loadData(true) }
    view.rvItems.layoutManager = LinearLayoutManager(activity)
    view.rvItems.adapter = itemsAdapter

    return view
  }

  override fun showProgress(show: Boolean) {
    srlRefresh.isRefreshing = show
  }

  override fun showScholarships(list: List<Scholarship>) {
    itemsAdapter.bindScholarships(list)
  }

  override fun showInternships(list: List<Work>) {
    itemsAdapter.bindInternships(list)
  }

  override fun showEmployments(list: List<Work>) {
    itemsAdapter.bindEmployments(list)
  }

  override fun createPresenter(): OverviewContract.Presenter = OverviewPresenter(MladiInfoApiClient(activity).client)
}