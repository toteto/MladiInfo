package mk.ams.mladi.mladiinfo.ViewModels

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BindableViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
  abstract fun bindItem(item: T)
}