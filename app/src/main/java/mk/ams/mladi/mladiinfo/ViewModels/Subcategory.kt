package mk.ams.mladi.mladiinfo.ViewModels

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.SubcategoryAdapterInterface
import retrofit2.Call

class Subcategory<T>(
    val name: String,
    val call: (client: MladiInfoApiInterface) -> Call<List<T>>,
    val dataPreprocessor: (data: List<T>) -> List<T> = { it },
    val bindDataTo: (data: List<T>, adapter: SubcategoryAdapterInterface) -> Unit)