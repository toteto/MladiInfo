package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle
import java.lang.ref.WeakReference

/** Base abstract class that every MPV presenter should inherit. It takes care of MVP view
 * attaching/detaching and getting reference to that view itself. */
abstract class MVPPresenter<V : MVPContract.View> : MVPContract.Presenter<V> {
  /** WeakReference to the MVP view in order to avoid memory leaks. */
  private var viewRef: WeakReference<V>? = null

  override fun getView(): V? = if (viewRef == null) null else viewRef?.get()

  override fun attachView(view: V, savedInstanceState: Bundle?) {
    viewRef = WeakReference(view)
  }

  override fun detachView() {
    viewRef?.clear()
    viewRef = null
  }
}