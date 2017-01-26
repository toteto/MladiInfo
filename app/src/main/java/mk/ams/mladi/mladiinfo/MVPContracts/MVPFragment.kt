package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class MVPFragment<V : MVPContract.View, P : MVPContract.Presenter<V>> : Fragment(), MVPContract.View {
  protected val presenter: P by lazy { createPresenter() }
  protected abstract fun createPresenter(): P

  @Suppress("UNCHECKED_CAST")
  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    presenter.attachView(this as V)
  }

  override fun onDetach() {
    super.onDetach()
    presenter.detachView()
  }
}