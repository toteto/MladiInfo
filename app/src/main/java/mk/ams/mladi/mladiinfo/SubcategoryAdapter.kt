package mk.ams.mladi.mladiinfo

import com.airbnb.epoxy.EpoxyAdapter
import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface
import mk.ams.mladi.mladiinfo.DataModels.ContactInterface
import mk.ams.mladi.mladiinfo.DataModels.DiscountCard
import mk.ams.mladi.mladiinfo.ViewModels.ArticleModel
import mk.ams.mladi.mladiinfo.ViewModels.ContactModel
import mk.ams.mladi.mladiinfo.ViewModels.DiscountCardModel

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
}