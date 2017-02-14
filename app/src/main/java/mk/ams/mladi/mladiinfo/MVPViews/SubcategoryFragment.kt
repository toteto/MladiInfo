package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.overview_fragment_layout.*
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.SubcategoryAdapter
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory
import mk.ams.mladi.mladiinfo.notifications.getNotificationPreferences

class SubcategoryFragment : Fragment() {
  lateinit var subcategory: Subcategory<Any>
  private val subcategoryAdapter = SubcategoryAdapter()

  companion object {
    fun getInstance(subcategory: Subcategory<Any>): SubcategoryFragment {
      val fragment = SubcategoryFragment()
      fragment.subcategory = subcategory
      return fragment
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Bind the existing data from the subcategory
    subcategory.bindDataTo(subcategory.data, subcategoryAdapter)
    // Register observer for further updates of the data
    subcategory.addDataObserver {
      subcategory.bindDataTo(it, subcategoryAdapter)
      showLoading(false)
    }
    val shouldShowNotificationSwitch =
        activity.getNotificationPreferences().areNotificationsEnabled() &&
        subcategory.supportsNotifications()
    if (shouldShowNotificationSwitch) {
      subcategoryAdapter.withNotificationSwitch(subcategory.navItem.id)
    }

    return inflater?.inflate(R.layout.overview_fragment_layout, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    rvItems.adapter = subcategoryAdapter
    rvItems.layoutManager = LinearLayoutManager(activity)
    srlRefresh.setOnRefreshListener {
      subcategory.requestUpdateDataHandler?.invoke() ?: showLoading(false)
    }
  }

  fun showLoading(show: Boolean) {
    srlRefresh.isRefreshing = show
  }

  fun showError(message: String) {
    throw UnsupportedOperationException("not implemented")
  }

}