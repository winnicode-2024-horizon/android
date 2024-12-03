package id.winnicode.horizon.model

import id.winnicode.horizon.data.remote.response.CommentItem

data class News(
    val publishedAt: String,
    val author: String,
    val imageUrl: String,
    val description: String,
    val id: Int,
    val title: String,
    val url: String,
    val content: String,
    val comments: List<CommentItem>,
    var isBookmarked: Boolean = false
)

data class SearchSuggestion(
    val keyword: String
)
