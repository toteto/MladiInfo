package mk.ams.mladi.mladiinfo.MVPPresenters

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.View
import java.lang.ref.WeakReference

/** Connection broadcast receiver that will handle the visibility of the provided view.
 * @param view the view that will signal "no internet"*/
class InternetConnectionViewHandler(view: View) : BroadcastReceiver() {
  val view = WeakReference(view)
  override fun onReceive(context: Context?, intent: Intent?) {
    val noConnectivity = intent?.extras?.getBoolean(
        ConnectivityManager.EXTRA_NO_CONNECTIVITY) ?: false
    view.get()?.visibility = if (noConnectivity) View.VISIBLE else View.GONE
  }
}