package mk.ams.mladi.mladiinfo.MVPContracts

interface MainContract : MVPContract {
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
    fun showCategory()
  }

  interface Presenter : MVPContract.Presenter<MainContract.View> {
    fun onCategoryItemSelected(item: CATEGORY_MENU_ITEM)
  }
}