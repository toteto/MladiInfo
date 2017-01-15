package mk.ams.mladi.mladiinfo.DataProviders

import mk.ams.mladi.mladiinfo.DataModels.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MladiInfoApiInterface {
  @GET("GetTrainings")
  fun getTraining(): Call<List<Training>>

  @GET("GetInternships")
  fun getWorkPostings(): Call<List<Work>>

  @GET("GetOrganizations")
  fun getOrganizations(): Call<List<Organization>>

  @GET("GetListings")
  fun getScholarships(): Call<List<Scholarship>>

  @GET("GetDorms")
  fun getDorms(): Call<List<Dorm>>

  @GET("GetLibraries")
  fun getLibraries(): Call<List<Library>>

  @GET("GetUniversities")
  fun getUniversities(): Call<List<School>>

  @GET("GetFaculties/{universityId]")
  fun getFaculties(@Path("universityId") universityId: Int): Call<Faculty>

  @GET("GetArticles")
  fun getArticles(): Call<List<Article>>
}