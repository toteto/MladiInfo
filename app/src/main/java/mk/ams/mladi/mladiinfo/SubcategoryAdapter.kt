package mk.ams.mladi.mladiinfo

import com.airbnb.epoxy.EpoxyAdapter
import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface
import mk.ams.mladi.mladiinfo.DataModels.ContactInterface
import mk.ams.mladi.mladiinfo.ViewModels.ArticleModel
import mk.ams.mladi.mladiinfo.ViewModels.ContactModel

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
}