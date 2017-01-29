package mk.ams.mladi.mladiinfo.ViewModels

import com.airbnb.epoxy.EpoxyModel
import java.util.*

/** Simple class used for wrapping single section with header and items.*/
class OverviewSection(headerTitle: String) {
  val header = CategoryHeaderModel(headerTitle, true)
  val models = ArrayList<EpoxyModel<*>>()
}