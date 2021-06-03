package vm

import PostResponse.Coords
import PostResponse.Main
import PostResponse.Results
import PostResponse.Sys
import api.MeteorologyApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.*

class GenericVm {
    private val api = MeteorologyApi()

    @ExperimentalCoroutinesApi
    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)

    @ExperimentalCoroutinesApi
    val uiStateFlow: StateFlow<UiState>
        get() = _uiStateFlow

    @ExperimentalCoroutinesApi
    private val _results = MutableStateFlow(
        Results(
            coord = Coords(0f, 0f),
            weather = emptyList(),
            name = "",
            main = Main(0f,0f, 0f),
            sys = Sys("")
        )
    )

    @ExperimentalCoroutinesApi
    val results: StateFlow<Results>
        get() = _results

    @ExperimentalCoroutinesApi
    fun getApi(lat: Double, long: Double) {
        MainScope().launch {
            _uiStateFlow.value = UiState.Loading
            _results.value = api.getAllLaunches(lat, long)

            kotlin.runCatching { api.getAllLaunches(lat, long) }
                .onSuccess { _uiStateFlow.value = UiState.Success }
                .onFailure { _uiStateFlow.value = UiState.Error }
        }
    }

    fun convertCelsius(value: Float): Int {
        return round(value - 273.15f).toInt()
    }

    sealed class UiState {
        object Success : UiState()
        object Error : UiState()
        object Loading : UiState()
        object Initial : UiState()
    }
}