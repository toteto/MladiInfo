package mk.ams.mladi.mladiinfo.DataModels

import android.support.annotation.ColorRes
import com.google.gson.annotations.SerializedName
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.parseMladiInfoLatLong

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
  override fun getContactId(): Long = id.toLong()

  enum class TYPE(val value: String, @ColorRes val color: Int) {
    STUDENT("Студентска организација", R.color.blue),
    NON_GOVERNMENTAL("Организација", R.color.blue_grey)
  }

  override fun getDividerColor(): Int = when (student) {
    TYPE.STUDENT.value -> TYPE.STUDENT.color
    TYPE.NON_GOVERNMENTAL.value -> TYPE.NON_GOVERNMENTAL.color
    else -> R.color.secondary_text
  }

  override fun getContactTitle(): String = name.trim()

  override fun getContactDescription(): String? = description?.trim()

  override fun getContactPhone(): String? = phoneNumber?.trim()

  override fun getContactMail(): String? = email?.trim()

  override fun getContactAddress(): String? = address?.trim()

  override fun getContactLatLong(): Pair<Double, Double>? = Pair(locationX, locationY).parseMladiInfoLatLong()

  override fun getContactSite(): String? = websiteUrl?.trim()

  override fun getContactFacebookProfile(): String? = facebookUrl?.trim()

  override fun getContactTwitterProfile(): String? = tw?.trim()

  override fun getContactLogoUrl(): String? {
    if (student == TYPE.STUDENT.value) {
      return "http://mladi.ams.mk/images/orgs/$id.png"
    }
    return null
  }
}