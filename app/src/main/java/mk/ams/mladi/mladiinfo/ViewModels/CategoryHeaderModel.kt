package mk.ams.mladi.mladiinfo.ViewModels

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.synthetic.main.category_header.view.*
import mk.ams.mladi.mladiinfo.R

class CategoryHeaderModel(val title: String, val showMore: Boolean) : EpoxyModel<View>() {
  override fun getDefaultLayout() = R.layout.category_header

  override fun bind(view: View) {
    super.bind(view)
    view.tvHeaderTitle.text = title
    view.tvHeaderShowMore.visibility = if (showMore) View.VISIBLE else View.GONE
  }
}