package mk.ams.mladi.mladiinfo.ViewModels

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.View
import android.widget.Toast
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.contact_item.view.*
import mk.ams.mladi.mladiinfo.*
import mk.ams.mladi.mladiinfo.DataModels.ContactInterface


class ContactModel(val contact: ContactInterface) :
    EpoxyModelWithHolder<ContactModel.ContactViewHolder>(contact.getContactId()), QueryableModelInterface {
  override fun createNewHolder(): ContactViewHolder = ContactViewHolder()

  override fun bind(holder: ContactViewHolder) {
    super.bind(holder)
    holder.bindContact(contact)
  }

  override fun unbind(holder: ContactViewHolder) {
    super.unbind(holder)
    holder.unbindContact()
  }

  override fun getDefaultLayout(): Int = R.layout.contact_item

  override fun queryModel(query: String): Boolean = contact.queryContact(query)

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
      view.itemDivider.setBackgroundColor(ContextCompat.getColor(view.context, contact.getDividerColor()))
    }

    fun unbindContact() {
      this.contact = null
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
        view.context.openWebsite(url)
      }
    }

    private fun onVisitFacebookClicked() {
      val fbLink = contact?.getContactFacebookProfile()
      if (fbLink != null) {
        view.context.tryOpenFacebook(fbLink)
      }
    }

    private fun onVisitWebsiteClicked() {
      val url = contact?.getContactSite()
      if (url != null) {
        view.context.openWebsite(url)
      }
    }

    private fun onCallPhoneClicked() {
      val phoneNumber = contact?.getContactPhone()
      if (phoneNumber != null) {
        view.context.dialPhone(phoneNumber)
      }
    }

    private fun onSendMailClicked() {
      val mail = contact?.getContactMail()
      if (mail != null) {
        view.context.openMailClient(mail)
      }
    }

    fun View.hideIfNoContent(content: CharSequence?, visibilityWhenNoContent: Int = View.GONE) {
      visibility = if (content.isNullOrEmpty()) visibilityWhenNoContent else View.VISIBLE
    }
  }
}