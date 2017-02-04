package mk.ams.mladi.mladiinfo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import mk.ams.mladi.mladiinfo.MVPViews.SubcategoryFragment
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory
import java.util.*

class SubcategoriesPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
  var subcategories: List<Subcategory<Any>> = ArrayList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItem(position: Int): Fragment {
    return SubcategoryFragment.getInstance(subcategories[position])
  }

  fun getSubcategory(position: Int) = subcategories[position]

  override fun getCount(): Int = subcategories.size

  override fun getPageTitle(position: Int): CharSequence = subcategories[position].name
}