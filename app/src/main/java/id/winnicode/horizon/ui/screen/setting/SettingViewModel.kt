package id.winnicode.horizon.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.winnicode.horizon.data.prefrences.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingViewModel(
    private val userPreferences: UserPreferences,
):ViewModel() {
    private val _userSession: StateFlow<Boolean> = userPreferences.getThemeSetting()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val userSession: StateFlow<Boolean>
        get() = _userSession

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            userPreferences.saveThemeSetting(isDarkModeActive)
        }
    }
}