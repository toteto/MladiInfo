package mk.ams.mladi.mladiinfo

import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface

interface SubcategoryAdapterInterface {
  fun bindArticleItems(items: List<ArticleInterface>)
}