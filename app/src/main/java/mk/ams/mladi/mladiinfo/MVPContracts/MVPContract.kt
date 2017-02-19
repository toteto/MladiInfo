package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle

interface MVPContract {
  interface View

  interface Presenter<V : View> {
    /** Get the view that is attached to this presenter. */
    fun getView(): V?

    /** Attach a view to this presenter. [getView] should return the provided view after this.
     * @param savedInstanceState the bundle that was used to save the view/presenter state. */
    fun attachView(view: V, savedInstanceState: Bundle?)

    /** Detach the view from this presenter. [getView] will return null after this.*/
    fun detachView()
  }
}