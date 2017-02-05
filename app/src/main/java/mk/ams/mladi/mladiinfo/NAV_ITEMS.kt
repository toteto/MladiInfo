package mk.ams.mladi.mladiinfo

import android.content.Context
import android.support.annotation.IdRes
import mk.ams.mladi.mladiinfo.ViewModels.Category

enum class NAV_ITEMS(@IdRes val id: Int) {
  STARTING_PAGE(R.id.starting_page),
  TRAININGS(R.id.trainings),
  EDUCATIONAL_INSTITUTIONS(R.id.educational_institutions),
  WORKS(R.id.work),
  STUDENT_DISCOUNTS(R.id.student_discounts),
  ORGANIZATIONS(R.id.organizations),
  INFO(R.id.info),
  SCHOLARSHIPS(R.id.scholarships),
  POLLS(R.id.polls),
  FACEBOOK(R.id.facebook),
  YOUTUBE(R.id.youtube);

  fun getCategoryObject(context: Context): Category = when (this) {
    NAV_ITEMS.TRAININGS -> Category.Factory.getTrainingCategory(context)
    NAV_ITEMS.WORKS -> Category.Factory.getWorkCategory(context)
    NAV_ITEMS.ORGANIZATIONS -> Category.Factory.getOrganizations(context)
    NAV_ITEMS.EDUCATIONAL_INSTITUTIONS -> Category.Factory.getEducationalInstitutions(context)
    else -> throw NotImplementedError("only ${NAV_ITEMS.TRAININGS} is supported.")
  }

  companion object {
    fun getItemById(@IdRes id: Int): NAV_ITEMS? = values().find { it.id == id }
  }
}
