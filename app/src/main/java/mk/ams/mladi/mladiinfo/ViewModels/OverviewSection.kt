package mk.ams.mladi.mladiinfo.ViewModels

import android.support.annotation.ColorRes
import com.airbnb.epoxy.EpoxyModel
import java.util.*

/** Simple class used for wrapping single section with header and items.*/
class OverviewSection(headerTitle: String, @ColorRes val headerColor: Int) {
  val header = CategoryHeaderModel(headerTitle, headerColor)
  val models = ArrayList<EpoxyModel<*>>()
}