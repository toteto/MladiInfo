package mk.ams.mladi.mladiinfo.ViewModels

import android.text.Html
import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.article_item_with_image.view.*
import mk.ams.mladi.mladiinfo.DataModels.Article
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.parseMladiInfoDate
import mk.ams.mladi.mladiinfo.setTextWithVisibility
import mk.ams.mladi.mladiinfo.toRelativeTime
import java.net.URL

class ArticleWithImageModel(val article: Article) : EpoxyModel<View>(article.id.toLong()) {
  override fun getDefaultLayout(): Int = R.layout.article_item_with_image

  override fun bind(view: View) {
    super.bind(view)

    // Thumbnail and source
    if (article.thumbnailUrl.isNullOrEmpty()) {
      view.tvArticleSource.text = ""
      view.ivArticleImage.setImageResource(R.drawable.article_image_placeholder)
    } else {
      view.tvArticleSource.text = URL(article.thumbnailUrl).host
      Glide.with(view.context).load(article.thumbnailUrl)
          .placeholder(R.drawable.article_image_placeholder).into(view.ivArticleImage)
    }
    // Title
    view.tvArticleTitle.text = article.title.trim()

    // Description
    val description = Html.fromHtml(article.description)?.toString()
    view.tvArticleDescription.setTextWithVisibility(description)

    // Date
    view.tvArticleData.text = article.date.parseMladiInfoDate().toRelativeTime(view.context)


  }
}