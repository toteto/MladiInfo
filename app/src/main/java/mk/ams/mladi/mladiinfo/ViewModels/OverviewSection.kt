package mk.ams.mladi.mladiinfo.ViewModels

import android.support.annotation.ColorRes
import com.airbnb.epoxy.EpoxyModel
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import java.util.*

/** Simple class used for wrapping single section with header and items.*/
class OverviewSection(category: NAV_ITEMS, @ColorRes val headerColor: Int) {
  val header = CategoryHeaderModel(category, headerColor)
  val models = ArrayList<EpoxyModel<*>>()
}