package mk.ams.mladi.mladiinfo.ViewModels

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.synthetic.main.notification_item.view.*
import mk.ams.mladi.mladiinfo.R
import mk.ams.mladi.mladiinfo.notifications.getNotificationPreferences

class NotificationModel(val listingId: Int) : EpoxyModel<View>() {
  override fun getDefaultLayout(): Int = R.layout.notification_item

  override fun bind(view: View) {
    super.bind(view)
    view.notificationSwitch.isChecked = view.context.getNotificationPreferences()
        .areNotificationsEnabledForListing(listingId)
    view.tvNotificationItemTitle.setOnClickListener { view.notificationSwitch.toggle() }
    view.notificationSwitch.setOnCheckedChangeListener { compoundButton, b ->
      view.context.getNotificationPreferences().setNotificationsEnabledForListing(listingId, b)
    }
  }

  override fun unbind(view: View) {
    super.unbind(view)
    view.setOnClickListener(null)
    view.notificationSwitch.setOnCheckedChangeListener(null)
  }
}