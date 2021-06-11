package api

import PostResponse.Daily
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import PostResponse.Results
import io.ktor.client.features.*

class MeteorologyApi {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllLaunches(LAT: Double, LONG: Double): Results {
        return httpClient.get("${Constants.LAUNCHES_ENDPOINT_TODAY}&lat=$LAT&lon=$LONG")
    }

    suspend fun getWeekLaunches(LAT: Double, LONG: Double): Daily {
        return httpClient.get("${Constants.LAUNCHES_ENDPOINT_WEEK}&lat=$LAT&lon=$LONG")
    }

}