package mk.ams.mladi.mladiinfo.DataProviders

import android.content.Context
import android.util.Log
import mk.ams.mladi.mladiinfo.DataModels.*
import java.io.ObjectInputStream

class CachedDataProvider(val context: Context) : DataProviderServices {
  val LOG_TAG = CachedDataProvider::class.java.simpleName
  val client = MladiInfoApiClient.client

  companion object {
    val TRAINING_FILE_KEY = "training"
    val SEMINARS_FILE_KEY = "seminars"
    val CONFERENCES_FILE_KEY = "conferences"
    val WORKS_FILE_KEY = "works"
    val INTERNSHIPS_FILE_KEY = "internships"
    val JOBS_FILE_KEY = "jobs"
    val ORGANIZATIONS_FILE_KEY = "organizations"
    val STUDENT_ORGANIZATIONS_FILE_KEY = "student_organizations"
    val NON_GOVERNMENT_FILE_KEY = "non_government_organizations"
    val SCHOLARSHIPS_FILE_KEY = "scholarships"
    val DORMS_FILE_KEY = "dorms"
    val LIBRARIES_FILE_KEY = "libraries"
    val UNIVERSITIES_FILE_KEY = "universities"
    fun FACULTIES_FILE_KEY(id: Int) = "faculties_" + id
    val ARTICLES_FILE_KEY = "articles"
  }

  fun <T> getData(filename: String, callback: Callback<List<T>>) {
    Thread(Runnable {
      try {
        @Suppress("UNCHECKED_CAST")
        val result = ObjectInputStream(context.openFileInput(TRAINING_FILE_KEY)).readObject() as List<T>
        callback.onSuccess(result)
      } catch (e: Exception) {
        callback.onFaulure(e.message ?: "Failed cached data retrieval.")
        Log.e(LOG_TAG, e.message, e)
      }
    })
  }

  override fun getTraining(callback: Callback<List<Training>>) = getData(TRAINING_FILE_KEY, callback)

  override fun getSeminars(callback: Callback<List<Training>>) = getData(SEMINARS_FILE_KEY, callback)

  override fun getConferences(callback: Callback<List<Training>>) = getData(CONFERENCES_FILE_KEY, callback)

  override fun getJobsAndInternships(callback: Callback<List<Work>>) = getData(WORKS_FILE_KEY, callback)

  override fun getInternships(callback: Callback<List<Work>>) = getData(INTERNSHIPS_FILE_KEY, callback)

  override fun getJobs(callback: Callback<List<Work>>) = getData(JOBS_FILE_KEY, callback)

  override fun getOrganizations(callback: Callback<List<Organization>>) = getData(ORGANIZATIONS_FILE_KEY, callback)

  override fun getStudentOrganizations(callback: Callback<List<Organization>>) = getData(STUDENT_ORGANIZATIONS_FILE_KEY, callback)

  override fun getNonGovernmentOrganizations(callback: Callback<List<Organization>>) = getData(NON_GOVERNMENT_FILE_KEY, callback)

  override fun getScholarships(callback: Callback<List<Scholarship>>) = getData(SCHOLARSHIPS_FILE_KEY, callback)

  override fun getDorms(callback: Callback<List<Dorm>>) = getData(DORMS_FILE_KEY, callback)

  override fun getLibraries(callback: Callback<List<Library>>) = getData(LIBRARIES_FILE_KEY, callback)

  override fun getUniversities(callback: Callback<List<School>>) = getData(UNIVERSITIES_FILE_KEY, callback)

  override fun getFaculties(id: Int, callback: Callback<List<Faculty>>) = getData(FACULTIES_FILE_KEY(id), callback)

  override fun getArticles(callback: Callback<List<Article>>) = getData(ARTICLES_FILE_KEY, callback)


  fun setTraining(input: List<Training>) {

  }

  fun setSeminars(input: List<Training>) {

  }

  fun setConferences(input: List<Training>) {

  }

  fun setJobsAndInternships(input: List<Work>) {

  }

  fun setInternships(input: List<Work>) {

  }

  fun setJobs(input: List<Work>) {

  }

  fun setOrganizations(input: List<Organization>) {

  }

  fun setStudentOrganizations(input: List<Organization>) {

  }

  fun setNonGovernmentOrganizations(input: List<Organization>) {

  }

  fun setScholarships(input: List<Scholarship>) {

  }

  fun setDorms(input: List<Dorm>) {

  }

  fun setLibraries(input: List<Library>) {

  }

  fun setUniversities(input: List<School>) {

  }

  fun setFaculties(inputId: Int, input: List<School>) {

  }

  fun setArticles(input: List<Article>) {

  }
}