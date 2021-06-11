package api

class Constants {

    companion object {
        const val REQUEST_CODE = 101
        const val KEY = "9ebb9fe1fa90ccf79f8d2e19901ae339"
        const val LAUNCHES_ENDPOINT_TODAY = "https://api.openweathermap.org/data/2.5/weather?appid=$KEY&lang=pt_br"
        const val LAUNCHES_ENDPOINT_WEEK = "https://api.openweathermap.org/data/2.5/onecall?exclude=minutely,hourly&appid=$KEY&lang=pt_br"
    }

}