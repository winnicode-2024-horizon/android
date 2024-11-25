package id.winnicode.horizon.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.AuthN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class SharedViewModel(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
): ViewModel() {
    private val _userSession: StateFlow<AuthN> = userPreferences.getUserSession().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AuthN("","", false)
    )
    val userSession: StateFlow<AuthN>
        get() = _userSession

    private val _uiState: MutableStateFlow<UiState<RegisterResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<RegisterResponse>>
        get() = _uiState

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun scheduleLogout(expiredAt: String) {
//        val expirationTime = Instant.parse(expiredAt)
//        val remainingTime = expirationTime.toEpochMilli() - System.currentTimeMillis()
//
//        viewModelScope.launch {
//            if (remainingTime > 0) {
//                delay(remainingTime)
//                logout()
//            } else {
//                logout()
//            }
//        }
//    }
//
    fun deleteSession() {
        viewModelScope.launch {
            userPreferences.logout()
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            userRepository.logout(token)
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
                    _uiState.value = UiState.Success(response)
                }

        }
    }

}