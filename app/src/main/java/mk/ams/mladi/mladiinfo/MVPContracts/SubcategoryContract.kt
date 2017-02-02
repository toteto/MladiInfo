package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.SubcategoryAdapterInterface
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory

interface SubcategoryContract {
  interface View : MvpLceContract.LCEView {
    fun getSubcategoryItemAdapter(): SubcategoryAdapterInterface
  }

  abstract class Presenter<V : View>(val subcategory: Subcategory<Any>) : MvpLceContract.LCEPresenter<V>()
}