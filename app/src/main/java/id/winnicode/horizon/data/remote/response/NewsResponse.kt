package id.winnicode.horizon.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("data")
	val data: NewsData,

	@field:SerializedName("status")
	val status: String
)

data class NewsData(

	@field:SerializedName("news")
	val news: List<NewsItem>,

	@field:SerializedName("size")
	val size: Int
)

data class NewsItem(

	@field:SerializedName("publishedAt")
	val publishedAt: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("content")
	val content: String
)
