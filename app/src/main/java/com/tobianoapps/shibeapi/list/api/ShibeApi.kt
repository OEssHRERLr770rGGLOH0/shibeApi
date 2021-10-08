package com.tobianoapps.shibeapi.list.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Main endpoint for the Shibe API
 */
class ShibeApi: KoinComponent {

    private val client: HttpClient by inject()

    suspend fun getShibes(): List<String> = client.get("https://shibe.online/api/shibes")
}
