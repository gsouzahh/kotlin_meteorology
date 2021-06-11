package PostResponse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    @SerialName("current")
    val current: Current,
    @SerialName("daily")
    val daily: List<Week>
)

@Serializable
data class Current(
    @SerialName("dt")
    val dt: Int,
    @SerialName("temp")
    val temp: Float,
    @SerialName("feels_like")
    val feels_like: Float,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("weather")
    val weather: List<WeatherWeek>

)

@Serializable
data class Week(
    @SerialName("dt")
    val dt: Int,
    @SerialName("temp")
    val temp: TempWeek,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("weather")
    val weather: List<WeatherWeek>
)

@Serializable
data class TempWeek(
    @SerialName("min")
    val min: Float,
    @SerialName("max")
    val max: Float
)

@Serializable
data class WeatherWeek(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)