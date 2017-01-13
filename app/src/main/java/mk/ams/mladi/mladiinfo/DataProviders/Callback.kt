package mk.ams.mladi.mladiinfo.DataProviders

interface Callback<T> {
  /** Called when the requested data has been successfully retrieved. */
  fun onSuccess(listData: T)

  /** Called when the request for the data has failed. */
  fun onFaulure(message: String)
}