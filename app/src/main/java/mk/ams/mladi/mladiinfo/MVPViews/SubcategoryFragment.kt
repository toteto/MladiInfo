package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.overview_fragment_layout.*
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.MVPContracts.MVPFragment
import mk.ams.mladi.mladiinfo.MVPContracts.SubcategoryContract
import mk.ams.mladi.mladiinfo.MVPPresenters.SubcategoryPresenter
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.SubcategoryAdapter
import mk.ams.mladi.mladiinfo.SubcategoryAdapterInterface
import mk.ams.mladi.mladiinfo.ViewModels.SubCategory

class SubcategoryFragment : MVPFragment<SubcategoryFragment, SubcategoryPresenter>(), SubcategoryContract.View {
  lateinit var subcategory: SubCategory<Any>
  private val subcategoryAdapter = SubcategoryAdapter()

  companion object {
    fun getInstance(subCategory: SubCategory<Any>): SubcategoryFragment {
      val fragment = SubcategoryFragment()
      fragment.subcategory = subCategory
      return fragment
    }
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    rvItems.adapter = subcategoryAdapter
    rvItems.layoutManager = LinearLayoutManager(activity)
    srlRefresh.setOnRefreshListener { presenter.loadData(true) }
  }

  override fun createPresenter() = SubcategoryPresenter(MladiInfoApiClient(activity).client, subcategory)

  override fun getLayoutId(): Int = R.layout.overview_fragment_layout

  override fun getSubcategoryItemAdapter(): SubcategoryAdapterInterface = subcategoryAdapter

  override fun showLoading(show: Boolean) {
    srlRefresh.isRefreshing = show
  }

  override fun showError(message: String) {
    throw UnsupportedOperationException("not implemented")
  }

}