package co.sirano.news.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // https://newsapi.org/v2/top-headlines?country=kr&apiKey={api-key}
    @GET("/v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("country") country: String = NetworkConstants.COUNTRY,
        @Query("apiKey") apiKey: String = NetworkConstants.NEWS_API_KEY
    ): Response<String>
}