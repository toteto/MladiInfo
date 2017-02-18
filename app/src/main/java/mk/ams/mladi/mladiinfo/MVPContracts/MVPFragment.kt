package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class MVPFragment<V : MVPContract.View, P : MVPContract.Presenter<V>> : Fragment(), MVPContract.View {
  protected val presenter: P by lazy { createPresenter() }
  protected abstract fun createPresenter(): P

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
    setHasOptionsMenu(true) /*fixme hotfix to remove search action from SubcategoryFragment */
  }

  override final fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(getLayoutId(), container, false)
  }

  abstract fun getLayoutId(): Int

  @Suppress("UNCHECKED_CAST")
  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    presenter.attachView(this as V, savedInstanceState)
  }

  override fun onDetach() {
    super.onDetach()
    presenter.detachView()
  }
}