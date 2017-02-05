package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.parseMladiInfoLatLong

data class Library(
    @SerializedName("Address") val address: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Email") val email: String?,
    @SerializedName("FB") val facebookUrl: String?,
    @SerializedName("Fax") val faxNumber: String?,
    @SerializedName("ID") val id: String,
    @SerializedName("Keywords") val keywords: String?,
    @SerializedName("LocationX") val locationX: String?,
    @SerializedName("LocationY") val locationY: String?,
    @SerializedName("Name") val name: String,
    @SerializedName("TW") val tw: String?,
    @SerializedName("Telephone") val phoneNumber: String?,
    @SerializedName("Website") val websiteUrl: String?
) : ContactInterface {
  override fun getContactTitle(): String = name

  override fun getContactDescription(): String? = description?.trim()

  override fun getContactPhone(): String? = phoneNumber

  override fun getContactMail(): String? = email

  override fun getContactAddress(): String? = address

  override fun getContactLatLong(): Pair<Double, Double>? = Pair(locationX, locationY).parseMladiInfoLatLong()

  override fun getContactSite(): String? = websiteUrl

  override fun getContactFacebookProfile(): String? = facebookUrl

  override fun getContactTwitterProfile(): String? = tw

  override fun getContactId(): Long = id.toLong()

  override fun getContactLogoUrl(): String? = null

  override fun getDividerColor(): Int = R.color.light_green
}