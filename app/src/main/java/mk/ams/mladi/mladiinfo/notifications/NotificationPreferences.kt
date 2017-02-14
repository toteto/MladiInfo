package mk.ams.mladi.mladiinfo.notifications

import android.content.Context
import android.content.SharedPreferences
import mk.ams.mladi.mladiinfo.R

class NotificationPreferences(context: Context) {
  companion object {
    private val NOTIFICATION_PREFERENCES_KEY = "NotificationPreferences"
    private val supportedListings = listOf(
        R.id.scholarships,
        R.id.internships, R.id.employments,
        R.id.seminars, R.id.conferences)

    fun areNotificationsForListingSupported(id: Int) = supportedListings.contains(id)
  }

  val sharedPreferences: SharedPreferences = context.getSharedPreferences(NOTIFICATION_PREFERENCES_KEY, Context.MODE_PRIVATE)

  fun areNotificationsEnabled() = sharedPreferences.getBoolean(NOTIFICATION_PREFERENCES_KEY, true)

  fun areNotificationsEnabledForListing(id: Int) = sharedPreferences.getBoolean(id.toString(), false)

  fun setNotificationsEnabledForListing(id: Int, enabled: Boolean = true) = sharedPreferences.edit().putBoolean(id.toString(), enabled).apply()
}

fun Context.getNotificationPreferences(): NotificationPreferences = NotificationPreferences(this)