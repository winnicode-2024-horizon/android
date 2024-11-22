package id.winnicode.horizon.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.winnicode.horizon.R
import id.winnicode.horizon.model.NewsDummyData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchView(
    isSearchVisible: Boolean,
    onSearchVisibilityChange: (Boolean) -> Unit,
    searchQuery:String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val KeywordData = NewsDummyData.keyword

    val filteredSuggestions = KeywordData.filter {
        it.keyword.contains(searchQuery, ignoreCase = true)
    }

    SearchBar(
        query = searchQuery,
        onQueryChange = {
            onSearchQueryChange(it)
        },
        onSearch = { onSearchVisibilityChange(false) },
        active = isSearchVisible,
        onActiveChange = { onSearchQueryChange("")
            onSearchVisibilityChange(it) },
        placeholder = {
            Text(text = stringResource(R.string.search_new))
        },
        leadingIcon = {
            IconButton(onClick = { onSearchVisibilityChange(false)
            },
                modifier.padding(end = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button",
                )
            }
        },
        trailingIcon = {
            if (isSearchVisible){
                Icon(
                    modifier =
                    Modifier.clickable {
                        if (searchQuery.isNotEmpty()){
                            onSearchQueryChange("")
                        } else{
                            onSearchVisibilityChange(false)
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        }
    ) {
        if (searchQuery.isNotEmpty() && filteredSuggestions.isNotEmpty()) {
            LazyColumn {
                items(filteredSuggestions) { suggestion ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSearchQueryChange(suggestion.keyword)
                                onSearchVisibilityChange(false)
                            }
                            .padding(16.dp)
                            .height(IntrinsicSize.Min)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = 16.dp)
                        )
                        Text(
                            text = suggestion.keyword,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}