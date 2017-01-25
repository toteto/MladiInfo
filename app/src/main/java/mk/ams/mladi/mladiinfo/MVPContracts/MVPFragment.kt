package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle
import android.support.v4.app.Fragment

abstract class MVPFragment<V : MVPContract.View, P : MVPContract.Presenter<V>> : Fragment(), MVPContract.View {
  protected val presenter: P by lazy { createPresenter() }
  protected abstract fun createPresenter(): P

  @Suppress("UNCHECKED_CAST")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    presenter.attachView(this as V)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.detachView()
  }
}