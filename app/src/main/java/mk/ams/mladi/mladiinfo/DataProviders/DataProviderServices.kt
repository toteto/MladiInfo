package mk.ams.mladi.mladiinfo.DataProviders

import android.provider.ContactsContract
import mk.ams.mladi.mladiinfo.DataModels.*

interface DataProviderServices {
  fun getTraining(): List<Training>

  fun getSeminars(): List<Training>

  fun getConferences(): List<Training>

  fun getJobsAndInternships(): List<Work>

  fun getInternships(): List<Work>

  fun getJobs(): List<Work>

  fun getOrganizations(): List<Organization>

  fun getStudentOrganizations(): List<Organization>

  fun getNonGovermentOrganizations(): List<Organization>

  fun getScholarships(): List<Scholarship>

  fun getDorms(): List<Dorm>

  fun getLibraries(): List<Library>

  fun getUniversities(): List<School>

  fun getFaculties(universityId: Int): List<Faculty>

  fun getArticles(): List<Article>
}