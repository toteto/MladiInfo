package mk.ams.mladi.mladiinfo.notifications

import android.app.IntentService
import android.content.Context
import android.content.Intent
import java.util.*

class MarkAsReadService : IntentService(MarkAsReadService::class.java.simpleName) {
  companion object {
    val RESULTS_KEY = "mark_as_read"

    fun getIntent(context: Context, subcategories: List<Int>): Intent {
      val intent = Intent(context, MarkAsReadService::class.java)
      intent.putExtra(RESULTS_KEY, subcategories.toIntArray())
      return intent
    }
  }

  override fun onHandleIntent(intent: Intent?) {
    val store = LastArticleReadStore(this)
    val now = Date()
    intent?.getIntArrayExtra(RESULTS_KEY)?.forEach { store.storeLastArticleDate(it, now) }
  }
}