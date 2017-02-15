package mk.ams.mladi.mladiinfo.notifications

import android.content.Context
import android.content.SharedPreferences
import mk.ams.mladi.mladiinfo.R

/** Persistent storage of notification preferences of the user and the developer. */
class NotificationPreferences(context: Context) {
  companion object {
    private val NOTIFICATION_PREFERENCES_KEY = "NotificationPreferences"
    private val supportedListings = listOf(
        R.id.scholarships,
        R.id.internships, R.id.employments,
        R.id.seminars, R.id.conferences)

    fun areNotificationsForListingSupported(id: Int) = supportedListings.contains(id)
  }

  private val sharedPreferences: SharedPreferences = context.getSharedPreferences(NOTIFICATION_PREFERENCES_KEY, Context.MODE_PRIVATE)

  fun areNotificationsEnabled() = sharedPreferences.getBoolean(NOTIFICATION_PREFERENCES_KEY, true)

  fun setNotificationsEnabled(enabled: Boolean) = sharedPreferences.edit().putBoolean(NOTIFICATION_PREFERENCES_KEY, enabled).apply()

  fun areNotificationsEnabledForListing(id: Int) = sharedPreferences.getBoolean(id.toString(), true)

  fun setNotificationsEnabledForListing(id: Int, enabled: Boolean = true) = sharedPreferences.edit().putBoolean(id.toString(), enabled).apply()

  fun getSupportedSubcategories(): List<Int> =
      if (areNotificationsEnabled()) supportedListings.filter { areNotificationsEnabledForListing(it) }
      else emptyList()

  fun shouldEnableNotificationService(): Boolean = areNotificationsEnabled() && getSupportedSubcategories().isNotEmpty()
}

fun Context.getNotificationPreferences(): NotificationPreferences = NotificationPreferences(this)