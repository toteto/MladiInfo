package mk.ams.mladi.mladiinfo

import android.support.annotation.ColorRes
import com.airbnb.epoxy.EpoxyAdapter
import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface
import mk.ams.mladi.mladiinfo.DataModels.ContactInterface
import mk.ams.mladi.mladiinfo.ViewModels.ArticleModel
import mk.ams.mladi.mladiinfo.ViewModels.ContactModel

class SubcategoryAdapter : EpoxyAdapter(), SubcategoryAdapterInterface {
  init {
    enableDiffing()
  }

  override fun bindArticleItems(items: List<ArticleInterface>, @ColorRes dividerColor: Int) {
    models.clear()
    models.addAll(items.flatMap { listOf(ArticleModel(it)) })
    notifyModelsChanged()
  }

  override fun bindContactItems(items: List<ContactInterface>, @ColorRes dividerColor: Int) {
    models.clear()
    models.addAll(items.flatMap { listOf(ContactModel(it).withDividerColor(dividerColor)) })
    notifyModelsChanged()
  }
}