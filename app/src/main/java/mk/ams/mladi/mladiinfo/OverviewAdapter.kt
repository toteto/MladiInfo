package mk.ams.mladi.mladiinfo

import android.content.Context
import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel
import mk.ams.mladi.mladiinfo.DataModels.Scholarship
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.ViewModels.ArticleModel
import mk.ams.mladi.mladiinfo.ViewModels.OverviewSection

class OverviewAdapter(context: Context) : EpoxyAdapter() {
  val scholarshipsSection = OverviewSection(context.getString(R.string.scholarships))
  val internshipsSection = OverviewSection(context.getString(R.string.internships))
  val employmentsSection = OverviewSection(context.getString(R.string.employments))
  val seminarsSection = OverviewSection(context.getString(R.string.seminars))
  val conferencesSection = OverviewSection(context.getString(R.string.conferences))

  init {
    enableDiffing()
    addModel(scholarshipsSection.header.hide())
    addModel(internshipsSection.header.hide())
    addModel(employmentsSection.header.hide())
    addModel(seminarsSection.header.hide())
    addModel(conferencesSection.header.hide())
  }

  fun bindScholarships(scholarships: List<Scholarship>) {
    bindCategory(scholarshipsSection, scholarships.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindInternships(internships: List<Work>) {
    bindCategory(internshipsSection, internships.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindEmployments(employments: List<Work>) {
    bindCategory(employmentsSection, employments.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindSeminars(seminars: List<Training>) {
    bindCategory(seminarsSection, seminars.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindConferences(conferences: List<Training>) {
    bindCategory(conferencesSection, conferences.flatMap { listOf(ArticleModel(it)) })
  }

  fun bindCategory(section: OverviewSection, newModels: List<EpoxyModel<*>>) {
    synchronized(models) {
      models.removeAll(section.models)
      section.models.clear()
      section.models.addAll(newModels)
      models.addAll(models.indexOf(section.header) + 1, newModels)
      section.header.show(newModels.isNotEmpty())
      notifyModelsChanged()
    }
  }
}