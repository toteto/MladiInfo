package mk.ams.mladi.mladiinfo.ViewModels

import java.util.*

class Category(val name: String) {
  val subCategories = ArrayList<SubCategory>()

  fun addSubCategory(subCategory: SubCategory): Category {
    subCategories.add(subCategory)
    return this
  }
}