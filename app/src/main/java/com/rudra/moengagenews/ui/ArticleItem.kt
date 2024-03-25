package com.rudra.moengagenews.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rudra.moengagenews.data.model.Article
import com.rudra.moengagenews.util.NetworkImage

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = article.title ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.description ?: "",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.publishedAt ?: "",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Author: ${article.author}",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Content: ${article.content}",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Source: ${article.source.name}",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Load image from URL
            NetworkImage(
                url = article.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Adjust the height as needed
            )
        }
    }
}

