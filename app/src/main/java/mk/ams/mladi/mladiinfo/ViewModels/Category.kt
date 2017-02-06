package mk.ams.mladi.mladiinfo.ViewModels

import android.content.Context
import mk.ams.mladi.mladiinfo.DataModels.EducationalInstitution
import mk.ams.mladi.mladiinfo.DataModels.Organization
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.NAV_ITEMS
import mk.ams.mladi.mladiinfo.R
import java.util.*

class Category(val name: String) {
  var selectedSubcategory: Subcategory<Any>? = null
    private set
  private val subcategories: MutableList<Subcategory<Any>> = ArrayList()

  fun addSubCategory(subcategory: Subcategory<out Any>): Category {
    subcategories.add(subcategory as Subcategory<Any>)
    return this
  }

  fun withSelectedSubcategory(subcategory: NAV_ITEMS): Category {
    selectedSubcategory = subcategories.first { it.navItem.id == subcategory.id }
    return this
  }

  fun getSubcategories(): List<Subcategory<Any>> = subcategories

  object Factory {
    fun getTrainingCategory(context: Context, selectedSubcategory: NAV_ITEMS?) = Category(context.getString(R.string.trainings))
        .addSubCategory(Subcategory(NAV_ITEMS.SEMINARS,
            call = { it.getTraining() },
            dataPreprocessor = { it.filter { it.type == Training.TYPE.SEMINAR.value } },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.orange))
        .addSubCategory(Subcategory(NAV_ITEMS.CONFERENCES,
            call = { it.getTraining() },
            dataPreprocessor = { it.filter { it.type == Training.TYPE.CONFERENCE.value } },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.dark_orange))
        .withSelectedSubcategory(selectedSubcategory ?: NAV_ITEMS.SEMINARS)

    fun getWorkCategory(context: Context, selectedSubcategory: NAV_ITEMS?) = Category(context.getString(R.string.work))
        .addSubCategory(Subcategory(NAV_ITEMS.INTERNSHIPS,
            call = { it.getWorkPostings() },
            dataPreprocessor = { it.filter { it.workType == Work.TYPE.INTERNSHIP.value } },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.green))
        .addSubCategory(Subcategory(NAV_ITEMS.EMPLOYMENTS,
            call = { it.getWorkPostings() },
            dataPreprocessor = { it.filter { it.workType == Work.TYPE.EMPLOYMENT.value } },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.deep_orange))
        .withSelectedSubcategory(selectedSubcategory ?: NAV_ITEMS.INTERNSHIPS)

    fun getOrganizations(context: Context, selectedSubcategory: NAV_ITEMS?) = Category(context.getString(R.string.organisations))
        .addSubCategory(Subcategory(NAV_ITEMS.STUDENT_ORGANIZATIONS,
            call = { it.getOrganizations() },
            dataPreprocessor = { it.filter { it.student == Organization.TYPE.STUDENT.value } },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
            color = Organization.TYPE.STUDENT.color))
        .addSubCategory(Subcategory(NAV_ITEMS.NON_GOVERNMENT_ORGANIZATIONS,
            call = { it.getOrganizations() },
            dataPreprocessor = { it.filter { it.student == Organization.TYPE.NON_GOVERNMENTAL.value } },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
            color = Organization.TYPE.NON_GOVERNMENTAL.color))
        .withSelectedSubcategory(selectedSubcategory ?: NAV_ITEMS.STUDENT_ORGANIZATIONS)

    fun getEducationalInstitutions(context: Context, selectedSubcategory: NAV_ITEMS?) = Category(context.getString(R.string.educational_institutions))
        .addSubCategory(Subcategory(NAV_ITEMS.STATE_UNIVERSITIES,
            call = { it.getUniversities() },
            dataPreprocessor = { it.filter { it.typeId == EducationalInstitution.TYPE.STATE_UNIVERSITY.key } },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
            color = EducationalInstitution.TYPE.STATE_UNIVERSITY.color))
        .addSubCategory(Subcategory(NAV_ITEMS.PRIVATE_UNIVERSITIES,
            call = { it.getUniversities() },
            dataPreprocessor = { it.filter { it.typeId == EducationalInstitution.TYPE.PRIVATE_UNIVERSITY.key } },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
            color = EducationalInstitution.TYPE.PRIVATE_UNIVERSITY.color))
        .addSubCategory(Subcategory(NAV_ITEMS.OTHER_HIGH_EDU_INSTITUTIONS,
            call = { it.getUniversities() },
            dataPreprocessor = { it.filter { it.typeId == EducationalInstitution.TYPE.OTHER_HIGHER_EDUCATION.key } },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
            color = EducationalInstitution.TYPE.OTHER_HIGHER_EDUCATION.color))
        .addSubCategory(Subcategory(NAV_ITEMS.HIGH_SCHOOLS,
            call = { it.getUniversities() },
            dataPreprocessor = { it.filter { it.typeId == EducationalInstitution.TYPE.HIGH_SCHOOL.key } },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
            color = EducationalInstitution.TYPE.HIGH_SCHOOL.color))
        .withSelectedSubcategory(selectedSubcategory ?: NAV_ITEMS.STATE_UNIVERSITIES)

    fun getDormitories(context: Context) = Category(context.getString(R.string.dormitories))
        .addSubCategory(Subcategory(NAV_ITEMS.DORMITORIES,
            call = { it.getDorms() },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
            color = R.color.dark_yellow))

    fun getLibraries(context: Context) = Category(context.getString(R.string.libraries))
        .addSubCategory(Subcategory(NAV_ITEMS.LIBRARIES,
            call = { it.getLibraries() },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
            color = R.color.light_green))

    fun getScholarships(context: Context) = Category(context.getString(R.string.scholarships))
        .addSubCategory(Subcategory(NAV_ITEMS.SCHOLARSHIPS,
            call = { it.getScholarships() },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.orange))
  }
}