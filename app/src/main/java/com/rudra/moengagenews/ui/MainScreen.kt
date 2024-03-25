package com.rudra.moengagenews.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rudra.moengagenews.R
import com.rudra.moengagenews.createNotificationChannel
import com.rudra.moengagenews.data.NewsRepository
import com.rudra.moengagenews.data.model.News
import com.rudra.moengagenews.showSimpleNotification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Get the current context
    val context = LocalContext.current

    // Define notification channel ID and notification ID
    val channelId = "MyTestChannel"
    val notificationId = 0

    // Create notification channel when the component is launched
    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }

    // Main column layout for the screen
    Column(
        modifier = Modifier
            .padding(1.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxSize(),
    ) {
        // ViewModel initialization
        val repository = remember { NewsRepository() } // Instantiate NewsRepository here or inject it from elsewhere
        val viewModel: NewsViewModel = remember {
            NewsViewModel(repository)
        }

        // Observe news state
        val newsState: News? by viewModel.newsState

        // State for filter dialog visibility
        val openDialog = remember { mutableStateOf(false) }

        // State for sorting option
        val sortByOldToNew = remember { mutableStateOf(false) }

        // Sort articles based on the selected option
        if (sortByOldToNew.value) {
            viewModel.sortArticlesByDateOldToNew()
        } else {
            viewModel.sortArticlesByDateNewToOld()
        }

        // Top app bar with title and action icons
        TopAppBar(
            title = { Text(stringResource(id = R.string.moengage)) },
            actions = {
                // Notification icon button
                IconButton(
                    onClick = {
                        showSimpleNotification(
                            context,
                            channelId,
                            notificationId,
                            "Hello World",
                            "This is a simple notification with default priority."
                        )
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_notifications_24),
                        contentDescription = "notification"
                    )
                }
                // Filter icon button to open dialog
                IconButton(
                    onClick = {
                        openDialog.value = true
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                        contentDescription = "Search"
                    )
                }
            }
        )
        // Spacer
        Spacer(modifier = Modifier.height(8.dp))

        // Display news sources filter
        newsState?.articles?.let {
            viewModel.getSelectedSource()?.let { source ->
                NewsSourcesFilter(
                    articles = it,
                    selectedSource = source,
                    onItemClick = {
                        viewModel.filterArticlesBySource(it)
                    }
                )
            }
        }

        // Spacer
        Spacer(modifier = Modifier.height(24.dp))

        // Display news articles or loading indicator
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (!viewModel.isLoading.value) {
                viewModel.newsState.value?.let { news ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(news.articles) { article ->
                            ArticleItem(article = article) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                                context.startActivity(intent)
                            }
                        }
                    }
                }
            } else {
                // Display loading indicator
                CircularProgressIndicator()
            }
        }

        // Display filter dialog if openDialog is true
        if (openDialog.value) {
            FilterDialog(
                openDialog = openDialog,
                sortByOldToNew = sortByOldToNew,
                onCloseDialog = { openDialog.value = false }
            )
        }
    }
}
