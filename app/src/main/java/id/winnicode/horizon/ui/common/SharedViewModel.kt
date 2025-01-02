package id.winnicode.horizon.ui.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.CommentRequest
import id.winnicode.horizon.model.News
import id.winnicode.horizon.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SharedViewModel(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
): ViewModel() {
    private val _userSession: StateFlow<AuthN> = userPreferences.getUserSession().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AuthN("", "", false)
    )
    val userSession: StateFlow<AuthN>
        get() = _userSession

    private val _uiState: MutableStateFlow<UiState<RegisterResponse>> =
        MutableStateFlow(UiState.Loading)


    val uiState: StateFlow<UiState<RegisterResponse>>
        get() = _uiState

    private val _newsUiState: MutableStateFlow<UiState<News>> =
        MutableStateFlow(UiState.Loading)

    val newsUiState: StateFlow<UiState<News>>
        get() = _newsUiState

    private val _profileUiState = MutableStateFlow(UserProfile("", "", "", ""))

    val profileUiState: StateFlow<UserProfile>
        get() = _profileUiState

    fun deleteSession() {
        viewModelScope.launch {
            userPreferences.logout()
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            userRepository.logout(token)
                .catch { e ->
//                    when (e) {
//                        is HttpException -> {
//                            val errorResponse = e.response()?.errorBody()?.string()
//                            val json = JSONObject(errorResponse.toString())
//                            val message = json.optString("message", "Unknown error")
                    _uiState.value = UiState.Error(e.message.toString())
//                        }
//                    }
                }
                .collect { response ->
                    _uiState.value = UiState.Success(response)
                }
        }
    }

    fun fetchUserProfile(token: String) {
        viewModelScope.launch {
            userRepository.fetchUserProfile(token)
                .catch { e ->
//                    when (e) {
//                        is HttpException -> {
//                            val errorResponse = e.response()?.errorBody()?.string()
//                            val json = JSONObject(errorResponse.toString())
//                            val message = json.optString("message", "Unknown error")
                    Log.e("Profile", e.message.toString())
//                        }
//                    }
                }
                .collect { news ->
                    _profileUiState.value = news
                }
        }
    }

    fun commentNew(token: String, newsId: Int, request: CommentRequest) {
        viewModelScope.launch {
            userRepository.comment(token, newsId, request)
                .catch {e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect{response ->
                    _uiState.value = UiState.Success(response)
                }
        }
    }

    fun fetchNewsById(token: String, NewsId: Int) {
        viewModelScope.launch {
            userRepository.fetchNewsById(token, NewsId)
                .catch {e ->
                    _newsUiState.value = UiState.Error(e.message.toString())
                }
                .collect{ news ->
                    _newsUiState.value = UiState.Success(news)
                }
        }

    }



}