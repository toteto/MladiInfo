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
  var selectedSubcategory: NAV_ITEMS? = null
    private set
  val subcategoryBundles: MutableList<SubcategoryBundle<Any>> = ArrayList()

  fun addSubcategoryBundle(subcategoryBundle: SubcategoryBundle<*>): Category {
    @Suppress("UNCHECKED_CAST")
    subcategoryBundles.add(subcategoryBundle as SubcategoryBundle<Any>)
    return this
  }

  fun withSelectedSubcategory(subcategory: NAV_ITEMS): Category {
    selectedSubcategory = subcategory
    return this
  }

  object Factory {
    fun getTrainingCategory(context: Context, selectedSubcategory: NAV_ITEMS?) = Category(context.getString(R.string.trainings))
        .addSubcategoryBundle(SubcategoryBundle(
            call = { it.getTraining() },
            subcategories = listOf(
                Subcategory(NAV_ITEMS.SEMINARS,
                    dataPreprocessor = { it.filter { it.type == Training.TYPE.SEMINAR.value } },
                    bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
                    color = R.color.orange),
                Subcategory(NAV_ITEMS.CONFERENCES,
                    dataPreprocessor = { it.filter { it.type == Training.TYPE.CONFERENCE.value } },
                    bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
                    color = R.color.dark_orange))))
        .withSelectedSubcategory(selectedSubcategory ?: NAV_ITEMS.SEMINARS)

    fun getWorkCategory(context: Context, selectedSubcategory: NAV_ITEMS?) = Category(context.getString(R.string.work))
        .addSubcategoryBundle(SubcategoryBundle(
            call = { it.getWorkPostings() },
            subcategories = listOf(
                Subcategory(NAV_ITEMS.INTERNSHIPS,
                    dataPreprocessor = { it.filter { it.workType == Work.TYPE.INTERNSHIP.value } },
                    bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
                    color = R.color.green),
                Subcategory(NAV_ITEMS.EMPLOYMENTS,
                    dataPreprocessor = { it.filter { it.workType == Work.TYPE.EMPLOYMENT.value } },
                    bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
                    color = R.color.deep_orange)
            )))
        .withSelectedSubcategory(selectedSubcategory ?: NAV_ITEMS.INTERNSHIPS)

    fun getOrganizations(context: Context, selectedSubcategory: NAV_ITEMS?) = Category(context.getString(R.string.organisations))
        .addSubcategoryBundle(SubcategoryBundle(
            call = { it.getOrganizations() }, subcategories = listOf(
            Subcategory(NAV_ITEMS.STUDENT_ORGANIZATIONS,
                dataPreprocessor = { it.filter { it.student == Organization.TYPE.STUDENT.value } },
                bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
                color = Organization.TYPE.STUDENT.color),
            Subcategory(NAV_ITEMS.NON_GOVERNMENT_ORGANIZATIONS,
                dataPreprocessor = { it.filter { it.student == Organization.TYPE.NON_GOVERNMENTAL.value } },
                bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
                color = Organization.TYPE.NON_GOVERNMENTAL.color))))
        .withSelectedSubcategory(selectedSubcategory ?: NAV_ITEMS.STUDENT_ORGANIZATIONS)

    fun getEducationalInstitutions(context: Context, selectedSubcategory: NAV_ITEMS?) = Category(context.getString(R.string.educational_institutions))
        .addSubcategoryBundle(SubcategoryBundle(
            call = { it.getUniversities() }, subcategories = listOf(
            Subcategory(NAV_ITEMS.STATE_UNIVERSITIES,
                dataPreprocessor = { it.filter { it.typeId == EducationalInstitution.TYPE.STATE_UNIVERSITY.key } },
                bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
                color = EducationalInstitution.TYPE.STATE_UNIVERSITY.color),
            Subcategory(NAV_ITEMS.PRIVATE_UNIVERSITIES,
                dataPreprocessor = { it.filter { it.typeId == EducationalInstitution.TYPE.PRIVATE_UNIVERSITY.key } },
                bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
                color = EducationalInstitution.TYPE.PRIVATE_UNIVERSITY.color),
            Subcategory(NAV_ITEMS.OTHER_HIGH_EDU_INSTITUTIONS,
                dataPreprocessor =
                { it.filter { it.typeId == EducationalInstitution.TYPE.OTHER_HIGHER_EDUCATION.key } },
                bindDataTo =
                { data, adapter -> adapter.bindContactItems(data) },
                color = EducationalInstitution.TYPE.OTHER_HIGHER_EDUCATION.color),
            Subcategory(NAV_ITEMS.HIGH_SCHOOLS,
                dataPreprocessor =
                { it.filter { it.typeId == EducationalInstitution.TYPE.HIGH_SCHOOL.key } },
                bindDataTo =
                { data, adapter -> adapter.bindContactItems(data) },
                color = EducationalInstitution.TYPE.HIGH_SCHOOL.color))))
        .withSelectedSubcategory(selectedSubcategory ?: NAV_ITEMS.STATE_UNIVERSITIES)

    fun getDormitories(context: Context) = Category(context.getString(R.string.dormitories))
        .addSubcategoryBundle(SubcategoryBundle(call = { it.getDorms() }, subcategories = listOf(
            Subcategory(NAV_ITEMS.DORMITORIES,
                bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
                color = R.color.dark_yellow))))

    fun getLibraries(context: Context) = Category(context.getString(R.string.libraries))
        .addSubcategoryBundle(SubcategoryBundle(call = { it.getLibraries() }, subcategories = listOf(
            Subcategory(NAV_ITEMS.LIBRARIES,
                bindDataTo = { data, adapter -> adapter.bindContactItems(data) },
                color = R.color.light_green))))

    fun getScholarships(context: Context) = Category(context.getString(R.string.scholarships))
        .addSubcategoryBundle(SubcategoryBundle(call = { it.getScholarships() }, subcategories = listOf(
            Subcategory(NAV_ITEMS.SCHOLARSHIPS,
                bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
                color = R.color.orange)
        )))

    fun getStudentDiscounts(context: Context) = Category(context.getString(R.string.student_discounts))
        .addSubcategoryBundle(SubcategoryBundle(call = { it.getEduCards() }, subcategories = listOf(
            Subcategory(NAV_ITEMS.STUDENT_DISCOUNT_EDU_CARD,
                dataPreprocessor = { it.filter { it.name == "ЕДУ КАРТИЧКА" } },
                bindDataTo = { card, adapter -> adapter.bindDiscountCards(card) }),
            Subcategory(NAV_ITEMS.STUDENT_DISCOUNT_SPUKIM,
                dataPreprocessor = { it.filter { it.name == "КАРТИЧКА НА СПУКИМ" } },
                bindDataTo = { card, adapter -> adapter.bindDiscountCards(card) }),
            Subcategory(NAV_ITEMS.STUDENT_DISCOUNT_EYCA,
                dataPreprocessor = { it.filter { it.name == "EYCA" } },
                bindDataTo = { card, adapter -> adapter.bindDiscountCards(card) })
        )))
  }
}