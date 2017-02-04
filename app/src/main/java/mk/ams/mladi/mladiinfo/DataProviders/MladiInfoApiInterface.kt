package mk.ams.mladi.mladiinfo.DataProviders

import mk.ams.mladi.mladiinfo.DataModels.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MladiInfoApiInterface {
  @GET("GetTrainings")
  fun getTraining(@Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<List<Training>>

  @GET("GetInternships")
  fun getWorkPostings(@Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<List<Work>>

  @GET("GetOrganizations")
  fun getOrganizations(@Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<List<Organization>>

  @GET("GetListings")
  fun getScholarships(@Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<List<Scholarship>>

  @GET("GetDorms")
  fun getDorms(@Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<List<Dorm>>

  @GET("GetLibraries")
  fun getLibraries(@Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<List<Library>>

  @GET("GetUniversities")
  fun getUniversities(@Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<List<EducationalInstitution>>

  @GET("GetFaculties/{universityId]")
  fun getFaculties(@Path("universityId") universityId: Int, @Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<Faculty>

  @GET("GetArticles")
  fun getArticles(@Header("Cache-Control") cacheControl: String? = MladiInfoApiClient.CACHE_CONTROL.MAYBE_CACHED.value): Call<List<Article>>
}