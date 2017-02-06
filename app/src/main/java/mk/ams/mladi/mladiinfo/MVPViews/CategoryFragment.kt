package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.category_fragment_layout.*
import mk.ams.mladi.mladiinfo.MVPContracts.CategoryContract
import mk.ams.mladi.mladiinfo.MVPContracts.MVPFragment
import mk.ams.mladi.mladiinfo.MVPPresenters.CategoryPresenter
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.SubcategoriesPagerAdapter
import mk.ams.mladi.mladiinfo.ViewModels.Category
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory
import mk.ams.mladi.mladiinfo.onPageChangeListener

/** View that is used for displaying Category.*/
class CategoryFragment : MVPFragment<CategoryFragment, CategoryPresenter>(), CategoryContract.View {
  companion object {
    val CATEGORY_KEY = "category"

    fun newInstance(category: NAV_ITEMS): CategoryFragment {
      val fragment = CategoryFragment()
      val bundle = Bundle()
      bundle.putSerializable(CATEGORY_KEY, category)
      fragment.arguments = bundle

      return fragment
    }
  }

  val category: Category by lazy { (arguments.get(CATEGORY_KEY) as NAV_ITEMS).getCategoryObject(activity) }

  val pagerAdapter: SubcategoriesPagerAdapter by lazy { SubcategoriesPagerAdapter(activity, childFragmentManager) }

  override fun getLayoutId(): Int = R.layout.category_fragment_layout

  override fun createPresenter(): CategoryPresenter = CategoryPresenter(category)

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewPager.adapter = pagerAdapter
    tabLayout.setupWithViewPager(viewPager)
    viewPager.onPageChangeListener { updateTabIndicatorColor(pagerAdapter.getSubcategory(it)) }
  }

  fun updateTabIndicatorColor(subcategory: Subcategory<*>) {
    tabLayout.setSelectedTabIndicatorColor(
        ContextCompat.getColor(activity, subcategory.color))
  }

  override fun setSubCategories(subcategories: List<Subcategory<Any>>) {
    pagerAdapter.subcategories = subcategories
    updateTabIndicatorColor(subcategories.first())
    tabLayout.visibility = if (subcategories.size > 1) View.VISIBLE else View.GONE
  }

  override fun showSubCategory(subcategory: Subcategory<out Any>) {
    viewPager.post { viewPager.setCurrentItem(pagerAdapter.subcategories.indexOf(subcategory), false) }
  }

  override fun setTitle(title: String) {
    activity?.title = title
  }
}