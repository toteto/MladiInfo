package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName

class Training(
    @SerializedName("Conference") val type: String,
    @SerializedName("CrawlDate") val crawlDate: String,
    @SerializedName("Description") val description: String,
    @SerializedName("EduSiteName") val siteName: String,
    @SerializedName("ID") val id: String,
    @SerializedName("Link") val link: String,
    @SerializedName("Title") var title: String
) : UrlInterface {
  override fun getUrl() = link
}

