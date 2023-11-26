package co.sirano.news.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 공용 응답 데이터
@Serializable
open class BaseResponse(
    val status: ResponseStatus = ResponseStatus.OK,
    val totalResults: Int = 0,
    val code: ResponseErrorCode = ResponseErrorCode.NETWORK_ERROR,
    val message: String = ""
)

@Serializable
enum class ResponseStatus {
    @SerialName("ok")
    OK,

    @SerialName("error")
    ERROR
}

@Serializable
enum class ResponseErrorCode {
    @SerialName("apiKeyDisabled")
    API_KEY_DISABLED,

    @SerialName("apiKeyExhausted")
    API_KEY_EXHAUSTED,

    @SerialName("apiKeyMissing")
    API_KEY_MISSING,

    @SerialName("parameterInvalid")
    PARAMETER_INVALID,

    @SerialName("parametersMissing")
    PARAMETERS_MISSING,

    @SerialName("rateLimited")
    RATE_LIMITED,

    @SerialName("sourcesTooMany")
    SOURCES_TOO_MANY,

    @SerialName("sourceDoesNotExist")
    SOURCE_DOES_NOT_EXIST,

    @SerialName("unexpectedError")
    UNEXPECTED_ERROR,

    @SerialName("networkError")
    NETWORK_ERROR
}
