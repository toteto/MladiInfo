package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.MVPContracts.CategoryContract
import mk.ams.mladi.mladiinfo.MVPViews.CategoryFragment
import mk.ams.mladi.mladiinfo.ViewModels.Category

class CategoryPresenter(category: Category) : CategoryContract.Presenter<CategoryFragment>(category) {
  override fun attachView(view: CategoryFragment) {
    super.attachView(view)
    getView()?.setSubCategories(category.subCategories)
    getView()?.setTitle(category.name)
  }
}