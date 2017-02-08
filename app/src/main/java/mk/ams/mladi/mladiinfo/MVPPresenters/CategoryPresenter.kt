package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.DataProviders.enqueueTrueSuccess
import mk.ams.mladi.mladiinfo.MVPContracts.CategoryContract
import mk.ams.mladi.mladiinfo.MVPViews.CategoryFragment
import mk.ams.mladi.mladiinfo.ViewModels.Category

class CategoryPresenter(val client: MladiInfoApiInterface, category: Category) : CategoryContract.Presenter<CategoryFragment>(category) {
  override fun attachView(view: CategoryFragment) {
    super.attachView(view)
    view.setTitle(category.name)

    view.setSubCategories(category.subcategoryBundles.flatMap { it.subcategories })
    category.subcategoryBundles.forEach {
      it.subcategories.forEach {
        it.setRequestDataUpdateHandler { loadData() }
      }
    }
    loadData()
  }

  /** Go through each of the subcategory bundles and get the data that is relevant to that bundle.
   * When the data is received, distribute it to each of the subcategories in it. */
  fun loadData() {
    val bundles = category.subcategoryBundles
    bundles.forEach { bundle ->
      bundle.call(client).enqueueTrueSuccess(blockOnSuccess = { data, call ->
        bundle.setNewDataToSubcategories(data)
      }, blockOnFailure = {
        TODO("Implement on failure when loading data for subcategories.")
      })
    }
  }
}