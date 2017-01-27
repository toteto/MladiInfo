package mk.ams.mladi.mladiinfo.ViewModels

import android.text.format.DateUtils
import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelWithHolder
import kotlinx.android.synthetic.main.article_item.view.*
import mk.ams.mladi.mladiinfo.DataModels.ArticleInterface
import mk.ams.mladi.mladiinfo.R
import java.util.*

class ArticleModel(val title: String, val description: String, val date: Date, val source: String)
  : EpoxyModelWithHolder<ArticleModel.ArticleItemVH>() {
  var id = hashCode().toLong()

  constructor(dataModel: ArticleInterface) :
      this(dataModel.getArticleTitle().trim(), dataModel.getArticleDescription().trim(),
          dataModel.getArticlePublishDate(), dataModel.getArticleSiteName().trim()) {
    id(dataModel.getArticleId().toLong())
  }

  override fun createNewHolder() = ArticleItemVH()

  override fun getDefaultLayout() = R.layout.article_item

  override fun bind(holder: ArticleItemVH) {
    val dateStr = DateUtils.getRelativeDateTimeString(holder.itemView.context,
        date.time,
        DateUtils.MINUTE_IN_MILLIS,
        DateUtils.WEEK_IN_MILLIS,
        0)
    holder.itemView.context
    holder.itemView.tvArticleTitle.text = title
    holder.itemView.tvArticleDescription.text = description
    holder.itemView.tvArticleData.text = dateStr
    holder.itemView.tvArticleSource.text = source
  }

  class ArticleItemVH : EpoxyHolder() {
    lateinit var itemView: View
    override fun bindView(itemView: View) {
      this.itemView = itemView
    }
  }

  override fun id(): Long {
    return id
  }

  override fun id(id: Long): EpoxyModel<ArticleItemVH> {
    this.id = id
    return this
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other?.javaClass != javaClass) return false
    if (!super.equals(other)) return false

    other as ArticleModel

    if (title != other.title) return false
    if (date != other.date) return false
    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + title.hashCode()
    result = 31 * result + date.hashCode()
    result = 31 * result + id.hashCode()
    return result
  }


}
