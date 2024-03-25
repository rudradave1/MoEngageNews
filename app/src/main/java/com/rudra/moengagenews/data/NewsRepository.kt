package com.rudra.moengagenews.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.rudra.moengagenews.data.model.News
import com.rudra.moengagenews.network.fetchDataFromApi
import com.rudra.moengagenews.util.parseJsonToNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// NewsRepository responsible for fetching news data from the API
class NewsRepository {
    // Mutable state to hold the fetched news data
    private val _newsState = mutableStateOf<News?>(null)
    val newsState: State<News?> = _newsState

    // API URL for fetching news data
    private val apiUrl =
        "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"

    // Function to fetch data from the API
    suspend fun fetchData(onDataFetched: (News?) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                // Fetch data from the API
                fetchDataFromApi(
                    apiUrl = apiUrl,
                    onError = { error ->
                        // Handle error
                        error.printStackTrace()
                    },
                    onSuccess = { data ->
                        // Parse JSON data to News object and update newsState
                        _newsState.value = parseJsonToNews(data)
                        // Notify onDataFetched callback with fetched data
                        onDataFetched(_newsState.value)
                    }
                )
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                // Notify onDataFetched callback with null when there's an error
                onDataFetched(null)
            }
        }
    }
}