package mk.ams.mladi.mladiinfo

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import mk.ams.mladi.mladiinfo.ViewModels.Category

enum class NAV_ITEMS(@IdRes val id: Int, @StringRes val title: Int, val parentCategory: NAV_ITEMS? = null) {
  STARTING_PAGE(R.id.starting_page, R.string.starting_page),
  TRAININGS(R.id.trainings, R.string.trainings),
  EDUCATIONAL_INSTITUTIONS(R.id.educational_institutions, R.string.educational_institutions),
  WORKS(R.id.work, R.string.work),
  STUDENT_DISCOUNTS(R.id.student_discounts, R.string.student_discounts),
  ORGANIZATIONS(R.id.organizations, R.string.organisations),
  INFO(R.id.info, R.string.ams),
  SCHOLARSHIPS(R.id.scholarships, R.string.scholarships),
  POLLS(R.id.polls, R.string.polls),
  FACEBOOK(R.id.facebook, R.string.facebook),
  YOUTUBE(R.id.youtube, R.string.you_tube),
  DORMITORIES(R.id.dormitory, R.string.dormitories),
  LIBRARIES(R.id.library, R.string.libraries),
  INTERNSHIPS(R.id.internships, R.string.internships, WORKS),
  EMPLOYMENTS(R.id.employments, R.string.employments, WORKS),
  CONFERENCES(R.id.conferences, R.string.conferences, TRAININGS),
  SEMINARS(R.id.seminars, R.string.seminars, TRAININGS);

  fun getCategoryObject(context: Context): Category = when (this) {
    TRAININGS -> Category.Factory.getTrainingCategory(context)
    WORKS -> Category.Factory.getWorkCategory(context)
    ORGANIZATIONS -> Category.Factory.getOrganizations(context)
    EDUCATIONAL_INSTITUTIONS -> Category.Factory.getEducationalInstitutions(context)
    DORMITORIES -> Category.Factory.getDormitories(context)
    LIBRARIES -> Category.Factory.getLibraries(context)
    SCHOLARSHIPS -> Category.Factory.getScholarships(context)
    else -> throw NotImplementedError("only ${NAV_ITEMS.TRAININGS} is supported.")
  }

  companion object {
    fun getItemById(@IdRes id: Int): NAV_ITEMS? = values().find { it.id == id }
  }
}
