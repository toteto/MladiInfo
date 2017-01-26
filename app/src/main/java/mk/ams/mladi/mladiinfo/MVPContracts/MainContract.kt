package mk.ams.mladi.mladiinfo.MVPContracts

import mk.ams.mladi.mladiinfo.ViewModels.Category

interface MainContract {
  enum class CATEGORY_MENU_ITEM {
    STARTING_PAGE,
    TRAININGS,
    EDUCATIONAL_INSTITUTIONS,
    WORKS,
    STUDENT_DISCOUNTS,
    ORGANIZATIONS,
    INFO,
    SCHOLARSHIPS,
    POLLS,
    FACEBOOK,
    YOUTUBE
  }

  interface View : MVPContract.View {
    fun showOverview()
    fun showCategory(addSubCategory: Category)
  }

  abstract class Presenter : MVPPresenter<View>() {
    abstract fun onCategoryItemSelected(item: CATEGORY_MENU_ITEM)
  }
}