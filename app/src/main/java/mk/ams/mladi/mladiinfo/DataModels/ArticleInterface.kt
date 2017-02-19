package mk.ams.mladi.mladiinfo.DataModels

/** Interface for reading the data from the data class and provided it to [ArticleModel][mk.ams.mladi.mladiinfo.ViewModels.ArticleModel]*/
interface ArticleInterface : DividerColorInterface, DateInterface {
  /** Id of the article, it needs to be unique. */
  fun getArticleId(): Long

  /** Title for the article.*/
  fun getArticleTitle(): String

  fun getArticleDescription(): String
  fun getArticleSiteName(): String
  /** An URL that will be loaded once the article is chooses by the user.*/
  fun getArticleUrl(): String

  /** Search the article for the provided query.
   * @return true if the article matches the query*/
  fun searchArticle(query: String): Boolean = getArticleTitle().contains(query, true)
}