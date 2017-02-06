package mk.ams.mladi.mladiinfo.ViewModels

import android.support.annotation.ColorRes
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiInterface
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.SubcategoryAdapterInterface
import retrofit2.Call

class Subcategory<T>(
    val navItem: NAV_ITEMS,
    val call: (client: MladiInfoApiInterface) -> Call<List<T>>,
    val dataPreprocessor: (data: List<T>) -> List<T> = { it },
    val bindDataTo: (data: List<T>, adapter: SubcategoryAdapterInterface) -> Unit,
    @ColorRes val color: Int = R.color.secondary_text)