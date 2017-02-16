package mk.ams.mladi.mladiinfo.MVPContracts

import android.support.annotation.StringRes
import mk.ams.mladi.mladiinfo.ViewModels.Category
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory

interface CategoryContract {
  interface View : MVPContract.View {
    /** Set the subcategories that will be available to be displayed.
     * @return true if the categories in the view have been changed. */
    fun setSubCategories(subcategories: List<Subcategory<Any>>): Boolean

    /** Make the provided subcategory as active. */
    fun showSubcategory(subcategory: Subcategory<Any>)

    /** Set the title of the view. */
    fun setTitle(title: String)

    fun showError(@StringRes strId: Int)
  }

  abstract class Presenter<V : View>(val category: Category) : MVPPresenter<V>() {
  }
}