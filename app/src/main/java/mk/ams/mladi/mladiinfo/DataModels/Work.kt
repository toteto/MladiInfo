package mk.ams.mladi.mladiinfo.DataModels

import com.google.gson.annotations.SerializedName

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
) : UrlInterface {
  override fun getUrl() = moreInfoUrl
}