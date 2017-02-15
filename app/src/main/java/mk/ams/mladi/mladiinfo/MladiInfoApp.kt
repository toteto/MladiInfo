package mk.ams.mladi.mladiinfo

import android.app.Application
import android.content.Context
import mk.ams.mladi.mladiinfo.notifications.NotificationJobService

class MladiInfoApp : Application() {
  companion object {
    private val LOG_TAG: String = MladiInfoApp::class.java.simpleName
  }

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(LocaleHelper.onAttach(base, "mk"))
  }

  override fun onCreate() {
    super.onCreate()
    NotificationJobService.managedBasedOnPreferences(this)
  }
}