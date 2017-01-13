package mk.ams.mladi.mladiinfo.DataProviders

import mk.ams.mladi.mladiinfo.DataModels.*

interface DataProviderServices {
  fun getTraining(): DataProviderCallback<List<Training>>

  fun getSeminars(): DataProviderCallback<List<Training>>

  fun getConferences(): DataProviderCallback<List<Training>>

  fun getJobsAndInternships(): DataProviderCallback<List<Work>>

  fun getInternships(): DataProviderCallback<List<Work>>

  fun getJobs(): DataProviderCallback<List<Work>>

  fun getOrganizations(): DataProviderCallback<List<Organization>>

  fun getStudentOrganizations(): DataProviderCallback<List<Organization>>

  fun getNonGovermentOrganizations(): DataProviderCallback<List<Organization>>

  fun getScholarships(): DataProviderCallback<List<Scholarship>>

  fun getDorms(): DataProviderCallback<List<Dorm>>

  fun getLibraries(): DataProviderCallback<List<Library>>

  fun getUniversities(): DataProviderCallback<List<School>>

  fun getFaculties(universityId: Int): DataProviderCallback<List<Faculty>>

  fun getArticles(): DataProviderCallback<List<Article>>
}