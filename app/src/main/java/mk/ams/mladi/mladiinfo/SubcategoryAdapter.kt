package mk.ams.mladi.mladiinfo

import com.airbnb.epoxy.EpoxyAdapter
import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface
import mk.ams.mladi.mladiinfo.ViewModels.ArticleModel

class SubcategoryAdapter : EpoxyAdapter() {
  init {
    enableDiffing()
  }

  fun bindArticleItems(items: List<ArticleInterface>) {
    models.clear()
    models.addAll(items.flatMap { listOf(ArticleModel(it)) })
    notifyModelsChanged()
  }
}