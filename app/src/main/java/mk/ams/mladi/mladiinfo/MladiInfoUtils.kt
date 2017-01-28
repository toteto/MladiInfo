package mk.ams.mladi.mladiinfo

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun String.parseMladiInfoDate(): Date = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH).parse(toString())

fun String.parseMladiInfoDescription(crawlUrl: String): String {
  if (crawlUrl.contains("www.mladiinfo.eu")) {
    val pattern = Pattern.compile("Description (.*).\\[read more\\]")
    val matcher = pattern.matcher(toString())
    if (matcher.find()) {
      return matcher.group(1)
    }
  }
  return toString()
}

fun Date.toRelativeTime(context: Context): String = DateUtils.getRelativeDateTimeString(context,
    time, DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0).toString()
