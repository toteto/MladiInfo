package mk.ams.mladi.mladiinfo

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyAdapter
import mk.ams.mladi.mladiinfo.DataModels.Scholarship
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataModels.Work
import mk.ams.mladi.mladiinfo.ViewModels.ArticleModel
import mk.ams.mladi.mladiinfo.ViewModels.CategoryHeaderModel
import mk.ams.mladi.mladiinfo.ViewModels.EpoxyModelWithDivider
import mk.ams.mladi.mladiinfo.ViewModels.OverviewSection

class OverviewAdapter(context: Context) : EpoxyAdapter() {
  var onCategoryHeaderClickListener: OnCategoryHeaderClickListener? = null

  val scholarshipsSection = OverviewSection(NAV_ITEMS.SCHOLARSHIPS, R.color.orange)
  val internshipsSection = OverviewSection(NAV_ITEMS.INTERNSHIPS, R.color.green)
  val employmentsSection = OverviewSection(NAV_ITEMS.EMPLOYMENTS, R.color.deep_orange)
  val seminarsSection = OverviewSection(NAV_ITEMS.SEMINARS, R.color.orange)
  val conferencesSection = OverviewSection(NAV_ITEMS.CONFERENCES, R.color.orange)

  val headerViewClickListener = View.OnClickListener {
    val category = it.getTag(CategoryHeaderModel.CATEGORY_TAG_KEY) as NAV_ITEMS?
    val listener = onCategoryHeaderClickListener
    if (category != null && listener != null) {
      listener.onCategoryClicked(category)
    }
  }

  init {
    enableDiffing()
    addModel(scholarshipsSection.header.withOnClickListener(headerViewClickListener).hide())
    addModel(internshipsSection.header.withOnClickListener(headerViewClickListener).hide())
    addModel(employmentsSection.header.withOnClickListener(headerViewClickListener).hide())
    addModel(seminarsSection.header.withOnClickListener(headerViewClickListener).hide())
    addModel(conferencesSection.header.withOnClickListener(headerViewClickListener).hide())
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

  fun bindCategory(section: OverviewSection, newModels: List<EpoxyModelWithDivider<*>>) {
    synchronized(models) {
      models.removeAll(section.models)
      section.models.clear()
      section.models.addAll(newModels)
      models.addAll(models.indexOf(section.header) + 1, newModels)
      newModels.lastOrNull()?.withDivider(false)
      section.header.show(newModels.isNotEmpty())
      notifyModelsChanged()
    }
  }

  interface OnCategoryHeaderClickListener {
    fun onCategoryClicked(navItem: NAV_ITEMS)
  }
}