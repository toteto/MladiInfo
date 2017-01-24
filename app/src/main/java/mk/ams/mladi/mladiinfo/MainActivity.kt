package mk.ams.mladi.mladiinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.DataProviders.methodNoName
import retrofit2.Call

class MainActivity : AppCompatActivity() {
  private val LOG_TAG: String = MainActivity::class.java.simpleName
  lateinit var itemsAdapter: OverviewAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    itemsAdapter = OverviewAdapter(this)

    rvItems.adapter = itemsAdapter
    rvItems.layoutManager = LinearLayoutManager(this)
    srlRefresh.setOnRefreshListener {
      val client = MladiInfoApiClient(this).client

      client.getScholarships().getDataHandleRefresh { itemsAdapter.bindScholarships(it.slice(0 until 3)) }
      client.getWorkPostings().getDataHandleRefresh {
        val workPostings = it.partition { it.workType == "Internship" }
        itemsAdapter.bindInternships(workPostings.first.slice(0 until 3))
        itemsAdapter.bindEmployments(workPostings.second.slice(0 until 3))
      }
    }
  }

  fun <T> Call<T>.getDataHandleRefresh(blockOnSuccess: (result: T) -> Unit) {
    methodNoName({
      srlRefresh.isRefreshing = false
      blockOnSuccess(it)
    }, {
      srlRefresh.isRefreshing = false
      Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
    })
  }
}



