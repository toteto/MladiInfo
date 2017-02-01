package mk.ams.mladi.mladiinfo.MVPPresenters

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.MVPContracts.SubcategoryContract
import mk.ams.mladi.mladiinfo.MVPViews.SubcategoryFragment
import mk.ams.mladi.mladiinfo.ViewModels.SubCategory

class SubcategoryPresenter(val client: MladiInfoApiInterface, subCategory: SubCategory<Any>) : SubcategoryContract.Presenter<SubcategoryFragment>(subCategory) {
  override fun loadData(forceUpdate: Boolean) {
    enqueueCall(subCategory.call(client),
        {
          val view = getView()
          if (view != null) {
            val pData = subCategory.dataPreprocessor(it)
            subCategory.bindDataTo(pData, view.getSubcategoryItemAdapter())
          }
        })
  }
}
