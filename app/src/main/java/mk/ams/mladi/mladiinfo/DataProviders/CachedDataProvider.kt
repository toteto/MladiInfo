package mk.ams.mladi.mladiinfo.DataProviders

import android.content.Context
import mk.ams.mladi.mladiinfo.DataModels.*

class CachedDataProvider(val context: Context) : DataProviderServices {
  val client = MladiInfoApiClient.client

  override fun getTraining(): DataProviderCallback<List<Training>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSeminars(): DataProviderCallback<List<Training>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getConferences(): DataProviderCallback<List<Training>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getJobsAndInternships(): DataProviderCallback<List<Work>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getInternships(): DataProviderCallback<List<Work>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getJobs(): DataProviderCallback<List<Work>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getOrganizations(): DataProviderCallback<List<Organization>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getStudentOrganizations(): DataProviderCallback<List<Organization>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getNonGovermentOrganizations(): DataProviderCallback<List<Organization>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getScholarships(): DataProviderCallback<List<Scholarship>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDorms(): DataProviderCallback<List<Dorm>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getLibraries(): DataProviderCallback<List<Library>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getUniversities(): DataProviderCallback<List<School>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getFaculties(universityId: Int): DataProviderCallback<List<Faculty>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getArticles(): DataProviderCallback<List<Article>> {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  fun setTraining(input: List<Training>) {

  }

  fun setSeminars(input: List<Training>) {

  }

  fun setConferences(input: List<Training>) {

  }

  fun setJobsAndInternships(input: List<Work>) {

  }

  fun setInternships(input: List<Work>) {

  }

  fun setJobs(input: List<Work>) {

  }

  fun setOrganizations(input: List<Organization>) {

  }

  fun setStudentOrganizations(input: List<Organization>) {

  }

  fun setNonGovermentOrganizations(input: List<Organization>) {

  }

  fun setScholarships(input: List<Scholarship>) {

  }

  fun setDorms(input: List<Dorm>) {

  }

  fun setLibraries(input: List<Library>) {

  }

  fun setUniversities(input: List<School>) {

  }

  fun setFaculties(inputId: Int, input: List<School>) {

  }

  fun setArticles(input: List<Article>) {

  }
}