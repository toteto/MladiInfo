package mk.ams.mladi.mladiinfo.notifications

import android.content.Context
import android.util.Log
import com.firebase.jobdispatcher.*
import mk.ams.mladi.mladiinfo.DataModels.DateInterface
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient
import mk.ams.mladi.mladiinfo.DataProviders.MladiInfoApiClient.CACHE_CONTROL.NO_CACHE
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import mk.ams.mladi.mladiinfo.R
import retrofit2.Call
import java.util.*

/** Service that is responsible for retrieving and processing data from the services that will be used
 * to build notifications for the user.  */
class NotificationJobService : JobService() {
  companion object {
    val SERVICE_TAG = "MLADI_INFO_NOTIFICATION_SERVICE"
    private val LOG_TAG: String = NotificationJobService::class.java.simpleName

    fun buildJob(dispatcher: FirebaseJobDispatcher): Job = dispatcher.newJobBuilder()
        .setService(NotificationJobService::class.java)
        .setTag(NotificationJobService.SERVICE_TAG)
        .setRecurring(true)
        .setLifetime(Lifetime.FOREVER)
        .setTrigger(Trigger.executionWindow(5, 10))
        .setReplaceCurrent(false)
        .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
        .setConstraints(Constraint.ON_ANY_NETWORK)
        .build()

    fun selfCancel(dispatcher: FirebaseJobDispatcher) = dispatcher.cancel(SERVICE_TAG)

    fun managedBasedOnPreferences(context: Context) {
      if (context.getNotificationPreferences().shouldEnableNotificationService()) {
        context.scheduleNotificationService()
      } else {
        context.cancelNotificationService()
      }
    }

    fun enableNotifications(context: Context, toEnable: Boolean = true) {
      context.getNotificationPreferences().setNotificationsEnabled(toEnable)
      managedBasedOnPreferences(context)
    }
  }

  override fun onStartJob(job: JobParameters): Boolean {
    Thread {
      val subcategoriesWithNotificationsEnabled = getNotificationPreferences().getSupportedSubcategories()
      if (subcategoriesWithNotificationsEnabled.isEmpty()) {
        jobFinished(job, false) // Cancel the job because no enabled notifications were found
      } else {
        val threads = buildSyncThreads(subcategoriesWithNotificationsEnabled)
        threads.forEach { it.start() }
        val sb = StringBuilder()
        threads.forEach {
          it.join()
          if (it.result != null) {
            val title = getString(NAV_ITEMS.getItemById(it.id)?.title ?: R.string.restart /*fixme*/)
            sb.append("${it.result} unread articles for $title\n")
          }
        }
        Log.d(LOG_TAG, sb.toString())
        jobFinished(job, true)
      }
    }.start()
    return true // Answers the question: "Is there still work going on?"
  }

  /** @param ids the id of each subcategory that supports and has notifications enabled. */
  private fun buildSyncThreads(ids: List<Int>): ArrayList<SyncThread<out DateInterface>> {
    val threads = ArrayList<SyncThread<out DateInterface>>(ids.size)
    val store = LastArticleReadStore(this)
    val client = MladiInfoApiClient(this).client
    ids.forEach {
      when (it) {
        R.id.scholarships ->
          threads.add(SyncThread(it, client.getScholarships(NO_CACHE.value), store))
        R.id.internships ->
          threads.add(SyncThread(it, client.getWorkPostings(NO_CACHE.value), store, Work::isInternship))
        R.id.employments ->
          threads.add(SyncThread(it, client.getWorkPostings(NO_CACHE.value), store, Work::isEmployment))
        R.id.seminars ->
          threads.add(SyncThread(it, client.getTraining(NO_CACHE.value), store, Training::isSeminar))
        R.id.conferences ->
          threads.add(SyncThread(it, client.getTraining(NO_CACHE.value), store, Training::isConference))
        else -> Log.e(LOG_TAG, "Notification for subcategory: ${NAV_ITEMS.getItemById(it)}, is not supported.")
      }
    }
    return threads
  }

  override fun onStopJob(params: JobParameters?): Boolean {
    return true // Answers the question: "Should this job be retried?"
  }

  /** Thread that will make the provided call and compare the dates of the received content with
   * last read from that same subcategory.
   * @param id the id of the subcategory
   * @param call the call needed to get the content of the subcategory
   * @return the unread articles*/
  private class SyncThread<T : DateInterface>(
      val id: Int, val call: Call<List<T>>, val store: LastArticleReadStore,
      val filterCondition: (article: T) -> Boolean = { true }) : Thread() {

    var result: Int? = null
    override fun run() {
      val articles = call.execute()?.body()
      if (articles != null) {
        val lastReadDate = store.getLastArticleReadDate(id)
        result = articles.filter {
          filterCondition(it) && it.getParsedDate().after(lastReadDate)
        }.size
      }
    }
  }
}

fun Context.scheduleNotificationService(dispatcher: FirebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))) {
  val notificationJob = NotificationJobService.buildJob(dispatcher)
  try {
    dispatcher.mustSchedule(notificationJob)
  } catch (e: FirebaseJobDispatcher.ScheduleFailedException) {
    Log.e(javaClass.simpleName, e.message)
  }
}

fun Context.cancelNotificationService(dispatcher: FirebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))) {
  NotificationJobService.selfCancel(dispatcher)
}