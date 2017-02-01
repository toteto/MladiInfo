package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.ViewModels.Category
import mk.ams.mladi.mladiinfo.ViewModels.SubCategory

interface CategoryContract {
  interface View : MVPContract.View {
    /** Set the subcategories that will be available to be displayed.*/
    fun setSubCategories(subCategories: List<SubCategory<Any>>)

    /** Make the provided subcategory as active. */
    fun showSubCategory(subCategory: SubCategory<out Any>)

    /** Set the title of the view. */
    fun setTitle(title: String)
  }

  abstract class Presenter<V : View>(val category: Category) : MVPPresenter<V>() {

  }
}