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

  private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
      NOTIFICATION_PREFERENCES_KEY, Context.MODE_PRIVATE)

  /** Returns true if notifications are enabled in general.*/
  fun areNotificationsEnabled() = sharedPreferences.getBoolean(NOTIFICATION_PREFERENCES_KEY, true)

  /** Enables or disabled the notification in general.*/
  fun setNotificationsEnabled(enabled: Boolean) = sharedPreferences.edit().putBoolean(
      NOTIFICATION_PREFERENCES_KEY, enabled).apply()

  /** Returns true if notifications are enabled for the listing with the provided [id]*/
  fun areNotificationsEnabledForListing(id: Int) = sharedPreferences.getBoolean(id.toString(), true)

  fun setNotificationsEnabledForListing(id: Int,
                                        enabled: Boolean = true) = sharedPreferences.edit().putBoolean(
      id.toString(), enabled).apply()

  /** Returns a list of ids that identify subcategories that support notifications.*/
  fun getSupportedSubcategories(): List<Int> =
      if (areNotificationsEnabled()) supportedListings.filter {
        areNotificationsEnabledForListing(it)
      }
      else emptyList()

  /** Helper method that returns whether notifications should be enabled.*/
  fun shouldEnableNotificationService(): Boolean = areNotificationsEnabled() && getSupportedSubcategories().isNotEmpty()
}

fun Context.getNotificationPreferences(): NotificationPreferences = NotificationPreferences(this)