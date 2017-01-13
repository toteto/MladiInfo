package mk.ams.mladi.mladiinfo.DataProviders

import android.content.Context
import mk.ams.mladi.mladiinfo.DataModels.*
import retrofit2.Call
import retrofit2.Response

class DataProvider(context: Context) : DataProviderServices {
  val apiClient = MladiInfoApiClient.client
  val cachedStorage = CachedDataProvider(context)

  override fun getTraining(callback: Callback<List<Training>>) {
    cachedStorage.getTraining(object : Callback<List<Training>> {
      override fun onSuccess(listData: List<Training>) {
        callback.onSuccess(listData)
      }

      override fun onFailure(message: String) {
        apiClient.getTraining().enqueue(object : retrofit2.Callback<List<Training>> {
          override fun onResponse(call: Call<List<Training>>?, response: Response<List<Training>>?) {
            if (response != null && response.isSuccessful) {
              cachedStorage.putTraining(response.body())
              callback.onSuccess(response.body())
            } else {
              throw RuntimeException("failed")
            }
          }

          override fun onFailure(call: Call<List<Training>>?, t: Throwable?) {
            callback.onFailure(t?.message ?: "Failed")
          }
        })
      }

    })
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