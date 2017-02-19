package mk.ams.mladi.mladiinfo.notifications

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import java.util.*

/** Background service that is used to mark articles as read from a notification.*/
class MarkAsReadService : IntentService(MarkAsReadService::class.java.simpleName) {
  companion object {
    val RESULTS_KEY = "mark_as_read"
    val NOTIFICATION_ID_KEY = "notification_id_key"

    /**@param subcategories the ids of subcategories that will update the [LastArticleReadStore].
     * @param notificationId the id of the notification, used for canceling it. */
    fun getIntent(context: Context, subcategories: List<Int>, notificationId: Int): Intent {
      val intent = Intent(context, MarkAsReadService::class.java)
      intent.putExtra(RESULTS_KEY, subcategories.toIntArray())
      intent.putExtra(NOTIFICATION_ID_KEY, notificationId)
      return intent
    }
  }

  /** Will go through all the [subcategories][RESULTS_KEY] and mark the as freshly read. */
  override fun onHandleIntent(intent: Intent?) {
    val store = LastArticleReadStore(this)
    val now = Date()
    intent?.getIntArrayExtra(RESULTS_KEY)?.forEach { store.storeLastArticleDate(it, now) }
    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        .cancel(
            intent?.extras?.getInt(NOTIFICATION_ID_KEY) ?: NotificationJobService.NOTIFICATION_ID)
  }
}