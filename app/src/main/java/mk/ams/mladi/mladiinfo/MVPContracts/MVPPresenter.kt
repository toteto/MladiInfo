package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle
import java.lang.ref.WeakReference

abstract class MVPPresenter<V : MVPContract.View> : MVPContract.Presenter<V> {
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