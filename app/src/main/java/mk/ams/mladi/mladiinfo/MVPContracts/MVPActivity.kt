package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/** Should be used as a base for every activity that is going to implement the MVP pattern. */
abstract class MVPActivity<V : MVPContract.View, P : MVPContract.Presenter<V>> : AppCompatActivity(), MVPContract.View {
  protected val presenter: P by lazy { createPresenter() }
  protected abstract fun createPresenter(): P

  @Suppress("UNCHECKED_CAST")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    presenter.attachView(this as V, savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.detachView()
  }
}