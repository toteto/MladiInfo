package mk.ams.mladi.mladiinfo

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.ViewModels.ArticleItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
  private val LOG_TAG: String = MainActivity::class.java.simpleName
  private val itemsAdapter = ArticleItemsAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    rvItems.adapter = itemsAdapter
    rvItems.layoutManager = LinearLayoutManager(this)
    val itemDecorator = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
    itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.training_item_divider))
    rvItems.addItemDecoration(itemDecorator)
    btnRefresh.setOnClickListener { MladiInfoApiClient(this).client.getTraining().enqueue(responseCallback) }
  }

  val responseCallback: Callback<List<Training>> = object : Callback<List<Training>> {
    override fun onResponse(call: Call<List<Training>>?, response: Response<List<Training>>?) {

      if (response?.isSuccessful ?: false && response?.body() != null) {
        itemsAdapter.items = buildArticleItems(response?.body() as List<Training>)
        itemsAdapter.notifyDataSetChanged()
      }
    }

    override fun onFailure(call: Call<List<Training>>?, t: Throwable?) {
      Toast.makeText(this@MainActivity, t?.message ?: "Api call failed", Toast.LENGTH_SHORT).show()
      t?.printStackTrace()
    }
  }

  override fun onResume() {
    super.onResume()
  }

  fun buildArticleItems(items: List<Training>): List<ArticleItem> = items.flatMap { listOf(ArticleItem(it.title, it.description, it.crawlDate, it.siteName)) }
}

