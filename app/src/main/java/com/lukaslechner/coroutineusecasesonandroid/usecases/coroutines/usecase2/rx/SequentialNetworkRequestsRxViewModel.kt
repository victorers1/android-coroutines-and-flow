package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.rx

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    // Creates group of disposables, but we have only one
    private val disposables = CompositeDisposable()

    // Code much smaller
    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        mockApi.getRecentAndroidVersions().flatMap { androidVersions ->
            val recentVersion = androidVersions.last()
            mockApi.getAndroidVersionFeatures(recentVersion.apiLevel)
        }.subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(onSuccess = { featureVersions ->
                uiState.value = UiState.Success(featureVersions)

            }, onError = {
                // Error handling in only one place
                uiState.value = UiState.Error("Network request failed!")
            }).addTo(disposables)

    }

    // When the screen is dismissed
    override fun onCleared() {
        super.onCleared()

        // dispose any ongoing requests
        disposables.clear()
    }
}