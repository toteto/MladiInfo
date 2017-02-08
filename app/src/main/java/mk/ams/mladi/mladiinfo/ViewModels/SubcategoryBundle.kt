package mk.ams.mladi.mladiinfo.ViewModels

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import retrofit2.Call
import java.util.*

/** A bundle of subcategories that will receive data from the same service call.
 * @param call the service call that will provide the data for the subcategories.
 * @param subcategories the subcategories that will receive and handle the data from the call. */
class SubcategoryBundle<T>(
    val call: (client: MladiInfoApiInterface) -> Call<List<T>>,
    val subcategories: List<Subcategory<T>> = ArrayList<Subcategory<T>>()
) {

  /** Distributes the new data that is provided to the subcategories in this bundle. */
  fun setNewDataToSubcategories(newData: List<T>) {
    subcategories.forEach { it.setNewData(newData) }
  }
}