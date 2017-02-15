package mk.ams.mladi.mladiinfo.notifications

import android.content.Context
import android.content.Context.MODE_PRIVATE
import mk.ams.mladi.mladiinfo.parseMladiInfoDate
import java.util.*

class UnseenArticlesStore(context: Context) {
  private val sharedPreferences = context.getSharedPreferences(UnseenArticlesStore::class.java.simpleName, MODE_PRIVATE)

  /** Returns the date of the last stored article with the provided category id.*/
  fun getLastStoredArticleDate(id: Int): Date? {
    if (sharedPreferences.contains(id.toString())) {
      return Date(sharedPreferences.getLong(id.toString(), Long.MAX_VALUE))
    } else {
      return null
    }
  }

  fun storeLastArticleDate(id: Int, date: Date) {
    sharedPreferences.edit().putLong(id.toString(), date.time).apply()
  }

  fun storeLastMladiInfoDate(id: Int, mladiInfoDate: String) {
    storeLastArticleDate(id, mladiInfoDate.parseMladiInfoDate())
  }
}