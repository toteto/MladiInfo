package mk.ams.mladi.mladiinfo.DataProviders

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


class MladiInfoApiClient(val context: Context) {
  private val BASE_URL = "http://mladi.ams.mk/eduservice.svc/"
  val client: MladiInfoApiInterface by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(ResponseCacheInterceptor(10))
        .addInterceptor(OfflineResponseCacheInterceptor(context, 30))
        .cache(Cache(File(context.cacheDir, "mladiInfoCachedResponses"), 10 * 1024 * 1024))
        .build()

    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build().create(MladiInfoApiInterface::class.java)
  }

  private class ResponseCacheInterceptor(val minutesValid: Long) : Interceptor {
    @Throws(IOException::class)
    override
    fun intercept(chain: Interceptor.Chain): okhttp3.Response {
      val originalResponse = chain.proceed(chain.request())
      return originalResponse.newBuilder()
          .header("Cache-Control", "public, max-age=${TimeUnit.MINUTES.toSeconds(minutesValid)}")
          .build()
    }
  }

  private class OfflineResponseCacheInterceptor(val context: Context, val daysValid: Long) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
      var request = chain.request()
      if (context.getConnectivityManager().activeNetworkInfo?.isConnected?.not() ?: true) {
        request = request.newBuilder()
            .header("Cache-Control",
                "public, only-if-cached, max-stale=" + TimeUnit.DAYS.toSeconds(daysValid))
            .build()
      }
      return chain.proceed(request)
    }
  }
}

fun Context.getConnectivityManager() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager