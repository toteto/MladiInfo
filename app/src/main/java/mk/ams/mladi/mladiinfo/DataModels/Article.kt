package mk.ams.mladi.mladiinfo.DataModels

import android.support.annotation.ColorRes
import com.google.gson.annotations.SerializedName
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.parseMladiInfoDate
import java.util.*

data class Article(
    @SerializedName("ArticleCategoryID") val articleCategory: String,
    @SerializedName("Date") val date: String,
    @SerializedName("Description") val description: String?,
    @SerializedName("ID") val id: String,
    @SerializedName("Text") val text: String?,
    @SerializedName("Thumbnail") val thumbnailUrl: String?,
    @SerializedName("Title") val title: String
) : DateInterface {
  enum class TYPE(val value: String, @ColorRes val dividerColor: Int) {
    TRENDING("Актуелно", R.color.orange),
    PROJECT("Проекти", R.color.blue_grey)
  }

  fun isTrendingArticle() = articleCategory == TYPE.TRENDING.value

  fun isProject() = articleCategory == TYPE.PROJECT.value

  override fun getParsedDate(): Date = date.parseMladiInfoDate()
}