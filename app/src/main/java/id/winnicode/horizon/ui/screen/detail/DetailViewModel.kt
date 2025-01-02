package id.winnicode.horizon.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.remote.response.RegisterResponse
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
import org.json.JSONObject
import retrofit2.HttpException

class DetailViewModel(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<News>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<News>>
        get() = _uiState

    private val _bookmarkState: MutableStateFlow<UiState<RegisterResponse>> =
        MutableStateFlow(UiState.Loading)
    val bookmarkState: StateFlow<UiState<RegisterResponse>>
        get() = _bookmarkState

    private val _userSession: StateFlow<AuthN> = userPreferences.getUserSession()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AuthN("","", false)
        )

    val userSession: StateFlow<AuthN>
        get() = _userSession


    fun fetchNewsById(token: String, NewsId: Int) {
        viewModelScope.launch {
            userRepository.fetchNewsById(token, NewsId)
                .catch {e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect{ news ->
                    _uiState.value = UiState.Success(news)
                }
        }

    }

    fun addBookmarkNew(token: String, id: Int) {
        viewModelScope.launch {
            userRepository.addBookmark(token, id)
                .catch {e ->
                    when (e) {
                        is HttpException -> {
                            val errorResponse = e.response()?.errorBody()?.string()
                            val json = JSONObject(errorResponse.toString())
                            val message = json.optString("message", "Unknown error")
                            _bookmarkState.value = UiState.Error("${e.message} $message")
                        }
                    }
                }
                .collect{ response ->
                    _bookmarkState.value = UiState.Success(response)
                }

        }
    }

    fun deleteBookmarkNew(token: String, id: Int) {
        viewModelScope.launch {
            userRepository.deleteBookmark(token, id)
                .catch {e ->
                    when (e) {
                        is HttpException -> {
                            val errorResponse = e.response()?.errorBody()?.string()
                            val json = JSONObject(errorResponse.toString())
                            val message = json.optString("message", "Unknown error")
                            _bookmarkState.value = UiState.Error("${e.message} $message")
                        }
                    }
                }
                .collect{ response ->
                    _bookmarkState.value = UiState.Success(response)
                }
        }
    }
}