package com.tobianoapps.shibeapi.di

import android.util.Log
import com.moczul.ok2curl.CurlInterceptor
import com.tobianoapps.shibeapi.ShibeViewModel
import com.tobianoapps.shibeapi.list.repository.ShibeRepositoryImpl
import io.ktor.http.HttpMethod
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.accept
import io.ktor.client.request.parameter
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Koin {

    private const val TIMEOUT = 10000L
    private const val SHIBE_ITEM_COUNT = 100

    val appModule = module {

        // Repo
        factory { ShibeRepositoryImpl() }

        // View Model
        viewModel { ShibeViewModel(get()) }

        // Coroutine Dispatcher
        factory { Dispatchers.IO }


        // Ktor Http Client
        single {
            HttpClient(OkHttp) {

                // Json
                install(JsonFeature) {
                    serializer = KotlinxSerializer(
                    )
                }

                // Timeout
                install(HttpTimeout) {
                    TIMEOUT.let {
                        requestTimeoutMillis = it
                        connectTimeoutMillis = it
                        socketTimeoutMillis = it
                    }
                }

                // Apply to All Requests
                defaultRequest {

                    parameter("count", SHIBE_ITEM_COUNT)
                    parameter("urls", "true")
                    parameter("httpsUrls", "true")

                    // Content Type
                    if (this.method != HttpMethod.Get) contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }

                // Optional OkHttp Interceptors
                engine {
                    addInterceptor(CurlInterceptor { Log.v("Curl", it) })
                }
            }
        }
    }
}
