package PostResponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Results(
    @SerialName("coord")
    val coord: Coords,
    @SerialName("weather")
    val weather: List<Weather>,
    @SerialName("name")
    val name: String,
    @SerialName("sys")
    val sys: Sys,
    @SerialName("main")
    val main: Main
)

@Serializable
data class Coords(
    @SerialName("lat")
    val lat: Float,
    @SerialName("lon")
    val lon: Float,
)

@Serializable
data class Sys(
    @SerialName("country")
    val country: String,
)

@Serializable
data class Main(
    @SerialName("temp")
    val temp: Float,
    @SerialName("feels_like")
    val feels_like: Float,
    @SerialName("humidity")
    val humidity: Float,
)

@Serializable
data class Weather(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)