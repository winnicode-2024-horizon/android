package id.winnicode.horizon.ui.screen.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.remote.response.CommentItem
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.remote.response.UserComment
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.News
import id.winnicode.horizon.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Untuk memastikan LiveData dijalankan di thread utama

    private lateinit var detailViewModel: DetailViewModel
    private val userPreferences: UserPreferences = mock()
    private val userRepository: UserRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Setup Dispatchers untuk unit test
        Dispatchers.setMain(testDispatcher)

        // Inisialisasi DetailViewModel dengan dependencies yang sudah di-mock
        detailViewModel = DetailViewModel(userPreferences, userRepository)
    }

    @Test
    fun `test fetchNewsById success`() = runTest {
        // Arrange: Siapkan response berita berdasarkan ID
        val new = News(
            publishedAt = "2024-11-05T08:00:00Z",
            author = "John Doe",
            imageUrl = "https://i.postimg.cc/MKLS8wxg/ezgif-5-0c331b9b43.png",
            description = "Berita terkini mengenai perkembangan ekonomi global.",
            id = 1,
            title = "Kesempatan Karir di Bidang Kecerdasan Buatan: Apa yang Perlu Dipersiapkan?",
            url = "https://example.com/news/1",
            content = "Ekonomi dunia diperkirakan tumbuh 3% pada tahun 2024, didorong oleh investasi teknologi dan infrastruktur.",
            category = "General",
            comments = listOf(
                CommentItem(
                    id = 1,
                    comment = "Good Information!",
                    user = UserComment(
                        username = "johnny",
                        firstName = "John",
                        lastName = "John Smith"
                    )
                )
            )
        )
        `when`(userRepository.fetchNewsById("token", 1)).thenReturn(flowOf(new))

        // Act: Panggil fungsi fetchNewsById
        detailViewModel.fetchNewsById("token", 1)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan newsUiState menjadi Success dengan data berita yang benar
        assert(detailViewModel.uiState.value is UiState.Success)
        val result = (detailViewModel.uiState.value as UiState.Success).data
        assert(new == result)
    }

    @Test
    fun `test fetchNewsById data empty`() = runTest {
        // Arrange: Simulasi error pada fetchNewsById
        val data =  News(
            publishedAt = "",
            author = "",
            imageUrl = "",
            description = "",
            id = -1,
            title = "",
            url = "",
            content = "",
            category = "",
            comments = listOf(
                CommentItem(
                    id = -1,
                    comment = "",
                    user = UserComment(
                        username = "",
                        firstName = "",
                        lastName = ""
                    )
                )
            )
        )
        `when`(userRepository.fetchNewsById("token", 1)).thenReturn(flowOf(data))

        // Act: Panggil fungsi fetchNewsById
        detailViewModel.fetchNewsById("token", 1)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan newsUiState menjadi Error dengan pesan yang benar
        println(detailViewModel.uiState.value)
        assert(detailViewModel.uiState.value is UiState.Success)
        val result = (detailViewModel.uiState.value as UiState.Success).data
        assert(result.publishedAt.isEmpty())
    }

    @Test
    fun `test addBookmarkNew success`() = runTest {
        // Arrange
        val response = RegisterResponse("success", "Bookmarked successfully")
        `when`(userRepository.addBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        detailViewModel.addBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(detailViewModel.bookmarkState.value is UiState.Success)
        val result = (detailViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
    }

    @Test
    fun `test addBookmarkNew data empty`() = runTest {
        // Arrange
        val response = RegisterResponse("", "")
        `when`(userRepository.addBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        detailViewModel.addBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(detailViewModel.bookmarkState.value is UiState.Success)
        val result = (detailViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
        result.message?.let { assert(it.isEmpty()) }
    }



    // Test for deleteBookmarkNew success
    @Test
    fun `test deleteBookmarkNew success`() = runTest {
        // Arrange
        val response = RegisterResponse("success", "Bookmark deleted successfully")
        `when`(userRepository.deleteBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        detailViewModel.deleteBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(detailViewModel.bookmarkState.value is UiState.Success)
        val result = (detailViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
    }

    // Test for deleteBookmarkNew success
    @Test
    fun `test deleteBookmarkNew data empty`() = runTest {
        // Arrange
        val response = RegisterResponse("", "")
        `when`(userRepository.deleteBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        detailViewModel.deleteBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(detailViewModel.bookmarkState.value is UiState.Success)
        val result = (detailViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
        result.message?.let { assert(it.isEmpty()) }
    }

}