package mk.ams.mladi.mladiinfo.MVPPresenters

import android.os.Bundle
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.DataProviders.enqueueTrueSuccess
import mk.ams.mladi.mladiinfo.MVPContracts.CategoryContract
import mk.ams.mladi.mladiinfo.MVPViews.CategoryFragment
import mk.ams.mladi.mladiinfo.ViewModels.Category

class CategoryPresenter(val client: MladiInfoApiInterface, category: Category) : CategoryContract.Presenter<CategoryFragment>(category) {
  private var viewDetached = false
  override fun attachView(view: CategoryFragment, savedInstanceState: Bundle?) {
    super.attachView(view, savedInstanceState)
    viewDetached = false
    view.setTitle(category.name)

    // Attach the subcategories to the view
    val allSubcategories = category.subcategoryBundles.flatMap { it.subcategories }
    val changed = view.setSubCategories(allSubcategories)

    // Update the categories views only if they have changed
    if (changed) {
      allSubcategories.forEach {
        if (it.navItem.id == category.selectedSubcategory?.id) {
          view.showSubcategory(it)
        }
        /** Set the handler for updating the date. This will be called by the subcategory fragment*/
        it.setRequestDataUpdateHandler { loadData() }
      }
      loadData()
    }
  }

  /** Go through each of the subcategory bundles and get the data that is relevant to that bundle.
   * When the data is received, distribute it to each of the subcategories in it. */
  fun loadData() {
    val bundles = category.subcategoryBundles
    bundles.forEach { bundle ->
      bundle.call(client).enqueueTrueSuccess(blockOnSuccess = { data, call ->
        if (viewDetached.not()) {
          bundle.setNewDataToSubcategories(data)
        }
      }, blockOnFailure = {
        TODO("Implement on failure when loading data for subcategories.")
      })
    }
  }

  override fun detachView() {
    super.detachView()
    viewDetached = true
  }
}