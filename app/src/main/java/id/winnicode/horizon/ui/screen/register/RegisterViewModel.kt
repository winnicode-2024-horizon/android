package id.winnicode.horizon.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.model.RegisterRequest
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class RegisterViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<RegisterResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<RegisterResponse>>
        get() = _uiState

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            userRepository.register(request)
                .catch {e ->
                    when (e) {
                        is HttpException -> {
                            val errorResponse = e.response()?.errorBody()?.string()
                            val json = JSONObject(errorResponse.toString())
                            val message = json.optString("message", "Unknown error")
                            _uiState.value = UiState.Error("${e.message} $message")
                        }
                    }
                }
                .collect{ response ->
                    if (response.status == "success") {
                        _uiState.value = UiState.Success(response)
                    } else {
                        _uiState.value = UiState.Error(response.message ?: "Unknown error")
                    }
                }

        }
    }
}