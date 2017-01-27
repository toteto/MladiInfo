package mk.ams.mladi.mladiinfo

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
