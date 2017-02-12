package mk.ams.mladi.mladiinfo.DataModels

import mk.ams.mladi.mladiinfo.R

data class MladiInfoLink(val name: String, val url: String) : LinkItemInterface {
  override fun getLinkItemUrl(): String = url

  override fun getLinkItemTitle(): String = name

  override fun getDividerColor(): Int = R.color.dark_yellow

  override fun getLinkItemIcon(): Int = if (url.endsWith(".pdf")) R.drawable.icon_pdf else R.drawable.icon_website
}