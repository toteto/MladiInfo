package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName

data class Faculty(
    @SerializedName("Address")
    val address: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("FB")
    val facebookUrl: String,
    @SerializedName("Fax")
    val faxNumber: String,
    @SerializedName("Founded")
    val yearFounded: String,
    @SerializedName("ID")
    val id: String,
    @SerializedName("Keywords")
    val keywords: String,
    @SerializedName("LocationX")
    val locationX: String,
    @SerializedName("LocationY")
    val locationY: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("SchoolType")
    val schoolType: String,
    @SerializedName("TW")
    val tw: String,
    @SerializedName("Telephone")
    val phoneNumber: String,
    @SerializedName("Website")
    val websiteUrl: String
)