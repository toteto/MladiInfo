package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.parseMladiInfoDate
import mk.ams.mladi.mladiinfo.parseMladiInfoDescription
import java.util.*

data class Scholarship(
    @SerializedName("CrawlDate") val crawlDate: String,
    @SerializedName("Description") val description: String,
    @SerializedName("EduSiteName") val siteName: String,
    @SerializedName("ID") val id: String,
    @SerializedName("Link") val websiteUrl: String,
    @SerializedName("Title") val title: String
) : ArticleInterface {
  override fun getDividerColor(): Int = R.color.orange

  override fun getArticleTitle(): String = title

  override fun getArticleDescription(): String = description.parseMladiInfoDescription(websiteUrl)

  override fun getArticleSiteName(): String = siteName

  override fun getArticlePublishDate(): Date = crawlDate.parseMladiInfoDate()

  override fun getArticleUrl(): String = websiteUrl

  override fun searchArticle(query: String): Boolean {
    throw UnsupportedOperationException("not implemented")
  }

  override fun getArticleId(): Long = id.toLong()
}