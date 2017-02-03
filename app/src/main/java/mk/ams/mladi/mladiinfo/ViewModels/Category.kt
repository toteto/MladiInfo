package mk.ams.mladi.mladiinfo.ViewModels

import android.content.Context
import mk.ams.mladi.mladiinfo.DataModels.Organization
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.R
import java.util.*

class Category(val name: String) {
  private val subcategories: MutableList<Subcategory<Any>> = ArrayList()

  fun addSubCategory(subcategory: Subcategory<out Any>): Category {
    subcategories.add(subcategory as Subcategory<Any>)
    return this
  }

  fun getSubcategories(): List<Subcategory<Any>> = subcategories

  object Factory {
    fun getTrainingCategory(context: Context) = Category(context.getString(R.string.trainings))
        .addSubCategory(Subcategory(context.getString(R.string.seminars),
            call = { it.getTraining() },
            dataPreprocessor = { it.filter { it.type == Training.TYPE.SEMINAR.value } },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.orange))
        .addSubCategory(Subcategory(context.getString(R.string.conferences),
            call = { it.getTraining() },
            dataPreprocessor = { it.filter { it.type == Training.TYPE.CONFERENCE.value } },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.dark_orange))

    fun getWorkCategory(context: Context) = Category(context.getString(R.string.work))
        .addSubCategory(Subcategory(context.getString(R.string.internships),
            call = { it.getWorkPostings() },
            dataPreprocessor = { it.filter { it.workType == Work.TYPE.INTERNSHIP.value } },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.green))
        .addSubCategory(Subcategory(context.getString(R.string.employments),
            call = { it.getWorkPostings() },
            dataPreprocessor = { it.filter { it.workType == Work.TYPE.EMPLOYMENT.value } },
            bindDataTo = { data, adapter -> adapter.bindArticleItems(data) },
            color = R.color.dark_orange))

    fun getOrganizations(context: Context) = Category(context.getString(R.string.organisations))
        .addSubCategory(Subcategory(context.getString(R.string.student_organizations),
            call = { it.getOrganizations() },
            dataPreprocessor = { it.filter { it.student == Organization.TYPE.STUDENT.value } },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data, R.color.green) }))
        .addSubCategory(Subcategory(context.getString(R.string.non_governmental_organization),
            call = { it.getOrganizations() },
            dataPreprocessor = { it.filter { it.student == Organization.TYPE.NON_GOVERNMENTAL.value } },
            bindDataTo = { data, adapter -> adapter.bindContactItems(data, R.color.dark_orange) }))
  }
}