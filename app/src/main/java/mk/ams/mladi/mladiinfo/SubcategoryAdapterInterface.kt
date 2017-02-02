package mk.ams.mladi.mladiinfo

import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface
import mk.ams.mladi.mladiinfo.DataModels.ContactInterface

interface SubcategoryAdapterInterface {
  fun bindArticleItems(items: List<ArticleInterface>)
  fun bindContactItems(items: List<ContactInterface>)
}