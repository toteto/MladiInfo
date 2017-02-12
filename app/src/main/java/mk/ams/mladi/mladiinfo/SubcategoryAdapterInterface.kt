package mk.ams.mladi.mladiinfo

import mk.ams.mladi.mladiinfo.DataModels.*

interface SubcategoryAdapterInterface {
  fun bindArticleItems(items: List<ArticleInterface>)
  fun bindArticleItemsWithImage(items: List<Article>)
  fun bindContactItems(items: List<ContactInterface>)
  fun bindDiscountCards(cards: List<DiscountCard>)
  fun bindLinkItems(items: List<LinkItemInterface>)
}