package mk.ams.mladi.mladiinfo.DataProviders

import android.content.Context
import android.util.Log
import mk.ams.mladi.mladiinfo.DataModels.*
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream

class CachedDataProvider(val context: Context) : DataProviderServices {
  val LOG_TAG: String = CachedDataProvider::class.java.simpleName

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
      var inputStream: InputStream? = null
      try {
        inputStream = context.openFileInput(filename)
        @Suppress("UNCHECKED_CAST")
        val result = ObjectInputStream(inputStream).readObject() as List<T>
        callback.onSuccess(result)
      } catch (e: Exception) {
        callback.onFailure(e.message ?: "Failed cached data retrieval.")
        Log.e(LOG_TAG, e.message, e)
      } finally {
        inputStream?.close()
      }
    }).start()
  }

  fun <T> putData(filename: String, data: List<T>) {
    Thread(Runnable {
      var outputStream: OutputStream? = null
      try {
        outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
        ObjectOutputStream(outputStream).writeObject(data)
      } catch (e: Exception) {
        throw RuntimeException(e)
      } finally {
        outputStream?.close()
      }
    }).start()
  }

  override fun getTraining(callback: Callback<List<Training>>) = getData(TRAINING_FILE_KEY, callback)

  override fun getSeminars(callback: Callback<List<Training>>) = getData(SEMINARS_FILE_KEY, callback)

  override fun getConferences(callback: Callback<List<Training>>) = getData(CONFERENCES_FILE_KEY, callback)

  override fun getWorks(callback: Callback<List<Work>>) = getData(WORKS_FILE_KEY, callback)

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

  fun putTraining(input: List<Training>) = putData(TRAINING_FILE_KEY, input)

  fun putSeminars(input: List<Training>) = putData(SEMINARS_FILE_KEY, input)

  fun putConferences(input: List<Training>) = putData(CONFERENCES_FILE_KEY, input)

  fun putWorks(input: List<Work>) = putData(WORKS_FILE_KEY, input)

  fun putInternships(input: List<Work>) = putData(INTERNSHIPS_FILE_KEY, input)

  fun putJobs(input: List<Work>) = putData(JOBS_FILE_KEY, input)

  fun putOrganizations(input: List<Organization>) = putData(ORGANIZATIONS_FILE_KEY, input)

  fun putStudentOrganizations(input: List<Organization>) = putData(STUDENT_ORGANIZATIONS_FILE_KEY, input)

  fun putNonGovernmentOrganizations(input: List<Organization>) = putData(NON_GOVERNMENT_FILE_KEY, input)

  fun putScholarships(input: List<Scholarship>) = putData(SCHOLARSHIPS_FILE_KEY, input)

  fun putDorms(input: List<Dorm>) = putData(DORMS_FILE_KEY, input)

  fun putLibraries(input: List<Library>) = putData(LIBRARIES_FILE_KEY, input)

  fun putUniversities(input: List<School>) = putData(UNIVERSITIES_FILE_KEY, input)

  fun putFaculties(inputId: Int, input: List<School>) = putData(FACULTIES_FILE_KEY(inputId), input)

  fun putArticles(input: List<Article>) = putData(ARTICLES_FILE_KEY, input)
}