package mk.ams.mladi.mladiinfo.ViewModels

import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.synthetic.main.category_header.view.*
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import mk.ams.mladi.mladiinfo.R

class CategoryHeaderModel(val category: NAV_ITEMS, @ColorRes val headerColor: Int) : EpoxyModel<View>() {
  private var clickListener: View.OnClickListener? = null

  companion object {
    val CATEGORY_TAG_KEY = R.id.category_tag_key
  }

  override fun getDefaultLayout() = R.layout.category_header

  fun withOnClickListener(onClickListener: View.OnClickListener): CategoryHeaderModel {
    clickListener = onClickListener
    return this
  }

  override fun bind(view: View) {
    super.bind(view)
    view.setTag(CATEGORY_TAG_KEY, category)
    view.tvHeaderTitle.setText(category.title)
    view.headerContainer.setBackgroundColor(ContextCompat.getColor(view.context, headerColor))
    view.setOnClickListener(clickListener)
  }
}