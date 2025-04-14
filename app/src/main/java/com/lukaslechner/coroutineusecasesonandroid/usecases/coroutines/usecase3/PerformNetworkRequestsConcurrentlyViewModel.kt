package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val oreoFeatures = mockApi.getAndroidVersionFeatures(27)
                val pieFeatures = mockApi.getAndroidVersionFeatures(28)
                val android10Features = mockApi.getAndroidVersionFeatures(27)

                val versionFeatures = listOf(oreoFeatures, pieFeatures, android10Features)
                uiState.value = UiState.Success(versionFeatures)

            } catch (e: Exception) {
                uiState.value = UiState.Error("Network Request failed! Details: $e")
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading

        val oreoFeaturesDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(27)
        }

        val pieFeaturesDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(28)
        }

        val android10FeaturesDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(29)
        }

        viewModelScope.launch {
            try {
                val oreoFeatures = oreoFeaturesDeferred.await()
                val pieFeatures = pieFeaturesDeferred.await()
                val androidFeatures = android10FeaturesDeferred.await()

                val versionFeatures = listOf(oreoFeatures, pieFeatures, androidFeatures)
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network Request failed! Details: $e")
            }
        }
    }
}