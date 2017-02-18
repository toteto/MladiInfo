package mk.ams.mladi.mladiinfo.ViewModels

/** Simple interface for models that will implement some sort of querying their data. */
interface QueryableModelInterface {
  abstract fun queryModel(query: String): Boolean
}