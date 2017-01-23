package mk.ams.mladi.mladiinfo.ViewModels

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelWithHolder
import kotlinx.android.synthetic.main.article_item.view.*
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.R

class ArticleModel(val title: String, val description: String, val date: String, val source: String)
  : EpoxyModelWithHolder<ArticleModel.ArticleItemVH>() {
  constructor(training: Training) : this(training.title, training.description, training.crawlDate, training.siteName)

  override fun createNewHolder() = ArticleItemVH()

  override fun getDefaultLayout() = R.layout.article_item

  override fun bind(holder: ArticleItemVH) {
    holder.itemView.tvArticleTitle.text = title
    holder.itemView.tvArticleDescription.text = description
    holder.itemView.tvArticleData.text = date
    holder.itemView.tvArticleSource.text = source
  }


  class ArticleItemVH : EpoxyHolder() {
    lateinit var itemView: View
    override fun bindView(itemView: View) {
      this.itemView = itemView
    }
  }

  override fun id(): Long = 31 * title.hashCode().toLong() + description.hashCode().toLong()

  override fun id(id: Long): EpoxyModel<ArticleItemVH> {
    return this
  }
}
