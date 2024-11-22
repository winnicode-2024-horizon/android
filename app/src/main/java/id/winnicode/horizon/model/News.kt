package id.winnicode.horizon.model

data class News(
    val publishedAt: String,
    val author: String,
    val imageUrl: String,
    val description: String,
    val id: Int,
    val title: String,
    val url: String,
    val content: String
)

data class SearchSuggestion(
    val keyword: String
)
