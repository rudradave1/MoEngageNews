package com.rudra.moengagenews.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rudra.moengagenews.data.model.Article
import com.rudra.moengagenews.data.model.Source
import com.rudra.moengagenews.ui.theme.BodyText
import com.rudra.moengagenews.ui.theme.HeadText

@Composable
fun NewsSourcesFilter(
    articles: List<Article>,
    selectedSource: Source,
    onItemClick: (Source) -> Unit
) {
    val uniqueSources = remember {
        articles.map { it.source }.distinctBy { it.id }
    }

    val selectedTabIndex = uniqueSources.indexOfFirst { it.id == selectedSource.id }

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = HeadText,
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        },
        edgePadding = 4.dp
    ) {
        uniqueSources.forEachIndexed { index, source ->
            Tab(
                text = { Text(text = source.name) },
                selected = index == selectedTabIndex,
                onClick = { onItemClick(source) },
                selectedContentColor = HeadText,
                unselectedContentColor = BodyText,
            )
        }
    }
}
