package mk.ams.mladi.mladiinfo.ViewModels

import android.view.View
import kotlinx.android.synthetic.main.article_item.view.*

class ArticleItem(val title: String, val description: String, val date: String, val source: String) {
  class ArticleItemVH(itemView: View) : BindableViewHolder<ArticleItem>(itemView) {
    override fun bindItem(item: ArticleItem) {
      itemView.tvArticleTitle.text = item.title
      itemView.tvArticleDescription.text = item.description
      itemView.tvArticleData.text = item.date
      itemView.tvArticleSource.text = item.source
    }
  }
}
