package id.winnicode.horizon.ui.screen.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import id.winnicode.horizon.MainApplication
import id.winnicode.horizon.factory.ViewModelFactory
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.News
import id.winnicode.horizon.ui.common.UiState
import id.winnicode.horizon.ui.theme.GreyDark
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    NewsId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    ),
) {
    val userSession = viewModel.userSession.collectAsState().value

    LaunchedEffect(userSession.token, NewsId) {
        if (userSession.token.isNotEmpty()) {
            viewModel.fetchNewsById(userSession.token, NewsId)
        }
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Box (modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    news = data,
                    userSession = userSession,
                    NewsId = NewsId
                )
            }

            is UiState.Error -> {

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailContent(
    news: News,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    ),
    NewsId: Int,
    userSession: AuthN
) {
    var isBookmarked by remember { mutableStateOf(news.isBookmarked) }
    val instant = Instant.parse(news.publishedAt)

    val wibTime = instant.atZone(ZoneId.of("Asia/Jakarta"))

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val formattedTime = wibTime.format(formatter)

    LaunchedEffect(isBookmarked){
        viewModel.fetchNewsById(userSession.token, NewsId)
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        AsyncImage(
            model = news.imageUrl,
            contentDescription = "Article Image",
            modifier = Modifier
                .height(277.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = news.description,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        // Article Title
        Text(
            text = news.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 3,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Author and Date Row
        Column(
            Modifier.padding(16.dp)
        ) {
            Text(
                text = news.author,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
            )
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
        }

        // Bookmark Button
        Button(
            onClick = {
            if (isBookmarked){
                viewModel.deleteBookmarkNew(userSession.token, news.id)
            } else {
                viewModel.addBookmarkNew(userSession.token, news.id)
            }
            isBookmarked = !isBookmarked
            },
            shape = RoundedCornerShape(7.dp),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.DarkGray,
                disabledContainerColor = GreyDark,
                disabledContentColor = Color.White
            ),
            border = BorderStroke(1.dp, Color.DarkGray),
            modifier = Modifier
                .height(45.dp)
                .width(196.dp)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (isBookmarked){
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Bookmark",
                        tint = Color.DarkGray
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = Color.DarkGray
                    )
                }
                if (isBookmarked){
                    Text(text = "Bookmarked",
                        Modifier.padding(6.dp)
                    )
                } else {
                    Text(text = "Bookmark",
                        Modifier.padding(6.dp)
                    )
                }

            }
        }

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        )

        // Article Content
        Text(
            text = news.content,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineBreak = LineBreak.Paragraph,
                hyphens = Hyphens.Auto
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
