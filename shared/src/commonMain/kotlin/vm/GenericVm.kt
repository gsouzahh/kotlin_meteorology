package vm

import ClimaDispatcher
import PostResponse.*
import api.MeteorologyApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class GenericVm {
    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext
        get() = job + ClimaDispatcher
    private val climaScope = CoroutineScope(coroutineContext)

    private val api = MeteorologyApi()
    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)

    val uiStateFlow: StateFlow<UiState>
        get() = _uiStateFlow

    private val _results = MutableStateFlow(
        Results(
            coord = Coords(0f, 0f),
            weather = emptyList(),
            name = "",
            main = Main(0f, 0f, 0f),
            sys = Sys("")
        )
    )

    private val _daily = MutableStateFlow(
        Daily(
            current = Current(0, 0f, 0f, 0, emptyList()),
            daily = emptyList(),
        )
    )

    val results: StateFlow<Results>
        get() = _results

    val daily: StateFlow<Daily>
        get() = _daily

    fun getApi(lat: Double, long: Double) {
        climaScope.launch {
            _uiStateFlow.value = UiState.Loading

            _results.value = api.getAllLaunches(lat, long)

            kotlin.runCatching { api.getAllLaunches(lat, long) }
                .onSuccess { _uiStateFlow.value = UiState.Success }
                .onFailure { _uiStateFlow.value = UiState.Error }
        }
    }

    fun getApiWeek(lat: Double, long: Double) {
        climaScope.launch {
            _daily.value = api.getWeekLaunches(lat, long)

            kotlin.runCatching { api.getWeekLaunches(lat, long) }
                .onSuccess { _uiStateFlow.value = UiState.Success }
                .onFailure { _uiStateFlow.value = UiState.Error }
        }
    }

    sealed class UiState {
        object Success : UiState()
        object Error : UiState()
        object Loading : UiState()
        object Initial : UiState()
    }
}