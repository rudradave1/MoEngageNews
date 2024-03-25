package com.rudra.moengagenews.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moengagenews.data.NewsRepository
import com.rudra.moengagenews.data.model.News
import com.rudra.moengagenews.data.model.Source
import com.rudra.moengagenews.util.parseDate
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    // Expose immutable state to the UI
    private val _newsState = mutableStateOf<News?>(null)
    val newsState: State<News?> = _newsState

    private var selectedSource: Source? = null
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        fetchData()
    }

    // Function to fetch data from the repository
    private fun fetchData() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Call repository's fetchData method and update _newsState
                repository.fetchData { fetchedData ->
                    _newsState.value = fetchedData
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }

    // Function to sort articles by date from old to new
    fun sortArticlesByDateOldToNew() {
        _isLoading.value = true
        // Sort articles by date and update _newsState
        _newsState.value = newsState.value?.articles?.sortedBy {
            parseDate(it.publishedAt)
        }?.let { _newsState.value?.copy(articles = it) }
        _isLoading.value = false
    }

    // Function to sort articles by date from new to old
    fun sortArticlesByDateNewToOld() {
        _isLoading.value = true
        // Sort articles by date in descending order and update _newsState
        _newsState.value = newsState.value?.articles?.sortedByDescending {
            parseDate(it.publishedAt)
        }?.let { _newsState.value?.copy(articles = it) }
        _isLoading.value = false
    }

    // Function to filter articles by source
    fun filterArticlesBySource(source: Source) {
        _isLoading.value = true
        // Get the original list of articles from the repository
        val originalArticles = repository.newsState.value?.articles ?: emptyList()

        // Filter the original list based on the selected source
        val filteredArticles = originalArticles.filter { it.source.id == source.id }

        // Update _newsState with the filtered articles
        _newsState.value = _newsState.value?.copy(articles = filteredArticles)

        // Update selected source
        // Note: This logic should be revisited to ensure it behaves as expected
        // and selectedSource is updated correctly
        // (e.g., what happens when no articles are found for the selected source?)
        selectedSource = source
        _isLoading.value = false
    }

    // Function to get the selected source
    fun getSelectedSource(): Source? {
        // If selectedSource is null, set it to the first article's source
        if (selectedSource == null) selectedSource = newsState.value?.articles?.firstOrNull()?.source
        return selectedSource
    }
}
