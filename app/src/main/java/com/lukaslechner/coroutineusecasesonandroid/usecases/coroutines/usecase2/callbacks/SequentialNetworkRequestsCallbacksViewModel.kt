package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    // Retrofit calls
    private var getAndroidVersionsCall: Call<List<AndroidVersion>>? = null
    private var getAndroidFeaturesCall: Call<VersionFeatures>? = null

    // This is a callback hell
    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        getAndroidVersionsCall = mockApi.getRecentAndroidVersions()

        // `enqueue`: run retrofit call asynchronously
        getAndroidVersionsCall!!.enqueue(object : Callback<List<AndroidVersion>> {
            override fun onResponse(
                call: Call<List<AndroidVersion>?>, response: Response<List<AndroidVersion>?>
            ) {
                if (response.isSuccessful) {
                    val mostRecentVersion = response.body()!!.last()

                    getAndroidFeaturesCall =
                        mockApi.getAndroidVersionFeatures(mostRecentVersion.apiLevel)

                    // Create a nasty nested callback hell
                    getAndroidFeaturesCall!!.enqueue(object : Callback<VersionFeatures> {
                        override fun onResponse(
                            call: Call<VersionFeatures?>, response: Response<VersionFeatures?>
                        ) {

                            if (response.isSuccessful) {
                                val versionFeatures = response.body()!!
                                uiState.value = UiState.Success(versionFeatures)
                            } else {
                                uiState.value =
                                    UiState.Error("Version features error: server returned bad response")
                            }
                        }

                        override fun onFailure(call: Call<VersionFeatures?>, error: Throwable) {
                            uiState.value =
                                UiState.Error("Version features error: connection failed")
                        }

                    })

                } else {
                    uiState.value =
                        UiState.Error("Android versions error: server returned bad response")
                }
            }

            override fun onFailure(call: Call<List<AndroidVersion>?>, error: Throwable) {
                uiState.value = UiState.Error("Android versions error: connection failed")
            }
        })
    }

    // When the screen is dismissed
    override fun onCleared() {
        super.onCleared()

        // Cancel all ongoing requests
        getAndroidFeaturesCall?.cancel()
        getAndroidFeaturesCall?.cancel()
    }
}