package mk.ams.mladi.mladiinfo.DataModels

import android.support.annotation.DrawableRes
import mk.ams.mladi.mladiinfo.R

/** Interface for reading the data from the data class and provided it to [ArticleModel][mk.ams.mladi.mladiinfo.ViewModels.LinkItemModel]*/
interface LinkItemInterface : DividerColorInterface {
  fun getLinkItemTitle(): String
  fun getLinkItemUrl(): String
  @DrawableRes fun getLinkItemIcon(): Int = R.drawable.icon_link
  fun queryItem(query: String): Boolean = getLinkItemTitle().contains(query, true)
}