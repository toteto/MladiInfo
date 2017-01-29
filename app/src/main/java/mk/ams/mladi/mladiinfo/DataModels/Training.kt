package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName
import mk.ams.mladi.mladiinfo.parseMladiInfoDate
import mk.ams.mladi.mladiinfo.parseMladiInfoDescription
import java.util.*

class Training(
    @SerializedName("Conference") val type: String,
    @SerializedName("CrawlDate") val crawlDate: String,
    @SerializedName("Description") val description: String,
    @SerializedName("EduSiteName") val siteName: String,
    @SerializedName("ID") val id: String,
    @SerializedName("Link") val link: String,
    @SerializedName("Title") var title: String
) : ArticleInterface {
  enum class TYPE(val value: String) {
    SEMINAR("Seminar"),
    CONFERENCE("Conference")
  }

  override fun getArticleId(): String = id

  override fun getArticleTitle(): String = title

  override fun getArticleDescription(): String = description.parseMladiInfoDescription(link)

  override fun getArticleSiteName(): String = siteName

  override fun getArticlePublishDate(): Date = crawlDate.parseMladiInfoDate()

  override fun getArticleUrl(): String = link

  override fun searchArticle(query: String): Boolean {
    throw UnsupportedOperationException("not implemented")
  }

}

