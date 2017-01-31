package mk.ams.mladi.mladiinfo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import mk.ams.mladi.mladiinfo.ViewModels.SubCategory
import java.util.*

class SubcategoriesPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
  var subcategories: List<SubCategory<*>> = ArrayList()
    set(value) {
      notifyDataSetChanged()
    }

  override fun getItem(position: Int): Fragment = Fragment()

  override fun getCount(): Int = subcategories.size

}