package mk.ams.mladi.mladiinfo.MVPContracts

interface MVPContract {
  interface View {
  }

  interface Presenter<V : View> {

    fun getView(): V?
    fun attachView(view: V)
    fun detachView()
  }
}