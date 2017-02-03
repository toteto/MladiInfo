package mk.ams.mladi.mladiinfo

import android.support.annotation.ColorRes
import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface
import mk.ams.mladi.mladiinfo.DataModels.ContactInterface

interface SubcategoryAdapterInterface {
  fun bindArticleItems(items: List<ArticleInterface>, @ColorRes dividerColor: Int = R.color.secondary_text)
  fun bindContactItems(items: List<ContactInterface>, @ColorRes dividerColor: Int = R.color.secondary_text)
}