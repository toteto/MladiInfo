package mk.ams.mladi.mladiinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onResume() {
    super.onResume()
    MladiInfoApiClient.client.getTraining().enqueue(object : Callback<String> {
      override fun onResponse(call: Call<String>?, response: Response<String>?) {
        val rawData = response?.body().toString()
        val trainings: List<Training> = Gson().fromJson(rawData, object : TypeToken<List<Training>>() {}.type)
        Log.d("asd", trainings.toString())
      }

      override fun onFailure(call: Call<String>?, t: Throwable?) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    })
  }
}
