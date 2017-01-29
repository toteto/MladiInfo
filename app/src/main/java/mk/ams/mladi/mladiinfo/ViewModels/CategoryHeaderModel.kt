package mk.ams.mladi.mladiinfo.ViewModels

import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.synthetic.main.category_header.view.*
import mk.ams.mladi.mladiinfo.R

class CategoryHeaderModel(val title: String, @ColorRes val headerColor: Int) : EpoxyModel<View>() {
  override fun getDefaultLayout() = R.layout.category_header

  override fun bind(view: View) {
    super.bind(view)
    view.tvHeaderTitle.text = title
    view.headerContainer.setBackgroundColor(ContextCompat.getColor(view.context, headerColor))
  }
}