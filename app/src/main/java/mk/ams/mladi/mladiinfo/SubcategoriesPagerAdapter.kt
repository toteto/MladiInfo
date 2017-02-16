package mk.ams.mladi.mladiinfo

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import mk.ams.mladi.mladiinfo.MVPViews.SubcategoryFragment
import mk.ams.mladi.mladiinfo.ViewModels.Subcategory
import java.util.*

class SubcategoriesPagerAdapter(val context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
  var subcategories: List<Subcategory<Any>> = ArrayList()
    private set

  override fun getItem(position: Int): Fragment {
    return SubcategoryFragment.getInstance(subcategories[position])
  }

  /** @return true if the subcategories have been changed. */
  fun setSubcategories(newSubcategories: List<Subcategory<Any>>): Boolean {
    val diffSubcategories = subcategories != newSubcategories
    if (diffSubcategories) {
      subcategories = newSubcategories
      notifyDataSetChanged()
    }
    return diffSubcategories
  }

  fun getSubcategory(position: Int) = subcategories[position]

  override fun getCount(): Int = subcategories.size

  override fun getPageTitle(position: Int): CharSequence = context.getString(subcategories[position].navItem.title)
}