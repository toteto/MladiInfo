package mk.ams.mladi.mladiinfo.ViewModels

import android.content.Context
import mk.ams.mladi.mladiinfo.DataModels.Training
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
            { it.getTraining() }, { it.filter { it.type == Training.TYPE.SEMINAR.value } }, { data, adapter -> adapter.bindArticleItems(data) }))
        .addSubCategory(Subcategory(context.getString(R.string.conferences),
            { it.getTraining() }, { it.filter { it.type == Training.TYPE.CONFERENCE.value } }, { data, adapter -> adapter.bindArticleItems(data) }))
  }
}