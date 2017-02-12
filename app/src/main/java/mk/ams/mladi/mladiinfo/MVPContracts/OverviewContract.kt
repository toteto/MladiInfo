package mk.ams.mladi.mladiinfo.MVPContracts

import android.support.annotation.IntRange
import mk.ams.mladi.mladiinfo.DataModels.Article
import mk.ams.mladi.mladiinfo.DataModels.Scholarship
import mk.ams.mladi.mladiinfo.DataModels.Training
import mk.ams.mladi.mladiinfo.DataModels.Work

interface OverviewContract {
  interface View : MvpLceContract.LCEView {
    fun showScholarships(list: List<Scholarship>)
    fun showInternships(list: List<Work>)
    fun showEmployments(list: List<Work>)
    fun showConferences(list: List<Training>)
    fun showSeminars(list: List<Training>)
    fun  showTrendingArticles(it: List<Article>)
    fun setNumberOfItemsToShow(@IntRange(from = 0) n: Int)

    /** Returns the first N elements of the list. Of the list is smaller than N, the whole list is limited. */
    fun <T> List<T>.getFirstN(n: Int): List<T> = subList(0, Math.min(n, size))
  }

  abstract class Presenter : MvpLceContract.LCEPresenter<View>() {
  }
}