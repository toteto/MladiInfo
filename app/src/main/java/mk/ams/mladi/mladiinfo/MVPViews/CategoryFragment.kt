package mk.ams.mladi.mladiinfo.MVPViews

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.category_fragment_layout.*
import mk.ams.mladi.mladiinfo.MVPContracts.CategoryContract
import mk.ams.mladi.mladiinfo.MVPContracts.MVPFragment
import mk.ams.mladi.mladiinfo.MVPContracts.MainContract
import mk.ams.mladi.mladiinfo.MVPPresenters.CategoryPresenter
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.SubcategoriesPagerAdapter
import mk.ams.mladi.mladiinfo.ViewModels.Category
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory

/** View that is used for displaying Category.*/
class CategoryFragment : MVPFragment<CategoryFragment, CategoryPresenter>(), CategoryContract.View {
  companion object {
    val CATEGORY_KEY = "category"

    fun newInstance(category: MainContract.CATEGORY_ITEM): CategoryFragment {
      val fragment = CategoryFragment()
      val bundle = Bundle()
      bundle.putSerializable(CATEGORY_KEY, category)
      fragment.arguments = bundle

      return fragment
    }
  }

  val category: Category by lazy {
    when (arguments.get(CATEGORY_KEY) as MainContract.CATEGORY_ITEM) {
      MainContract.CATEGORY_ITEM.TRAININGS -> Category.Factory.getTrainingCategory(activity)
      MainContract.CATEGORY_ITEM.WORKS -> Category.Factory.getWorkCategory(activity)
      MainContract.CATEGORY_ITEM.ORGANIZATIONS -> Category.Factory.getOrganizations(activity)
      else -> throw NotImplementedError("only ${MainContract.CATEGORY_ITEM.TRAININGS} is supported.")
    }
  }
  val pagerAdapter: SubcategoriesPagerAdapter by lazy { SubcategoriesPagerAdapter(childFragmentManager) }

  override fun getLayoutId(): Int = R.layout.category_fragment_layout

  override fun createPresenter(): CategoryPresenter = CategoryPresenter(category)

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewPager.adapter = pagerAdapter
    tabLayout.setupWithViewPager(viewPager)
  }

  override fun setSubCategories(subcategories: List<Subcategory<Any>>) {
    pagerAdapter.subcategories = subcategories
  }

  override fun showSubCategory(subcategory: Subcategory<out Any>) {
    throw UnsupportedOperationException("not implemented")
  }

  override fun setTitle(title: String) {
    activity?.title = title
  }
}