package mk.ams.mladi.mladiinfo.DataProviders

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MladiInfoApiInterface {
  @GET("GetTrainings")
  fun getTraining(): Call<String>

  @GET("GetInternships")
  fun getInternships(): Call<String>

  @GET("GetOrganizations")
  fun getOrganizations(): Call<String>

  @GET("GetListings")
  fun getScholarships(): Call<String>

  @GET("GetDorms")
  fun getDorms(): Call<String>

  @GET("GetLibraries")
  fun getLibraries(): Call<String>

  @GET("GetUniversities")
  fun getUniversities(): Call<String>

  @GET("GetFaculties/{universityId]")
  fun getFaculties(@Path("universityId") universityId: Int): Call<String>

  @GET("GetArticles")
  fun getArticles(): Call<String>
}