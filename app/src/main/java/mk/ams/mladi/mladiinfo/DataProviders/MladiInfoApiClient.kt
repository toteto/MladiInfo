package mk.ams.mladi.mladiinfo.DataProviders

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/** Client for accessing the apis for MladiInfo. */
class MladiInfoApiClient(val context: Context) {
  /** Headers used for header control. */
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

  /** Interceptor that is responsible for providing cache header to the responses. */
  private val RESPONSE_CACHE_INTERCEPTOR = Interceptor { chain ->
    val originalResponse = chain.proceed(chain.request())
    originalResponse.newBuilder()
        .header("Cache-Control",
            "public, max-age=${TimeUnit.MINUTES.toSeconds(CACHE_MINUTES_VALIDITY)}")
        .build()
  }

  /** Interceptor responsible for making the offline mode possible. */
  private val OFFLINE_RESPONSE_CACHE_INTERCEPTOR = Interceptor { chain ->
    var request = chain.request()
    if (context.getConnectivityManager().activeNetworkInfo?.isConnected?.not() ?: true) {
      request = request.newBuilder()
          .header("Cache-Control",
              "public, only-if-cached, max-stale=" + TimeUnit.DAYS.toSeconds(
                  OFFLINE_CACHE_VALIDITY))
          .build()
    }
    chain.proceed(request)
  }
}

fun Context.getConnectivityManager() = getSystemService(
    Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun <T> Call<T>.enqueueTrueSuccess(blockOnSuccess: (result: T, call: Call<T>) -> Unit,
                                   blockOnFailure: (call: Call<T>) -> Unit): Call<T> {
  enqueue(object : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>?) {
      val result = response?.body()
      if (response?.isSuccessful ?: false && result != null) {
        blockOnSuccess(result, call)
      } else {
        blockOnFailure(call)
      }
    }

    override fun onFailure(call: Call<T>, t: Throwable?) {
      blockOnFailure(call)
    }
  })
  return this
}