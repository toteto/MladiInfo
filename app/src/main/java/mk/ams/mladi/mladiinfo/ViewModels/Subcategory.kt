package mk.ams.mladi.mladiinfo.ViewModels

import android.support.annotation.ColorRes
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.SubcategoryAdapterInterface
import mk.ams.mladi.mladiinfo.notifications.NotificationPreferences
import java.util.*

/** Simple subcategory item that has knowledge how to [filter][dataPreprocessor] it's data and how
 * to [bind][bindDataTo] that data to a [SubcategoryAdapterInterface]. To observe of the data changes
 * in this subcategory, use [addDataObserver].
 * @param navItem the item from NAV_ITEMS that this subcategory represents.
 * @param dataPreprocessor this should filter the received raw data to data that this subcategory
 * represents. (ex. Subcategory 'Seminars' can get List<Trainig>, but only needs to keep Trainings
 * of type 'Seminars' and not 'Conferences'
 * @param bindDataTo method that will bind the data from this item to an provided adapter to it.
 * @param color the color theme of this subcategory.
 * @param queryable will this subcategory support searching of the data it provides */
open class Subcategory<T>(
    val navItem: NAV_ITEMS,
    val queryable: Boolean = true,
    val dataPreprocessor: (data: List<T>) -> List<T> = { it },
    val bindDataTo: (data: List<T>, adapter: SubcategoryAdapterInterface) -> Unit,
    @ColorRes val color: Int = R.color.secondary_text) {

  /** This function will be called when some view requests data update for this subcategory.
   * Example: [SubcategoryFragment][mk.ams.mladi.mladiinfo.MVPViews.SubcategoryFragment] wants to
   * refresh the data, it will call this method.*/
  var requestUpdateDataHandler: (() -> Unit)? = null
    private set
  /** Observers that will be notified when data changes on [data] are made. */
  private val observers: MutableList<(List<T>) -> Unit> = ArrayList()
  var data: List<T> = emptyList()
    set

  /** Filters the provided new data and saves it for anybody that wants to use that data. */
  fun setNewData(newData: List<T>) {
    data = dataPreprocessor.invoke(newData)
    notifyObservers()
  }

  private fun notifyObservers() = observers.forEach {
    it.invoke(data)
  }

  /** Register an observer on the data of this subcategory. */
  fun addDataObserver(observer: (newValues: List<T>) -> Unit) {
    observers.add(observer)
  }

  /** Set the handler that will be called when some view wants to refresh the data. */
  fun setRequestDataUpdateHandler(handler: () -> Unit) {
    requestUpdateDataHandler = handler
  }

  fun supportsNotifications() = NotificationPreferences.areNotificationsForListingSupported(
      navItem.id)
}