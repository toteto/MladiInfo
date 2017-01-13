package mk.ams.mladi.mladiinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataProviders.Callback
import mk.ams.mladi.mladiinfo.DataProviders.DataProvider

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onResume() {
    super.onResume()
    DataProvider(this).getTraining(object : Callback<List<Training>> {
      override fun onSuccess(listData: List<Training>) {
        Log.d("asd", listData.toString())
      }

      override fun onFailure(message: String) {
        Log.e("asd", message)
      }

    })
  }
}