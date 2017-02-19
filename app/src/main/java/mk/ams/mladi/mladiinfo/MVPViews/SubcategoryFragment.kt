package mk.ams.mladi.mladiinfo.MVPViews

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.overview_fragment_layout.*
import mk.ams.mladi.mladiinfo.DataModels.DateInterface
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.SubcategoryAdapter
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory
import mk.ams.mladi.mladiinfo.notifications.LastArticleReadStore
import mk.ams.mladi.mladiinfo.notifications.getNotificationPreferences

/** Not really a MVP View, but it is a View at least :)
 * This ia more of a page that knows how to display an [Subcategory]. */
class SubcategoryFragment : Fragment() {
  lateinit var subcategory: Subcategory<Any>
  val articlesStore by lazy { LastArticleReadStore(activity) }
  private val subcategoryAdapter = SubcategoryAdapter()

  companion object {
    fun getInstance(subcategory: Subcategory<Any>): SubcategoryFragment {
      val fragment = SubcategoryFragment()
      fragment.subcategory = subcategory
      return fragment
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    setHasOptionsMenu(subcategory.queryable)
    showLoading(true)
    // Bind the existing data from the subcategory
    bindData()
    // Register observer for further updates of the data
    subcategory.addDataObserver {
      bindData()
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

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater.inflate(R.menu.subcategory_fragment_menu, menu)
    val searchView = MenuItemCompat.getActionView(
        menu?.findItem(R.id.subcategory_search)) as SearchView?
    searchView?.setOnQueryTextListener(searchQueryTextListener)
    searchView?.setOnFocusChangeListener { view, b -> searchView.isIconified = true }
  }

  private fun bindData() {
    subcategory.bindDataTo(subcategory.data, subcategoryAdapter)
    if (subcategory.data.isNotEmpty()) {
      showLoading(false)
    }
    val lastArticle = subcategory.data.firstOrNull()
    if (lastArticle is DateInterface) {
      articlesStore.storeLastArticleDate(subcategory.navItem.id, lastArticle.getParsedDate())
    }
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
    srlRefresh?.isRefreshing = show
  }

  private val searchQueryTextListener = object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
      val focusedView = activity?.currentFocus
      if (focusedView != null) {
        val ims = (activity.getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager
        ims.hideSoftInputFromWindow(focusedView.windowToken, 0)
      }
      return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
      srlRefresh?.isEnabled = newText.isNullOrEmpty()
      if (newText != null) {
        subcategoryAdapter.filterItems(newText)
        return true
      }
      return false
    }
  }
}