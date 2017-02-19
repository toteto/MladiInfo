package mk.ams.mladi.mladiinfo.DataModels

import android.support.annotation.ColorRes
import mk.ams.mladi.mladiinfo.R

/** Interface that provides method for defining color of the dividers for the model class. */
interface DividerColorInterface {
  @ColorRes
  fun getDividerColor(): Int = R.color.orange
}