package mk.ams.mladi.mladiinfo.notifications

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.util.*
import java.util.concurrent.TimeUnit

/** Persistent storage of for the dates of the last read article in an subcategory. */
class LastArticleReadStore(context: Context) {
  companion object {
    val LAST_ARTICLE_STORE_KEY = "LastArticleReadStore"
  }

  private val sharedPreferences = context.getSharedPreferences(LAST_ARTICLE_STORE_KEY, MODE_PRIVATE)

  /** Returns the date of the last stored article with the provided category id.
   * @param fallbackDate date that will be used when there is no record of article read for this id.
   * Default value for [fallbackDate] will be one day in the past. Note: if [fallbackDate] is used,
   * [storeLastArticleDate] will automatically be called with this date.*/
  fun getLastArticleReadDate(id: Int,
                             fallbackDate: Long = Date().time - TimeUnit.DAYS.toMillis(1)): Date {
    if (sharedPreferences.contains(id.toString())) {
      return Date(sharedPreferences.getLong(id.toString(), fallbackDate))
    } else {
      storeLastArticleDate(id, Date(fallbackDate))
      return Date(fallbackDate)
    }
  }

  /** Assigns the provided [date] to this [id].*/
  fun storeLastArticleDate(id: Int, date: Date) {
    sharedPreferences.edit().putLong(id.toString(), date.time).apply()
  }
}