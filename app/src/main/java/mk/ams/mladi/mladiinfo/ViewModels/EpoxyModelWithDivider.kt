package mk.ams.mladi.mladiinfo.ViewModels

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder

/** Abstract class that provides mechanism for telling the model implementation that the current
 * model should include/exclude a item divider. Default visibility is VISIBLE.*/
abstract class EpoxyModelWithDivider<T : EpoxyHolder>(id: Long) : EpoxyModelWithHolder<T>(id) {
  protected var dividerVisible: Int = View.VISIBLE
    private set

  /** This model will be bound with the provided visibility for the divider.*/
  fun withDivider(show: Boolean) {
    dividerVisible = if (show) View.VISIBLE else View.GONE
  }
}