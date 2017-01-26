package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.DataProviders.enqueueTrueSuccess
import retrofit2.Call
import java.util.*

interface MvpLceContract {
  interface LCEView : MVPContract.View {
    fun showLoading(show: Boolean)
    fun showError(message: String)
  }

  abstract class LCEPresenter<V : LCEView> : MVPPresenter<V>() {
    val enqueuedCalls = ArrayList<Call<*>>()

    override fun attachView(view: V) {
      super.attachView(view)
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