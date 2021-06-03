package api

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import PostResponse.Results

class MeteorologyApi {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllLaunches(LAT: Double, LONG: Double): Results {
        println(">>>>>>>>>> ${Constants.LAUNCHES_ENDPOINT}&lat=$LAT&lon=$LONG")
        return httpClient.get("${Constants.LAUNCHES_ENDPOINT}&lat=$LAT&lon=$LONG")
    }
}