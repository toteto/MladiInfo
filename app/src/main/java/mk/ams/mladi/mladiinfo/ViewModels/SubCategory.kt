package mk.ams.mladi.mladiinfo.ViewModels

import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.SubcategoryAdapter
import retrofit2.Call

class SubCategory<T>(
    val name: String,
    val call: (client: MladiInfoApiInterface) -> Call<T>,
    val dataPreprocessor: (data: T) -> T = { it },
    val bindDataTo: (data: T, adapter: SubcategoryAdapter) -> Unit)