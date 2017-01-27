package mk.ams.mladi.mladiinfo

import android.app.Application
import android.content.Context

class MladiInfoApp : Application() {
  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(LocaleHelper.onAttach(base, "mk"))
  }
}