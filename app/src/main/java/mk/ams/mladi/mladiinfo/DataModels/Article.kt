package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("ArticleCategoryID") val articleCategory: String,
    @SerializedName("Date") val date: String,
    @SerializedName("Description") val description: String,
    @SerializedName("ID") val id: String,
    @SerializedName("Text") val text: String,
    @SerializedName("Thumbnail") val thumbnailUrl: String,
    @SerializedName("Title") val title: String
)