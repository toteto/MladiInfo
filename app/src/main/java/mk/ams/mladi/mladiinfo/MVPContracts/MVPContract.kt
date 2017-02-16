package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle

interface MVPContract {
  interface View {
  }

  interface Presenter<V : View> {

    fun getView(): V?
    fun attachView(view: V, savedInstanceState: Bundle?)
    fun detachView()
  }
}