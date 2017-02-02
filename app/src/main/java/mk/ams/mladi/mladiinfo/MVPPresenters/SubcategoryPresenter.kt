package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.SubcategoryContract
import mk.ams.mladi.mladiinfo.MVPViews.SubcategoryFragment
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory

class SubcategoryPresenter(val client: MladiInfoApiInterface, subcategory: Subcategory<Any>) : SubcategoryContract.Presenter<SubcategoryFragment>(subcategory) {
  override fun loadData(forceUpdate: Boolean) {
    enqueueCall(subcategory.call(client),
        {
          val view = getView()
          if (view != null) {
            val pData = subcategory.dataPreprocessor(it)
            subcategory.bindDataTo(pData, view.getSubcategoryItemAdapter())
          }
        })
  }
}
