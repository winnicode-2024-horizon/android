package id.winnicode.horizon.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.News
import id.winnicode.horizon.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<News>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<News>>>
        get() = _uiState

    private val _userSession: StateFlow<AuthN> = userPreferences.getUserSession()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthN("","", false)
    )

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
    fun fetchNews(token: String) {
        viewModelScope.launch {
            userRepository.fetchNews(token)
                .catch {e ->
//                    when (e) {
//                        is HttpException -> {
//                            val errorResponse = e.response()?.errorBody()?.string()
//                            val json = JSONObject(errorResponse.toString())
//                            val message = json.optString("message", "Unknown error")
//                            _uiState.value = UiState.Error("${e.message} $message")
//                        }
//                    }
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect{news ->
                    _uiState.value = UiState.Success(news)
                }
        }
    }

    fun searchNews(token: String, query: String) {
        viewModelScope.launch {
            userRepository.searchNews(query, token)
                .catch {e ->
//                    when (e) {
//                        is HttpException -> {
//                            val errorResponse = e.response()?.errorBody()?.string()
//                            val json = JSONObject(errorResponse.toString())
//                            val message = json.optString("message", "Unknown error")
//                            _uiState.value = UiState.Error("${e.message} $message")
//                        }
//                    }
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect{news ->
                    _uiState.value = UiState.Success(news)
                }
        }
    }
}