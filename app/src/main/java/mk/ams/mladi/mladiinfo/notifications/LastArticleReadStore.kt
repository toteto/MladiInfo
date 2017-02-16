package mk.ams.mladi.mladiinfo.notifications

import android.content.Context
import android.content.Context.MODE_PRIVATE
import mk.ams.mladi.mladiinfo.parseMladiInfoDate
import java.util.*
import java.util.concurrent.TimeUnit

/** Persistent storage of for the dates of the last read article in an subcategory. */
class LastArticleReadStore(context: Context) {
  companion object {
    val LAST_ARTICLE_STORE_KEY = "LastArticleReadStore"
  }

  private val sharedPreferences = context.getSharedPreferences(LAST_ARTICLE_STORE_KEY, MODE_PRIVATE)

  /** Returns the date of the last stored article with the provided category id.*/
  fun getLastArticleReadDate(id: Int): Date {
    val fallbackDate = Date().time - TimeUnit.DAYS.toMillis(1)
    if (sharedPreferences.contains(id.toString())) {
      return Date(sharedPreferences.getLong(id.toString(), fallbackDate))
    } else {
      storeLastArticleDate(id, Date(fallbackDate))
      return Date(fallbackDate)
    }
  }

  fun storeLastArticleDate(id: Int, date: Date) {
    sharedPreferences.edit().putLong(id.toString(), date.time).apply()
  }
}