package mk.ams.mladi.mladiinfo.DataModels

interface ContactInterface : DividerColorInterface {
  fun getContactTitle(): String
  fun getContactDescription(): String?
  fun getContactPhone(): String?
  fun getContactMail(): String?
  fun getContactAddress(): String?
  fun getContactLatLong(): Pair<Double, Double>?
  fun getContactSite(): String?
  fun getContactFacebookProfile(): String?
  fun getContactTwitterProfile(): String?
  fun getContactId(): Long
  fun getContactLogoUrl(): String?
  fun queryContact(query: String): Boolean = getContactTitle().contains(query, true)
}