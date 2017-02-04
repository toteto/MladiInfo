package mk.ams.mladi.mladiinfo.DataModels

import android.support.annotation.ColorRes
import com.google.gson.annotations.SerializedName
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.parseMladiInfoLatLong

data class EducationalInstitution(
    @SerializedName("Address") val address: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Email") val email: String?,
    @SerializedName("FB") val facebookUrl: String?,
    @SerializedName("Fax") val faxNumber: String?,
    @SerializedName("Founded") val founded: String?,
    @SerializedName("ID") val id: String,
    @SerializedName("Keywords") val keywords: String?,
    @SerializedName("LocationX") val locationX: String?,
    @SerializedName("LocationY") val locationY: String?,
    @SerializedName("Name") val name: String,
    @SerializedName("SchoolType") val schoolType: String,
    @SerializedName("Shortcut") val shortcut: String?,
    @SerializedName("TW") val tw: String?,
    @SerializedName("Telephone") val phoneNumber: String?,
    @SerializedName("TypeID") val typeId: String,
    @SerializedName("Website") val schoolWebUrl: String?
) : ContactInterface {
  enum class TYPE(val key: String, @ColorRes val color: Int) {
    HIGH_SCHOOL("Средни училишта", R.color.dark_yellow),
    STATE_UNIVERSITY("Државни универзитети", R.color.orange),
    PRIVATE_UNIVERSITY("Приватни универзитети", R.color.dark_orange),
    OTHER_HIGHER_EDUCATION("Останати високообразовни установи", R.color.deep_orange)
  }

  override fun getContactTitle(): String = name.trim()

  override fun getContactDescription(): String? {
    if (description != null && description.contains("<img")) {
      val startIndex = description.indexOf("/>") + 2
      return description.substring(startIndex).trim()
    } else {
      return description?.trim()
    }
  }

  override fun getContactPhone(): String? = phoneNumber

  override fun getContactMail(): String? = email

  override fun getContactAddress(): String? = address

  override fun getContactLatLong(): Pair<Double, Double>? = Pair(locationX, locationY).parseMladiInfoLatLong()

  override fun getContactSite(): String? = schoolWebUrl

  override fun getContactFacebookProfile(): String? = facebookUrl

  override fun getContactTwitterProfile(): String? = tw

  override fun getContactId(): Long = id.toLong()

  override fun getContactLogoUrl(): String? = when (typeId) {
    TYPE.STATE_UNIVERSITY.key, TYPE.PRIVATE_UNIVERSITY.key, TYPE.OTHER_HIGHER_EDUCATION.key -> "http://mladi.ams.mk/images/universities/$id.png"
    else -> null
  }
}