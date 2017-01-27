package mk.ams.mladi.mladiinfo.DataModels

import java.util.*

interface ArticleInterface {
  fun getArticleId(): String
  fun getArticleTitle(): String
  fun getArticleDescription(): String
  fun getArticleSiteName(): String
  fun getArticlePublishDate(): Date
  fun getArticleUrl(): String
  fun searchArticle(query: String): Boolean
}