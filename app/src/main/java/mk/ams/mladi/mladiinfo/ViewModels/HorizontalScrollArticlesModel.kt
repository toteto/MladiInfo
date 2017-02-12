package mk.ams.mladi.mladiinfo.ViewModels

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.synthetic.main.horizontal_scroll_recycler_view_item.view.*
import mk.ams.mladi.mladiinfo.DataModels.Article
import mk.ams.mladi.mladiinfo.R

class HorizontalScrollArticlesModel : EpoxyModel<View>() {
  private val adapter = ArticlesWithImageAdapter()
  private val snapHelper = MyLinearSnapHelper()
  private var layoutManager: LinearLayoutManager? = null
  private var currScrollPosition = 0

  override fun getDefaultLayout(): Int = R.layout.horizontal_scroll_recycler_view_item

  override fun bind(view: View) {
    super.bind(view)
    layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
    view.recyclerView.setItemViewCacheSize(5)
    view.recyclerView.layoutManager = layoutManager
    view.recyclerView.adapter = adapter
    view.recyclerView.scrollToPosition(Math.min(currScrollPosition, adapter.itemCount))
    snapHelper.attachToRecyclerView(view.recyclerView)

    view.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          updateNavigationSate(view, layoutManager)
        } else {
          view.goLeftArrow.visibility = View.GONE
          view.goRightArrow.visibility = View.GONE
        }
      }
    })

    view.goLeftArrow.setOnClickListener { navigateToPreviousItem(view.recyclerView, layoutManager) }
    view.goRightArrow.setOnClickListener { navigateToNextItem(view.recyclerView, layoutManager) }
    updateNavigationSate(view, layoutManager, true)
  }

  fun bindArticles(items: List<Article>) {
    adapter.bindArticles(items)
  }

  override fun unbind(view: View?) {
    super.unbind(view)
    currScrollPosition = layoutManager?.findFirstCompletelyVisibleItemPosition() ?: 0
    view?.recyclerView?.clearOnScrollListeners()
  }

  fun updateNavigationSate(view: View?, layoutManager: LinearLayoutManager?, initial: Boolean = false) {
    val currPosition = if (initial) currScrollPosition else layoutManager?.findFirstCompletelyVisibleItemPosition()
    if (currPosition != null) {
      view?.goLeftArrow?.visibility = if (currPosition > 0)
        View.VISIBLE else View.GONE
      view?.goRightArrow?.visibility = if (currPosition < adapter.itemCount - 1)
        View.VISIBLE else View.GONE
    }
  }

  fun navigateToPreviousItem(recyclerView: RecyclerView?, layoutManager: LinearLayoutManager?) {
    val currPosition = layoutManager?.findFirstCompletelyVisibleItemPosition()
    if (currPosition != null && currPosition > 0) {
      recyclerView?.smoothScrollToPosition(currPosition - 1)
    }
  }

  fun navigateToNextItem(recyclerView: RecyclerView?, layoutManager: LinearLayoutManager?) {
    val currPosition = layoutManager?.findFirstCompletelyVisibleItemPosition()
    val itemCount = adapter.itemCount
    if (currPosition != null && currPosition < itemCount - 1) {
      recyclerView?.smoothScrollToPosition(currPosition + 1)
    }
  }

  class ArticlesWithImageAdapter : EpoxyAdapter() {
    init {
      enableDiffing()
    }

    fun bindArticles(items: List<Article>) {
      models.clear()
      models.addAll(items.map(::ArticleWithImageModel))
      notifyModelsChanged()
    }
  }

  /** A simple linear snap helper that snap simply to the next/previous item based on the fling
   * direction. */
  class MyLinearSnapHelper : LinearSnapHelper() {
    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
      val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

      val position = layoutManager.getPosition(centerView)
      var targetPosition = -1
      if (layoutManager.canScrollHorizontally()) {
        if (velocityX < 0) {
          targetPosition = position - 1
        } else {
          targetPosition = position + 1
        }
      }

      if (layoutManager.canScrollVertically()) {
        if (velocityY < 0) {
          targetPosition = position - 1
        } else {
          targetPosition = position + 1
        }
      }

      val firstItem = 0
      val lastItem = layoutManager.itemCount - 1
      targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem))
      return targetPosition
    }
  }
}
