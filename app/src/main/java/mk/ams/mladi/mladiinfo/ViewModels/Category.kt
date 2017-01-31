package mk.ams.mladi.mladiinfo.ViewModels

import android.content.Context
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.R
import java.util.*

class Category(val name: String) {
  val subCategories = ArrayList<SubCategory<*>>()

  fun addSubCategory(subCategory: SubCategory<*>): Category {
    subCategories.add(subCategory)
    return this
  }

  sealed class Factory {
    companion object {
      fun getTrainingCategory(context: Context) = Category(context.getString(R.string.trainings))
          .addSubCategory(SubCategory(context.getString(R.string.seminars),
              { it.getTraining() }, { it.filter { it.type == Training.TYPE.SEMINAR.value } }, { data, adapter -> adapter.bindArticleItems(data) }))
          .addSubCategory(SubCategory(context.getString(R.string.conferences),
              { it.getTraining() }, { it.filter { it.type == Training.TYPE.CONFERENCE.value } }, { data, adapter -> adapter.bindArticleItems(data) }))
    }
  }
}