package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.SubcategoryAdapterInterface
import mk.ams.mladi.mladiinfo.ViewModels.SubCategory

interface SubcategoryContract {
  interface View : MvpLceContract.LCEView {
    fun getSubcategoryItemAdapter(): SubcategoryAdapterInterface
  }

  abstract class Presenter<V : View>(val subCategory: SubCategory<Any>) : MvpLceContract.LCEPresenter<V>()
}