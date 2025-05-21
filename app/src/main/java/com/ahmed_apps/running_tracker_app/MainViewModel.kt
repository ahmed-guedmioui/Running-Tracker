package com.ahmed_apps.running_tracker_app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.core.domian.util.SessionStorage
import kotlinx.coroutines.launch

/**
 * @author Ahmed Guedmioui
 */
class MainViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(isLoggedIn = sessionStorage.get() != null)
            state = state.copy(isCheckingAuth = false)
        }
    }

    fun setAnalyticsDialogVisibility(isVisible: Boolean) {
        state = state.copy(showAnalyticsInstallDialog = isVisible)
    }
}





















