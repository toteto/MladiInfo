package mk.ams.mladi.mladiinfo.DataModels

import android.support.annotation.ColorRes
import mk.ams.mladi.mladiinfo.R

interface DividerColorInterface {
  @ColorRes
  fun getDividerColor(): Int = R.color.orange
}