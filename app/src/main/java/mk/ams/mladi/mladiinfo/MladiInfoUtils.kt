package mk.ams.mladi.mladiinfo

import android.content.Context
import android.support.v4.view.ViewPager
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun String.parseMladiInfoDate(): Date = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH).parse(toString())

fun String.parseMladiInfoDescription(crawlUrl: String): String {
  if (crawlUrl.contains("www.mladiinfo.eu")) {
    val pattern = Pattern.compile("Description (.*).\\[read more\\]")
    val matcher = pattern.matcher(this)
    if (matcher.find()) {
      return matcher.group(1)
    }
  }
  return this
}

fun Date.toRelativeTime(context: Context): String = DateUtils.getRelativeDateTimeString(context,
    time, DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0).toString()

fun ViewPager.onPageChangeListener(pageChangeListener: (newPosition: Int) -> Unit) {
  addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
    override fun onPageSelected(position: Int) {
      pageChangeListener(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
      // nothing
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      // nothing
    }


  })
}
