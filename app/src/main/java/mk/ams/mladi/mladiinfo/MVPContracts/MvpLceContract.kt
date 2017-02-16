package mk.ams.mladi.mladiinfo.MVPContracts

import android.os.Bundle
import android.support.annotation.StringRes
import mk.ams.mladi.mladiinfo.DataProviders.enqueueTrueSuccess
import mk.ams.mladi.mladiinfo.R
import retrofit2.Call
import java.util.*

interface MvpLceContract {
  interface LCEView : MVPContract.View {
    fun showLoading(show: Boolean)
    fun showError(@StringRes strID: Int)
  }

  abstract class LCEPresenter<V : LCEView> : MVPPresenter<V>() {
    val enqueuedCalls = ArrayList<Call<*>>()

    override fun attachView(view: V, savedInstanceState: Bundle?) {
      super.attachView(view, savedInstanceState)
      loadData(false)
    }

    override fun detachView() {
      super.detachView()
      enqueuedCalls.forEach { it.cancel() }
      enqueuedCalls.clear()
    }

    abstract fun loadData(forceUpdate: Boolean)

    fun <T> enqueueCall(call: Call<T>, handleSuccess: (result: T) -> Unit, handleFailure: (call: Call<T>) -> Unit = {}) {
      call.enqueueTrueSuccess({ result, call ->
        handleSuccess(result)
        onCallFinished(call)
      }, { call ->
        getView()?.showError(R.string.server_fail_offline_fail)
        handleFailure(call)
        onCallFinished(call)
      }).let {
        enqueuedCalls.add(it)
        updateRefreshState()
      }
    }

    private fun updateRefreshState() {
      getView()?.showLoading(enqueuedCalls.isNotEmpty())
    }

    private fun onCallFinished(call: Call<*>) {
      enqueuedCalls.remove(call)
      updateRefreshState()
    }
  }
}