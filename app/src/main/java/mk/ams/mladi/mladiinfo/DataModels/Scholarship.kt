package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName

data class Scholarship(
    @SerializedName("CrawlDate")
    val crawlDate: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("EduSiteName")
    val siteName: String,
    @SerializedName("ID")
    val id: String,
    @SerializedName("Link")
    val websiteUrl: String,
    @SerializedName("Title")
    val title: String
)