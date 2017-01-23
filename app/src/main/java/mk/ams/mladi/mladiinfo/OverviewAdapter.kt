package mk.ams.mladi.mladiinfo

import android.content.Context
import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.ViewModels.ArticleModel
import mk.ams.mladi.mladiinfo.ViewModels.CategoryHeaderModel
import java.util.*

class OverviewAdapter(context: Context) : EpoxyAdapter() {
  val trainingHeader = CategoryHeaderModel(context.getString(R.string.app_name), true)
  val internshipHeader = CategoryHeaderModel(context.getString(R.string.app_name), true)
  val trainingModels = ArrayList<EpoxyModel<*>>()

  init {
    enableDiffing()
    addModel(trainingHeader.hide())
    addModel(internshipHeader.hide())
  }

  fun bindTrainings(trainings: List<Training>) {
    bindCategory(trainingHeader, trainingModels, trainings.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindCategory(headerModel: CategoryHeaderModel, oldModels: MutableList<EpoxyModel<*>>, newModels: List<EpoxyModel<*>>) {
    synchronized(models) {
      models.removeAll(oldModels)
      oldModels.clear()
      oldModels.addAll(newModels)
      models.addAll(models.indexOf(headerModel) + 1, oldModels)
      headerModel.show(oldModels.isNotEmpty())
      notifyModelsChanged()
    }
  }
}