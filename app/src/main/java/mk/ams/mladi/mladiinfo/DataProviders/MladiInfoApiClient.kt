package mk.ams.mladi.mladiinfo.DataProviders

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


class MladiInfoApiClient(val context: Context) {
  enum class CACHE_CONTROL(val value: String?) {
    NO_CACHE("no-cache"),
    MAYBE_CACHED(null)
  }

  private val BASE_URL = "http://mladi.ams.mk/eduservice.svc/"
  private val CACHE_MINUTES_VALIDITY: Long = 10
  private val OFFLINE_CACHE_VALIDITY: Long = 7
  val client: MladiInfoApiInterface by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(RESPONSE_CACHE_INTERCEPTOR)
        .addInterceptor(OFFLINE_RESPONSE_CACHE_INTERCEPTOR)
        .cache(Cache(File(context.cacheDir, "mladiInfoCachedResponses"), 10 * 1024 * 1024))
        .build()

    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build().create(MladiInfoApiInterface::class.java)
  }

  val RESPONSE_CACHE_INTERCEPTOR = Interceptor { chain ->
    val originalResponse = chain.proceed(chain.request())
    originalResponse.newBuilder()
        .header("Cache-Control", "public, max-age=${TimeUnit.MINUTES.toSeconds(CACHE_MINUTES_VALIDITY)}")
        .build()
  }

  private val OFFLINE_RESPONSE_CACHE_INTERCEPTOR = Interceptor { chain ->
    var request = chain.request()
    if (context.getConnectivityManager().activeNetworkInfo?.isConnected?.not() ?: true) {
      request = request.newBuilder()
          .header("Cache-Control",
              "public, only-if-cached, max-stale=" + TimeUnit.DAYS.toSeconds(OFFLINE_CACHE_VALIDITY))
          .build()
    }
    chain.proceed(request)
  }
}

fun Context.getConnectivityManager() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager