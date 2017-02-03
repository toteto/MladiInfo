package mk.ams.mladi.mladiinfo.ViewModels

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.View
import android.widget.Toast
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.contact_item.view.*
import mk.ams.mladi.mladiinfo.DataModels.ContactInterface
import mk.ams.mladi.mladiinfo.R


class ContactModel(val contact: ContactInterface) : EpoxyModelWithHolder<ContactModel.ContactViewHolder>(contact.getContactId()) {
  override fun createNewHolder(): ContactViewHolder = ContactViewHolder()

  @ColorRes
  private var dividerColor = R.color.secondary_text

  override fun bind(holder: ContactViewHolder) {
    super.bind(holder)
    holder.bindContact(contact)
    holder.view.itemDivider.setBackgroundColor(ContextCompat.getColor(holder.view.context, dividerColor))
  }

  override fun getDefaultLayout(): Int = R.layout.contact_item

  fun withDividerColor(@ColorRes color: Int): ContactModel {
    dividerColor = color
    return this
  }

  class ContactViewHolder : EpoxyHolder() {
    lateinit var view: View
      private set
    private var contact: ContactInterface? = null

    override fun bindView(itemView: View) {
      view = itemView
      view.setOnClickListener(expandCollapseClickListener)

      view.btnCallPhone.setOnClickListener(buttonsClickListener)
      view.btnSendMail.setOnClickListener(buttonsClickListener)
      view.btnOpenLocation.setOnClickListener(buttonsClickListener)
      view.btnVisitWebsite.setOnClickListener(buttonsClickListener)
      view.btnVisitFacebook.setOnClickListener(buttonsClickListener)
      view.btnVisitTwitter.setOnClickListener(buttonsClickListener)
    }

    private fun bindTitle() {
      view.tvArticleTitle.text = contact?.getContactTitle()
    }

    private fun bindDescription() {
      val desc = contact?.getContactDescription()
      if (!desc.isNullOrEmpty()) {
        if (desc!!.contains("<p>")) {
          view.tvArticleDescription.text = Html.fromHtml(desc)
        } else {
          view.tvArticleDescription.text = desc
        }
        view.tvArticleDescription.visibility = View.VISIBLE
      } else {
        view.tvArticleDescription.visibility = View.GONE
      }
    }

    private fun updateButtons() {
      view.btnCallPhone.hideIfNoContent(contact?.getContactPhone())
      view.btnSendMail.hideIfNoContent(contact?.getContactMail())
      view.btnOpenLocation.visibility = if (contact?.getContactLatLong() != null || contact?.getContactAddress() != null) View.VISIBLE else View.GONE
      view.btnVisitWebsite.hideIfNoContent(contact?.getContactSite())
      view.btnVisitFacebook.hideIfNoContent(contact?.getContactFacebookProfile())
      view.btnVisitTwitter.hideIfNoContent(contact?.getContactTwitterProfile())
    }

    private fun bindLogo() {
      val logoUrl = contact?.getContactLogoUrl()
      if (logoUrl != null) {
        Glide.with(view.context).load(logoUrl).placeholder(R.drawable.icon_organization_placeholder).into(view.ivContactLogo)
        view.ivContactLogo.visibility = View.VISIBLE
      } else {
        view.ivContactLogo.visibility = View.GONE
      }
    }

    fun bindContact(contact: ContactInterface) {
      this.contact = contact
      bindLogo()
      bindTitle()
      bindDescription()
      updateButtons()
    }

    private val expandCollapseClickListener = View.OnClickListener {
      toggleExpandCollapseView()
    }

    private fun toggleExpandCollapseView() {
      val expanded = view.collapsibleContainer.visibility == View.VISIBLE
      if (expanded) {
        collapseView()
      } else {
        expandView()
      }
    }

    private fun expandView() {
      view.collapsibleContainer.visibility = View.VISIBLE
      view.ivExpandIndicator.visibility = View.VISIBLE
    }

    private fun collapseView() {
      view.collapsibleContainer.visibility = View.GONE
      view.ivExpandIndicator.visibility = View.GONE
    }

    private val buttonsClickListener = View.OnClickListener {
      when (it.id) {
        view.btnCallPhone.id -> onCallPhoneClicked()
        view.btnSendMail.id -> onSendMailClicked()
        view.btnVisitWebsite.id -> onVisitWebsiteClicked()
        view.btnOpenLocation.id -> onOpenLocationClicked()
        view.btnVisitFacebook.id -> onVisitFacebookClicked()
        view.btnVisitTwitter.id -> onVisitTwitterClicked()
        else -> {
          Toast.makeText(view.context, R.string.not_yet_supported, Toast.LENGTH_SHORT).show()
        }
      }
    }

    private fun onOpenLocationClicked() {
      val latLong = contact?.getContactLatLong()
      val address = contact?.getContactAddress()
      if (latLong != null || address != null) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (latLong != null) {
          intent.data = Uri.parse("geo:${latLong.first},${latLong.second}?q=${latLong.first},${latLong.second}(${contact?.getContactTitle()})")
        } else {
          intent.data = Uri.parse("geo:0,0?q=$address")
        }

        view.context.startActivity(intent)
      }
      Toast.makeText(view.context, R.string.not_yet_supported, Toast.LENGTH_SHORT).show()
    }

    private fun onVisitTwitterClicked() {
      val url = contact?.getContactTwitterProfile()
      if (url != null) {
        visitWebsite(url)
      }
    }

    private fun onVisitFacebookClicked() {
      val fbLink = contact?.getContactFacebookProfile()
      if (fbLink != null) {
        val fbInstalled = try {
          view.context.packageManager.getPackageInfo("com.facebook.katana", 0) != null
        } catch (e: PackageManager.NameNotFoundException) {
          false
        }
        if (fbInstalled) {
          val facebookIntent = Intent(Intent.ACTION_VIEW)
          facebookIntent.data = Uri.parse("fb://facewebmodal/f?href=$fbLink")
          view.context.startActivity(facebookIntent)
        } else {
          visitWebsite(fbLink)
        }
      }
    }

    private fun onVisitWebsiteClicked() {
      val url = contact?.getContactSite()
      if (url != null) {
        visitWebsite(url)
      }
    }

    private fun visitWebsite(url: String) {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse(url)
      view.context.startActivity(intent)
    }

    private fun onCallPhoneClicked() {
      val phoneNumber = contact?.getContactPhone()
      if (phoneNumber != null) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        view.context.startActivity(intent)
      }
    }

    private fun onSendMailClicked() {
      val mail = contact?.getContactMail()
      if (mail != null) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
        intent.type = "message/rfc822"
        view.context.startActivity(intent)
      }
    }

    fun View.hideIfNoContent(content: CharSequence?, visibilityWhenNoContent: Int = View.GONE) {
      visibility = if (content.isNullOrEmpty()) visibilityWhenNoContent else View.VISIBLE
    }
  }
}