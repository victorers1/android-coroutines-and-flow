package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class NetworkRequestWithTimeoutViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.value = UiState.Loading

//        usingWithTimeout(timeout)

        usingWithTimeoutOrNull(timeout)
    }

    fun usingWithTimeout(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentAndroidVersions = withTimeout(timeout) {
                    mockApi.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (timeoutException: TimeoutCancellationException) {
                uiState.value = UiState.Error("Network request timed out!")
            } catch (exception: Exception) {
                Timber.i(exception)
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }

    fun usingWithTimeoutOrNull(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentAndroidVersions = withTimeoutOrNull(timeout) {
                    mockApi.getRecentAndroidVersions()
                }

                if (recentAndroidVersions != null) {
                    uiState.value = UiState.Success(recentAndroidVersions)
                } else {
                    uiState.value = UiState.Error("Network request timed out!")
                }

            } catch (exception: Exception) {
                Timber.i(exception)
                uiState.value = UiState.Error("Network request failed!")
            }
        }
    }
}