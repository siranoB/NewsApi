package co.sirano.news.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("articles")
    val articles: List<ArticleData> = emptyList()
) : BaseResponse()

@Serializable
class ArticleData(
    @SerialName("source")
    val source: SourceData? = null,
    @SerialName("author")
    val author: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("url")
    val url: String = "",
    @SerialName("urlToImage")
    val urlToImage: String = "",
    @SerialName("publishedAt")
    val publishedAt: String = "",
    @SerialName("content")
    val content: String = ""
)

@Serializable
class SourceData(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = ""
)
