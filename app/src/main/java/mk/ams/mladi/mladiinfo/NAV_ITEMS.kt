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
  STUDENT_DISCOUNT_EYCA(R.id.eyca, R.string.eyca),
  STUDENT_DISCOUNT_SPUKIM(R.id.spukim, R.string.spukim_edu_card),
  STUDENT_DISCOUNT_EDU_CARD(R.id.edu_card, R.string.edu_card),
  ORGANIZATIONS(R.id.organizations, R.string.organisations),
  INFO(R.id.info, R.string.ams),
  DOCUMENTS(R.id.documents, R.string.documents, INFO),
  USEFUL_LINKS(R.id.useful_links, R.string.useful_links, INFO),
  SCHOLARSHIPS(R.id.scholarships, R.string.scholarships),
  FACEBOOK(R.id.facebook, R.string.facebook),
  YOUTUBE(R.id.youtube, R.string.you_tube),
  DORMITORIES(R.id.dormitory, R.string.dormitories),
  LIBRARIES(R.id.library, R.string.libraries),
  INTERNSHIPS(R.id.internships, R.string.internships, WORKS),
  EMPLOYMENTS(R.id.employments, R.string.employments, WORKS),
  CONFERENCES(R.id.conferences, R.string.conferences, TRAININGS),
  STUDENT_ORGANIZATIONS(R.id.student_organizations, R.string.student_organizations, ORGANIZATIONS),
  NON_GOVERNMENT_ORGANIZATIONS(R.id.non_government_organizations, R.string.non_governmental_organization, ORGANIZATIONS),
  SEMINARS(R.id.seminars, R.string.seminars, TRAININGS),
  STATE_UNIVERSITIES(R.id.state_universities, R.string.state_universities, EDUCATIONAL_INSTITUTIONS),
  PRIVATE_UNIVERSITIES(R.id.private_universities, R.string.private_universities, EDUCATIONAL_INSTITUTIONS),
  OTHER_HIGH_EDU_INSTITUTIONS(R.id.other_higher_education_institutions, R.string.other_higher_education_institutions, EDUCATIONAL_INSTITUTIONS),
  HIGH_SCHOOLS(R.id.high_schools, R.string.high_schools, EDUCATIONAL_INSTITUTIONS),
  CALL_AMS(R.id.call_ams, R.string.call_ams),
  VISIT_AMS_WEBSITE(R.id.visit_ams_website, R.string.visit_ams_website);

  /** Gets a category object with proper check if the item is a subcategory.*/
  fun getCategoryObject(context: Context): Category {
    if (parentCategory != null)
      return parentCategory.getCategoryObject(context, this)
    else
      return getCategoryObject(context, null)
  }

  /** Gets a category object with the provided selected subcategory.*/
  fun getCategoryObject(context: Context, selectedSubcategory: NAV_ITEMS?): Category = when (this) {
    TRAININGS -> Category.Factory.getTrainingCategory(context, selectedSubcategory)
    WORKS -> Category.Factory.getWorkCategory(context, selectedSubcategory)
    ORGANIZATIONS -> Category.Factory.getOrganizations(context, selectedSubcategory)
    EDUCATIONAL_INSTITUTIONS -> Category.Factory.getEducationalInstitutions(context, selectedSubcategory)
    DORMITORIES -> Category.Factory.getDormitories(context)
    LIBRARIES -> Category.Factory.getLibraries(context)
    SCHOLARSHIPS -> Category.Factory.getScholarships(context)
    STUDENT_DISCOUNTS -> Category.Factory.getStudentDiscounts(context)
    INFO -> Category.Factory.getAMSInfo(context, selectedSubcategory)
    else -> throw NotImplementedError("only ${NAV_ITEMS.TRAININGS} is supported.")
  }

  companion object {
    fun getItemById(@IdRes id: Int): NAV_ITEMS? = values().find { it.id == id }
  }
}
