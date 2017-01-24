package mk.ams.mladi.mladiinfo

import android.content.Context
import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel
import mk.ams.mladi.mladiinfo.DataModels.Scholarship
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.ViewModels.ArticleModel
import mk.ams.mladi.mladiinfo.ViewModels.CategoryHeaderModel
import java.util.*

class OverviewAdapter(context: Context) : EpoxyAdapter() {
  val scholarshipsHeader = CategoryHeaderModel(context.getString(R.string.scholarships), true)
  val internshipsHeader = CategoryHeaderModel(context.getString(R.string.internships), true)
  val employmentsHeader = CategoryHeaderModel(context.getString(R.string.employments), true)

  val scholarshipsModels = ArrayList<EpoxyModel<*>>()
  val internshipsModels = ArrayList<EpoxyModel<*>>()
  val employmentModels = ArrayList<EpoxyModel<*>>()

  init {
    enableDiffing()
    addModel(scholarshipsHeader.hide())
    addModel(internshipsHeader.hide())
    addModel(employmentsHeader.hide())
  }

  fun bindScholarships(scholarships: List<Scholarship>) {
    bindCategory(scholarshipsHeader, scholarshipsModels, scholarships.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindInternships(internships: List<Work>) {
    bindCategory(internshipsHeader, internshipsModels, internships.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindEmployments(employments: List<Work>) {
    bindCategory(employmentsHeader, employmentModels, employments.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindCategory(headerModel: CategoryHeaderModel, oldModels: MutableList<EpoxyModel<*>>, newModels: List<EpoxyModel<*>>) {
    synchronized(models) {
      models.removeAll(oldModels)
      oldModels.clear()
      oldModels.addAll(newModels)
      models.addAll(models.indexOf(headerModel) + 1, newModels)
      headerModel.show(newModels.isNotEmpty())
      notifyModelsChanged()
    }
  }
}