package mk.ams.mladi.mladiinfo.DataProviders

import android.content.Context
import android.util.Log
import io.paperdb.Paper
import mk.ams.mladi.mladiinfo.DataModels.*
import retrofit2.Call
import retrofit2.Response

class DataProvider(context: Context) : DataProviderServices {
  val LOG_TAG: String = DataProvider::class.java.simpleName

  companion object {
    val DATA_VALIDITY_KEY = "data_validity_key"
  }

  /** The cached data that will remain valid for this amount of minutes.*/
  var DATA_VALIDITY = 15

  init {
    Paper.init(context)
  }

  enum class OFFLINE_FILE_NAME {
    TRAINING_FILE_KEY(),
    SEMINARS_FILE_KEY(),
    CONFERENCES_FILE_KEY(),
    WORKS_FILE_KEY(),
    INTERNSHIPS_FILE_KEY(),
    JOBS_FILE_KEY(),
    ORGANIZATIONS_FILE_KEY(),
    STUDENT_ORGANIZATIONS_FILE_KEY(),
    NON_GOVERNMENT_FILE_KEY(),
    SCHOLARSHIPS_FILE_KEY(),
    DORMS_FILE_KEY(),
    LIBRARIES_FILE_KEY(),
    UNIVERSITIES_FILE_KEY(),
    ARTICLES_FILE_KEY(),
    FACULTIES_FILE_KEY()
  }

  val apiClient = MladiInfoApiClient.client

  private fun <T> getCachedData(filename: OFFLINE_FILE_NAME): List<T>? = Paper.book().read(filename.name)

  private fun <T> storeDataToCache(filename: OFFLINE_FILE_NAME, data: List<T>) = Paper.book().write(filename.name, data)

  private fun <T> getDataFromApi(call: Call<List<T>>, callback: Callback<List<T>>) {
    call.enqueue(object : retrofit2.Callback<List<T>> {
      override fun onResponse(call: Call<List<T>>?, response: Response<List<T>>?) {
        if (response != null && response.isSuccessful) callback.onSuccess(response.body())
        else callback.onFailure(response?.message() ?: "Error!")
      }

      override fun onFailure(call: Call<List<T>>?, t: Throwable?) {
        Log.e(LOG_TAG, t?.message, t)
        callback.onFailure(t?.message ?: "Error!")
      }
    })
  }

  private fun <T> getDataFromApiAndCacheIt(call: Call<List<T>>, callback: Callback<List<T>>, filename: OFFLINE_FILE_NAME) {
    getDataFromApi(call, object : Callback<List<T>> {
      override fun onSuccess(listData: List<T>) {
        storeDataToCache(filename, listData)
        callback.onSuccess(listData)
      }

      override fun onFailure(message: String) {
        callback.onFailure(message)
      }
    })
  }

  private fun <T> getDataLikeABoss(call: Call<List<T>>, callback: Callback<List<T>>, filename: OFFLINE_FILE_NAME, forceUpdate: Boolean) {
    var done = false
    if (!forceUpdate && isCachedDataValid(filename)) {
      val res = getCachedData<T>(filename)
      if (res != null) {
        done = true
        callback.onSuccess(res)
      }
    }

    if (!done) {
      getDataFromApiAndCacheIt(call, callback, filename)
    }
  }

  private fun isCachedDataValid(filename: OFFLINE_FILE_NAME): Boolean {
    return Paper.book().exist(filename.name)
        && Paper.book().exist(filename.name + DATA_VALIDITY_KEY)
        && Paper.book().read<Int>(filename.name + DATA_VALIDITY_KEY) <= DATA_VALIDITY
  }

  override fun getTraining(callback: Callback<List<Training>>) {
    getDataLikeABoss(apiClient.getTraining(), callback, OFFLINE_FILE_NAME.TRAINING_FILE_KEY, false)
  }

  override fun getSeminars(callback: Callback<List<Training>>) {
    getTraining(object : Callback<List<Training>> {
      override fun onSuccess(listData: List<Training>) {
        callback.onSuccess(listData)
      }

      override fun onFailure(message: String) {
        callback.onFailure(message)
      }

    })
  }

  override fun getConferences(callback: Callback<List<Training>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getWorks(callback: Callback<List<Work>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getInternships(callback: Callback<List<Work>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getJobs(callback: Callback<List<Work>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getOrganizations(callback: Callback<List<Organization>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getStudentOrganizations(callback: Callback<List<Organization>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getNonGovernmentOrganizations(callback: Callback<List<Organization>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getScholarships(callback: Callback<List<Scholarship>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDorms(callback: Callback<List<Dorm>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getLibraries(callback: Callback<List<Library>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getUniversities(callback: Callback<List<School>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getFaculties(id: Int, callback: Callback<List<Faculty>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getArticles(callback: Callback<List<Article>>) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}