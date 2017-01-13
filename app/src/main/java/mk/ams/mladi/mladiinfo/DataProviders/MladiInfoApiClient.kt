package mk.ams.mladi.mladiinfo.DataProviders

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MladiInfoApiClient {
  private object Holder {
    val INSTANCE: MladiInfoApiInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(MladiInfoApiInterface::class.java)
  }

  companion object {
    val BASE_URL = "http://mladi.ams.mk/eduservice.svc/"
    val client: MladiInfoApiInterface by lazy { Holder.INSTANCE }
  }
}