package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
    uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val recentVersion = mockApi.getRecentAndroidVersions()

                val versionFeatures = recentVersion.map { version ->
                    mockApi.getAndroidVersionFeatures(version.apiLevel)
                }

                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value =UiState.Error("Network Request failed! Details: $e")
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val recentVersions = mockApi.getRecentAndroidVersions()
                val versionFeatures = recentVersions.map { version ->
                    async {
                        mockApi.getAndroidVersionFeatures(version.apiLevel)
                    }
                }.awaitAll()

                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network Request failed! Details: $e")
            }
        }
    }
}