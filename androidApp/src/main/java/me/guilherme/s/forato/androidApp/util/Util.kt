package me.guilherme.s.forato.androidApp.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.guilherme.s.forato.androidApp.R
import java.util.*
import kotlin.math.round

@ExperimentalCoroutinesApi
class Util {
    private val dailyWeek: MutableMap<String, String> =
        mutableMapOf(
            "FRIDAY" to "SEXTA-FEIRA",
            "MONDAY" to "SEGUNDA-FEIRA",
            "SATURDAY" to "SÁBADO",
            "SUNDAY" to "DOMIGO",
            "THURSDAY" to "QUINTA-FEIRA",
            "TUESDAY" to "TERÇA",
            "WEDNESDAY" to "QUARTA-FEIRA"
        )

    private val changeImage: MutableMap<String, Int> =
        mutableMapOf(
            "CHUVA LEVE" to R.drawable.chuva_leve,
            "CHUVA MODERADA" to R.drawable.chuva_moderada,
            "CHUVA FORTE" to R.drawable.chuva_forte,
            "NUBLADO" to R.drawable.nublado,
            "CÉU LIMPO" to R.drawable.sky_clear,
            "ALGUMAS NUVENS" to R.drawable.nuvens,
            "NUVENS DISPERSAS" to R.drawable.nuvens,
            "NÉVOA" to R.drawable.nublado,
            "CHUVA E NEVE" to R.drawable.snow,
        )

    fun convertCelsius(value: Float): Int = round(value - 273.15f).toInt()

    fun convertDay(long: Long): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale("pt", "BR"))
        val result = Instant.parse(sdf.format(Date(long * 1000)))
            .toLocalDateTime(TimeZone.currentSystemDefault())

        return dailyWeek.getValue(result.dayOfWeek.toString())
    }

    fun setImage(string: String):Int = changeImage.getValue(string.uppercase(Locale.getDefault()))
}