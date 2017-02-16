package mk.ams.mladi.mladiinfo.notifications

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import java.util.*

class MarkAsReadService : IntentService(MarkAsReadService::class.java.simpleName) {
  companion object {
    val RESULTS_KEY = "mark_as_read"
    val NOTIFICATION_ID_KEY = "notification_id_key"

    fun getIntent(context: Context, subcategories: List<Int>, notificationId: Int): Intent {
      val intent = Intent(context, MarkAsReadService::class.java)
      intent.putExtra(RESULTS_KEY, subcategories.toIntArray())
      intent.putExtra(NOTIFICATION_ID_KEY, notificationId)
      return intent
    }
  }

  override fun onHandleIntent(intent: Intent?) {
    val store = LastArticleReadStore(this)
    val now = Date()
    intent?.getIntArrayExtra(RESULTS_KEY)?.forEach { store.storeLastArticleDate(it, now) }
    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        .cancel(intent?.extras?.getInt(NOTIFICATION_ID_KEY) ?: NotificationJobService.NOTIFICATION_ID)
  }
}