package co.sirano.news.di

import co.sirano.news.BuildConfig
import co.sirano.news.data.remote.ApiService
import co.sirano.news.data.remote.NetworkConstants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private val contentType = "application/json".toMediaType()

    // 기본 JSON serializeJson 설정.
    private val serializeJson = Json {
        encodeDefaults = true
        prettyPrint = true
        isLenient = true // Json 큰따옴표 느슨하게 체크.
        ignoreUnknownKeys = true // Field 값이 없는 경우 무시
        coerceInputValues = true // "null" 이 들어간경우 default Argument 값으로 대체
        explicitNulls = false // "null"은 직렬화 하지 않게 처리
    }

    // 네트워크 로깅 설정.
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            if (BuildConfig.DEBUG) {
                Timber.d(message)
            }
        }

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return interceptor
    }

    // OkHttpClient 설정.
    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                addInterceptor(interceptor)
                connectTimeout(15, TimeUnit.SECONDS)
                readTimeout(15, TimeUnit.SECONDS)
                writeTimeout(15, TimeUnit.SECONDS)
            }
            .build()
    }

    // Retrofit 설정.
    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(serializeJson.asConverterFactory(contentType))
            .build()
    }

    // ApiService 설정.
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
