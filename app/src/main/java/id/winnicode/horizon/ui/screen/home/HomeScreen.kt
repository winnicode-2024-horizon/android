package id.winnicode.horizon.ui.screen.home

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipElevation
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import id.winnicode.horizon.MainApplication
import id.winnicode.horizon.R
import id.winnicode.horizon.factory.ViewModelFactory
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.News
import id.winnicode.horizon.ui.common.UiState
import id.winnicode.horizon.ui.theme.GreyDark
import id.winnicode.horizon.ui.theme.WhiteSmoke

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    ),
    query: String,
    navigateToDetail: (Int) -> Unit,
) {
    val userSession = viewModel.userSession.collectAsState().value

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LaunchedEffect(userSession.token, query) {
                    if (userSession.token.isNotEmpty() && query.isEmpty()) {
                        viewModel.fetchNews(userSession.token)
                    } else if (userSession.token.isNotEmpty() && query.isNotEmpty()) {
                        viewModel.searchNews(userSession.token, query)
                    }
                }
                Box (modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                Column {
                    val selectedFilters = remember { mutableStateOf<String?>(null) }
                    val categoryList = listOf("General", "Health", "Science", "Business", "Technology", "Sports", "Entertainment")

                    LazyRow {
                        items(categoryList) { category ->
                            ElevatedFilterChip(
                                selected = selectedFilters.value == category,
                                onClick = {
                                        if (selectedFilters.value == category) {
                                            selectedFilters.value = null
                                            if (userSession.token.isNotEmpty()) {
                                                viewModel.fetchNews(userSession.token)
                                            }
                                        } else {
                                            selectedFilters.value = category
                                            if (userSession.token.isNotEmpty()) {
                                                viewModel.fetchNewsByCategory(userSession.token, category) //Fetch by Category
                                            }
                                        }
                                },
                                elevation = SelectableChipElevation(
                                    elevation = 4.dp,
                                    pressedElevation = 12.dp,
                                    focusedElevation = 10.dp,
                                    hoveredElevation = 6.dp,
                                    draggedElevation = 14.dp,
                                    disabledElevation = 8.dp

                                ),
                                label = { Text(text = category, Modifier.padding(8.dp)) },
                                leadingIcon = { if (selectedFilters.value == category) Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.secondary) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.secondary,
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    labelColor = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = modifier.height(4.dp))
                    if (uiState.data.isNotEmpty()){
                        HomeContent(
                            news = uiState.data,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail,
                            userSession = userSession
                        )
                    } else{
                        Box (modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center){
                            Text(text = stringResource(R.string.empty_bookmark_message), color = GreyDark)
                        }
                    }

                }

            }

            is UiState.Error -> {
                Box (modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    Text(text = stringResource(R.string.error_home_screen), color = GreyDark)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeContent(
    news: List<News>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    userSession: AuthN
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp),


        ) {
        itemsIndexed(news) { index, new ->
            if (index == 0) {
                Box(
                    modifier = modifier
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(topStart = 27.dp, topEnd = 27.dp))
                        .background(WhiteSmoke)
                        .fillMaxSize()
                ) {
                    FirstArticleItem(
                        news = new,
                        context = context,
                        modifier = Modifier
                            .animateItemPlacement(tween(durationMillis = 100))
                            .clickable {
                                navigateToDetail(new.id)
                            },
                        userSession = userSession)
                }
            } else {
                Box(
                    modifier = modifier
                        .background(WhiteSmoke)
                        .fillMaxSize(),
                ) {
                    RegularArticleItem(
                        news = new,
                        context = context,
                        modifier = Modifier
                            .animateItemPlacement(tween(durationMillis = 100))
                            .clickable {
                                navigateToDetail(new.id)
                            },
                        userSession = userSession)
                }
            }
            if (index < news.size - 1) {
                Box(
                    modifier = modifier
                        .clip(RoundedCornerShape(topStart = 27.dp, topEnd = 27.dp))
                        .background(WhiteSmoke)
                        .fillMaxSize()
                ) {
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

        }
    }
}

@Composable
private fun FirstArticleItem(
    news: News,
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    ),
    userSession: AuthN
) {
    var isBookmarked = news.isBookmarked
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = isBookmarked){
        viewModel.fetchNews(userSession.token)
    }

    Column(
        modifier = modifier.padding(8.dp),
    ) {
        AsyncImage(
            model = news.imageUrl,
            contentDescription = null,
            modifier = modifier
                .clip(RoundedCornerShape(24.dp))
                .width(380.dp)
                .height(248.dp),
            contentScale = ContentScale.Crop
        )

        Column(Modifier.fillMaxWidth()) {
            Text(
                text = news.author,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Box(Modifier.fillMaxWidth()) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    maxLines = 3,
                    modifier = Modifier.padding(bottom = 14.dp)
                )
                Row(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(top = 4.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (isBookmarked){
                                viewModel.deleteBookmarkNew(userSession.token, news.id)
                            } else {
                                viewModel.addBookmarkNew(userSession.token, news.id)
                            }
                            isBookmarked = !isBookmarked
                        },
                        modifier = modifier.padding(end = 6.dp)
                    ) {
                        if (isBookmarked) {
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
                    }
                    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = "Bookmark",
                                tint = Color.DarkGray,
                            )
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(
                                text = { Text("Bagikan Berita") },
                                onClick = {
                                    val intent = Intent()
                                    intent.action = Intent.ACTION_SEND
                                    intent.putExtra(
                                        Intent.EXTRA_TEXT,
                                        "${news.title}\n${news.url}\nBaca berita terpecaya di Horizon"
                                    )
                                    intent.type = "text/plain"
                                    startActivity(
                                        context,
                                        Intent.createChooser(intent, "Share di:"),
                                        null
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Share,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun RegularArticleItem(
    news: News,
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    ),
    userSession: AuthN
) {
    var isBookmarked = news.isBookmarked
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(isBookmarked){
//        viewModel.fetchNews(userSession.token)
    }

    Row(
        modifier = modifier.padding(12.dp, 12.dp, 12.dp, 4.dp),

        ) {
        AsyncImage(
            model = news.imageUrl,
            contentDescription = null,
            modifier = modifier
                .width(116.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.End,
                maxLines = 2,
            )
            Text(
                text = news.author,
                style = MaterialTheme.typography.bodySmall,
                color = GreyDark,
                modifier = modifier.padding(end = 2.dp)
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        if (isBookmarked){
                            viewModel.deleteBookmarkNew(userSession.token, news.id)
                        } else {
                            viewModel.addBookmarkNew(userSession.token, news.id)
                        }
                        isBookmarked = !isBookmarked
                    },
                    modifier = modifier
                ) {
                    if (isBookmarked) {
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
                }
                Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "Bookmark",
                            tint = Color.DarkGray,
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Bagikan Berita") },
                            onClick = {
                                val intent = Intent()
                                intent.action = Intent.ACTION_SEND
                                intent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    "${news.title}\n${news.url}\nBaca berita terpecaya di Horizon"
                                )
                                intent.type = "text/plain"
                                startActivity(
                                    context,
                                    Intent.createChooser(intent, "Share di:"),
                                    null
                                )
                            },
                            leadingIcon = { Icon(Icons.Outlined.Share, contentDescription = null) }
                        )
                    }
                }
            }

        }
    }
}


