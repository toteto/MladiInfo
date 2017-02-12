package mk.ams.mladi.mladiinfo

import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface
import mk.ams.mladi.mladiinfo.DataModels.ContactInterface
import mk.ams.mladi.mladiinfo.DataModels.DiscountCard
import mk.ams.mladi.mladiinfo.DataModels.LinkItemInterface

interface SubcategoryAdapterInterface {
  fun bindArticleItems(items: List<ArticleInterface>)
  fun bindContactItems(items: List<ContactInterface>)
  fun bindDiscountCards(cards: List<DiscountCard>)
  fun bindLinkItems(items: List<LinkItemInterface>)
}