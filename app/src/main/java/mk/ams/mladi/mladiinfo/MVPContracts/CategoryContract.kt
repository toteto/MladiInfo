package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.ViewModels.Category
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory

interface CategoryContract {
  interface View : MVPContract.View {
    /** Set the subcategories that will be available to be displayed.*/
    fun setSubCategories(subcategories: List<Subcategory<Any>>)

    /** Make the provided subcategory as active. */
    fun showSubCategory(subcategory: Subcategory<Any>)

    /** Set the title of the view. */
    fun setTitle(title: String)
  }

  abstract class Presenter<V : View>(val category: Category) : MVPPresenter<V>() {
  }
}