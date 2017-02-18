package mk.ams.mladi.mladiinfo.DataModels

import android.support.annotation.ColorRes
import com.google.gson.annotations.SerializedName
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.parseMladiInfoDate
import mk.ams.mladi.mladiinfo.parseMladiInfoDescription
import java.util.*

data class Work(
    @SerializedName("CityID") val cityId: String,
    @SerializedName("CompanyName") val companyName: String,
    @SerializedName("CompanyWeb") val companyWebUrl: String,
    @SerializedName("Deadline") val deadline: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Duration") val duration: String,
    @SerializedName("EduInternshipTypeID") val internshipTypeId: String,
    @SerializedName("EduSiteID") val websiteID: String,
    @SerializedName("Email") val email: String,
    @SerializedName("ID") val id: String,
    @SerializedName("Inserted") val inserted: String,
    @SerializedName("Job") val workType: String,
    @SerializedName("Link") val moreInfoUrl: String,
    @SerializedName("Name") val name: String,
    @SerializedName("QualificationID") val qualificationId: String
) : ArticleInterface {
  enum class TYPE(val value: String, @ColorRes val dividerColor: Int) {
    INTERNSHIP("Internship", R.color.green),
    EMPLOYMENT("Job", R.color.deep_orange)
  }
  override fun getDividerColor(): Int = when (workType) {
    TYPE.INTERNSHIP.value -> TYPE.INTERNSHIP.dividerColor
    TYPE.EMPLOYMENT.value -> TYPE.EMPLOYMENT.dividerColor
    else -> R.color.orange
  }

  override fun getArticleId(): Long = id.toLong()

  override fun getArticleTitle(): String = name

  override fun getArticleDescription(): String = description.parseMladiInfoDescription(moreInfoUrl)

  override fun getArticleSiteName(): String = websiteID

  override fun getParsedDate(): Date = inserted.parseMladiInfoDate()

  override fun getArticleUrl(): String = moreInfoUrl

  fun isInternship(): Boolean = workType == TYPE.INTERNSHIP.value

  fun isEmployment(): Boolean = workType == TYPE.EMPLOYMENT.value
}