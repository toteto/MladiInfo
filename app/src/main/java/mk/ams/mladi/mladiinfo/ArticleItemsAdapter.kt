package mk.ams.mladi.mladiinfo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import mk.ams.mladi.mladiinfo.ViewModels.ArticleItem

class ArticleItemsAdapter : RecyclerView.Adapter<ArticleItem.ArticleItemVH>() {
  var items: List<ArticleItem> = emptyList()
  override fun getItemCount() = items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleItem.ArticleItemVH(LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false))

  override fun onBindViewHolder(holder: ArticleItem.ArticleItemVH, position: Int) {
    holder.bindItem(items[position])
  }
}