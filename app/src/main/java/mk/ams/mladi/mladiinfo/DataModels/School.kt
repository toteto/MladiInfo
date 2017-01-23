package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("Address") val address: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Email") val email: String,
    @SerializedName("FB") val facebookUrl: String,
    @SerializedName("Fax") val faxNumber: String,
    @SerializedName("Founded") val founded: String,
    @SerializedName("ID") val id: String,
    @SerializedName("Keywords") val keywords: String,
    @SerializedName("LocationX") val locationX: String,
    @SerializedName("LocationY") val locationY: String,
    @SerializedName("Name") val name: String,
    @SerializedName("SchoolType") val schoolType: String,
    @SerializedName("Shortcut") val shortcut: String,
    @SerializedName("TW") val tw: String,
    @SerializedName("Telephone") val phoneNumber: String,
    @SerializedName("TypeID") val typeId: String,
    @SerializedName("Website") val schoolWebUrl: String
): UrlInterface {
  override fun getUrl() = schoolWebUrl
}