package id.winnicode.horizon.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class LoginViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences


): ViewModel() {

    init {
        viewModelScope.launch {
            userPreferences.getUserSession()
                .collect { authN ->
                    _userSession.value = authN
                }
        }
    }

    private val _uiState: MutableStateFlow<UiState<AuthN>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AuthN>>
        get() = _uiState

    private val _userSession: MutableStateFlow<AuthN> = MutableStateFlow(AuthN("","", false))
    val userSession: StateFlow<AuthN>
    get() = _userSession

    fun saveUserSession(user: AuthN) {
        viewModelScope.launch {
            userPreferences.saveUserSession(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()
        }
    }

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            userRepository.login(request)
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
                .collect{user ->
                    _uiState.value = UiState.Success(user)
                }
        }
    }
}