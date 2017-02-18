package mk.ams.mladi.mladiinfo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.text.format.DateUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
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

fun Pair<String?, String?>.parseMladiInfoLatLong(): Pair<Double, Double>? {
  if (!first.isNullOrEmpty() && !second.isNullOrEmpty()) {
    return Pair(first?.replace(',', '.')?.toDouble() as Double, second?.replace(',', '.')?.toDouble() as Double)
  }
  return null
}

fun String.trimFirstImgTag(): String {
  if (this.contains("<img")) {
    val startIndex = this.indexOf("/>") + 2
    return this.substring(startIndex)
  } else {
    return this
  }
}

fun Context.openWebsite(url: String): Unit {
  val intent = Intent(Intent.ACTION_VIEW)
  intent.data = Uri.parse(url)
  startActivity(intent)
}

fun Context.openMailClient(vararg emails: String) {
  val intent = Intent(Intent.ACTION_SEND)
  intent.data = Uri.parse("mailto:")
  intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emails))
  intent.type = "message/rfc822"
  startActivity(intent)
}

fun Context.tryOpenFacebook(url: String): Unit {
  val fbInstalled = try {
    packageManager.getPackageInfo("com.facebook.katana", 0) != null
  } catch (e: PackageManager.NameNotFoundException) {
    false
  }
  if (fbInstalled) {
    val facebookIntent = Intent(Intent.ACTION_VIEW)
    facebookIntent.data = Uri.parse("fb://facewebmodal/f?href=$url")
    startActivity(facebookIntent)
  } else {
    openWebsite(url)
  }
}

fun Context.dialPhone(phone: String) {
  val intent = Intent(Intent.ACTION_DIAL)
  intent.data = Uri.parse("tel:$phone")
  startActivity(intent)
}

fun TextView.setTextWithVisibility(content: CharSequence?) {
  if (content.isNullOrEmpty()) {
    visibility = View.GONE
  } else {
    text = content
    visibility = View.VISIBLE
  }
}

fun Context.buildLanguagePreferenceDialog(onLanguageChanged: (newLanguage: String) -> Unit): Dialog {
  val builder = AlertDialog.Builder(this)
  val supportedLanguages = resources.getStringArray(R.array.available_languages)
  val currentLanguage = LocaleHelper.getLanguage(this)
  val supportedLocales = supportedLanguages.map(::Locale)

  val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,
      supportedLocales.map { it.getDisplayLanguage(it).capitalize() })

  var selectedIndex = supportedLanguages.indexOf(currentLanguage)

  builder.setTitle(R.string.choose_language)
      .setSingleChoiceItems(adapter,
          selectedIndex, { dialog, i -> selectedIndex = i })
      .setNegativeButton(getString(R.string.cancel), { dialog, i -> dialog.dismiss() })
      .setPositiveButton(getString(R.string.confirm), { dialog, i ->
        val selectedLanguage = supportedLanguages[selectedIndex]
        if (selectedLanguage != currentLanguage) {
          LocaleHelper.setLocale(this, selectedLanguage)
          onLanguageChanged.invoke(selectedLanguage)
        }
        dialog.dismiss()
      })
  return builder.create()
}

