package mk.ams.mladi.mladiinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
  private val LOG_TAG: String = MainActivity::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    lvItems.adapter = ArrayAdapter<Work>(this, android.R.layout.simple_list_item_1)
    btnRefresh.setOnClickListener { MladiInfoApiClient(this).client.getInternships().enqueue(responseCallback) }
  }

  val responseCallback: Callback<List<Work>> = object : Callback<List<Work>> {
    override fun onResponse(call: Call<List<Work>>?, response: Response<List<Work>>?) {
      lvItems.adapter = ArrayAdapter<Work>(this@MainActivity, android.R.layout.simple_list_item_1, response?.body())
    }

    override fun onFailure(call: Call<List<Work>>?, t: Throwable?) {
      Toast.makeText(this@MainActivity, t?.message ?: "Api call failed", Toast.LENGTH_SHORT).show()
      t?.printStackTrace()
    }

  }

  override fun onResume() {
    super.onResume()
  }
}

