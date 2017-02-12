package mk.ams.mladi.mladiinfo.ViewModels

import android.text.Html
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.discount_card_item.view.*
import mk.ams.mladi.mladiinfo.DataModels.DiscountCard
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.openWebsite

class DiscountCardModel(val discountCard: DiscountCard) : EpoxyModel<View>(discountCard.hashCode().toLong()) {
  override fun getDefaultLayout(): Int = R.layout.discount_card_item

  override fun bind(view: View) {
    super.bind(view)
    Glide.with(view.context).load(discountCard.imgUrl).placeholder(R.drawable.discount_card_placeholder).into(view.discountCardImage)
    view.discountCardDescription.text = Html.fromHtml(discountCard.data)
    view.discountCardMoreInfoButton.setOnClickListener { view.context.openWebsite(discountCard.moreInfoUrl) }
  }
}