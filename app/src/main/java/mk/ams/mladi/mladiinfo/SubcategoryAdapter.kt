package mk.ams.mladi.mladiinfo

import com.airbnb.epoxy.EpoxyAdapter
import mk.ams.mladi.mladiinfo.DataModels.*
import mk.ams.mladi.mladiinfo.ViewModels.*

class SubcategoryAdapter : EpoxyAdapter(), SubcategoryAdapterInterface {
  init {
    enableDiffing()
  }

  override fun bindArticleItems(items: List<ArticleInterface>) {
    models.clear()
    models.addAll(items.flatMap { listOf(ArticleModel(it)) })
    notifyModelsChanged()
  }

  override fun bindContactItems(items: List<ContactInterface>) {
    models.clear()
    models.addAll(items.flatMap { listOf(ContactModel(it)) })
    notifyModelsChanged()
  }

  override fun bindDiscountCards(cards: List<DiscountCard>) {
    models.clear()
    models.addAll(cards.map(::DiscountCardModel))
    notifyModelsChanged()
  }

  override fun bindLinkItems(items: List<LinkItemInterface>) {
    models.clear()
    models.addAll(items.map(::LinkItemModel))
    notifyModelsChanged()
  }

  override fun bindArticleItemsWithImage(items: List<Article>) {
    models.clear()
    models.addAll(items.map(::ArticleWithImageModel))
    notifyModelsChanged()
  }
}