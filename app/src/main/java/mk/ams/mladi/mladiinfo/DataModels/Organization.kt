package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName

class Organization(
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
    @SerializedName("Student") val student: String,
    @SerializedName("TW") val tw: String?,
    @SerializedName("Telephone") val phoneNumber: String?,
    @SerializedName("Website") val websiteUrl: String?
) : ContactInterface {
  enum class TYPE(val value: String) {
    STUDENT("Студентска организација"),
    GENERAL("Организација")
  }

  override fun getContactTitle(): String = name.trim()

  override fun getContactDescription(): String? = description?.trim()

  override fun getContactPhone(): String? = phoneNumber?.trim()

  override fun getContactMail(): String? = email?.trim()

  override fun getContactAddress(): String? = address?.trim()

  override fun getContactLatLong(): Pair<Double, Double>? {
    if (locationY != null && locationX != null) {
      return Pair(locationX.toDouble(), locationY.toDouble())
    }
    return null
  }

  override fun getContactSite(): String? = websiteUrl?.trim()

  override fun getContactFacebookProfile(): String? {
    throw UnsupportedOperationException("not implemented")
  }

  override fun getContactTwitterProfile(): String? = tw?.trim()
}