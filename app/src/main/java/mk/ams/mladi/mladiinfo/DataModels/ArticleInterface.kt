package mk.ams.mladi.mladiinfo.DataModels

interface ArticleInterface : DividerColorInterface, DateInterface {
    fun getArticleId(): Long
    fun getArticleTitle(): String
    fun getArticleDescription(): String
    fun getArticleSiteName(): String
    fun getArticleUrl(): String
    fun searchArticle(query: String): Boolean
}