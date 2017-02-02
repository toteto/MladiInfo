package mk.ams.mladi.mladiinfo.DataModels

interface ContactInterface {
  fun getContactTitle(): String
  fun getContactDescription(): String?
  fun getContactPhone(): String?
  fun getContactMail(): String?
  fun getContactAddress(): String?
  fun getContactLatLong(): Pair<Double, Double>?
  fun getContactSite(): String?
  fun getContactFacebookProfile(): String?
  fun getContactTwitterProfile(): String?
}