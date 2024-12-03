package id.winnicode.horizon.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.UserProfile
import id.winnicode.horizon.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<UserProfile>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<UserProfile>>
        get() = _uiState

    private val _userSession: StateFlow<AuthN> = userPreferences.getUserSession()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AuthN("","", false)
        )

    val userSession: StateFlow<AuthN>
        get() = _userSession


    fun fetchUserProfile(token: String) {
        viewModelScope.launch {
            userRepository.fetchUserProfile(token)
                .catch { e ->
//                    when (e) {
//                        is HttpException -> {
//                            val errorResponse = e.response()?.errorBody()?.string()
//                            if (errorResponse != null){
//                                val json = JSONObject(errorResponse.toString())
//                                val message = json.optString("message", "Unknown error")
//                                _uiState.value = UiState.Error("${e.message} $message")
//                            }
//                        }
//                    }
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect { news ->
                    _uiState.value = UiState.Success(news)
                }
        }
    }

}