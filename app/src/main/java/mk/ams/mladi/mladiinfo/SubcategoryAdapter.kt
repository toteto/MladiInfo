package mk.ams.mladi.mladiinfo

import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel
import mk.ams.mladi.mladiinfo.DataModels.*
import mk.ams.mladi.mladiinfo.ViewModels.*

class SubcategoryAdapter : EpoxyAdapter(), SubcategoryAdapterInterface {
  var notificationModel: NotificationModel? = null

  init {
    enableDiffing()
  }

  override fun withNotificationSwitch(id: Int) {
    notificationModel = NotificationModel(id)
  }

  override fun bindArticleItems(items: List<ArticleInterface>) = bindModels(items.map(::ArticleModel))

  override fun bindContactItems(items: List<ContactInterface>) = bindModels(items.map(::ContactModel))

  override fun bindDiscountCards(cards: List<DiscountCard>) = bindModels(cards.map(::DiscountCardModel))

  override fun bindLinkItems(items: List<LinkItemInterface>) = bindModels(items.map(::LinkItemModel))

  override fun bindArticleItemsWithImage(items: List<Article>) = bindModels(items.map(::ArticleWithImageModel))

  private fun bindModels(newModels: List<EpoxyModel<*>>) {
    models.clear()
    if (notificationModel != null) {
      models.add(notificationModel)
    }
    models.addAll(newModels)
    notifyModelsChanged()
  }
}