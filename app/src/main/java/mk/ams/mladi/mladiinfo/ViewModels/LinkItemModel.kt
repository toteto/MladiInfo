package mk.ams.mladi.mladiinfo.ViewModels

import android.support.v4.content.ContextCompat
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.synthetic.main.link_item.view.*
import mk.ams.mladi.mladiinfo.DataModels.LinkItemInterface
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.openWebsite

class LinkItemModel(val item: LinkItemInterface) : EpoxyModel<View>(item.hashCode().toLong()) {
  override fun getDefaultLayout(): Int = R.layout.link_item

  override fun bind(view: View) {
    super.bind(view)
    view.tvLinkItemTitle.text = item.getLinkItemTitle()
    view.ivLinkItemIcon.setImageResource(item.getLinkItemIcon())
    view.itemDivider.setBackgroundColor(ContextCompat.getColor(view.context, item.getDividerColor()))
    view.setOnClickListener { view.context.openWebsite(item.getLinkItemUrl()) }
  }
}