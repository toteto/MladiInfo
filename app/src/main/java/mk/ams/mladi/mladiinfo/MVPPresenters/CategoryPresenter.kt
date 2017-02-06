package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.MVPContracts.CategoryContract
import mk.ams.mladi.mladiinfo.MVPViews.CategoryFragment
import mk.ams.mladi.mladiinfo.ViewModels.Category

class CategoryPresenter(category: Category) : CategoryContract.Presenter<CategoryFragment>(category) {
  override fun attachView(view: CategoryFragment) {
    super.attachView(view)
    view.setSubCategories(category.getSubcategories())
    view.setTitle(category.name)
    val selectedSubcategory = category.selectedSubcategory
    if (selectedSubcategory != null) {
      view.showSubCategory(selectedSubcategory)
    }
  }
}