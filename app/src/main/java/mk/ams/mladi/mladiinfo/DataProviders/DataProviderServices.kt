package mk.ams.mladi.mladiinfo.DataProviders

import mk.ams.mladi.mladiinfo.DataModels.*

interface DataProviderServices {
  fun getTraining(callback: Callback<List<Training>>)

  fun getSeminars(callback: Callback<List<Training>>)

  fun getConferences(callback: Callback<List<Training>>)

  fun getJobsAndInternships(callback: Callback<List<Work>>)

  fun getInternships(callback: Callback<List<Work>>)

  fun getJobs(callback: Callback<List<Work>>)

  fun getOrganizations(callback: Callback<List<Organization>>)

  fun getStudentOrganizations(callback: Callback<List<Organization>>)

  fun getNonGovernmentOrganizations(callback: Callback<List<Organization>>)

  fun getScholarships(callback: Callback<List<Scholarship>>)

  fun getDorms(callback: Callback<List<Dorm>>)

  fun getLibraries(callback: Callback<List<Library>>)

  fun getUniversities(callback: Callback<List<School>>)

  fun getFaculties(id: Int, callback: Callback<List<Faculty>>)

  fun getArticles(callback: Callback<List<Article>>)
}