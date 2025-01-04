package id.winnicode.horizon.ui.screen.bookmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.winnicode.horizon.DataDummy
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.remote.response.RegisterResponse
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
class BookmarkViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Untuk memastikan LiveData dijalankan di thread utama

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private val userPreferences: UserPreferences = mock()
    private val userRepository: UserRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Setup Dispatchers untuk unit test
        Dispatchers.setMain(testDispatcher)


        bookmarkViewModel = BookmarkViewModel(userPreferences, userRepository)
    }

    @Test
    fun `test fetchBookmarkNews success with data`() = runTest {
        // Arrange: Siapkan data response yang valid
        val newsList = DataDummy.generateDummyNewsResponse()
        `when`(userRepository.fetchBookmarkNews("token")).thenReturn(flowOf(newsList))

        // Act: Panggil fungsi fetchBookmarkNews
        bookmarkViewModel.fetchBookmarkNews("token")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Success dan data sesuai
        assert(bookmarkViewModel.uiState.value is UiState.Success)
        val result = (bookmarkViewModel.uiState.value as UiState.Success).data
        assert(newsList == result)
    }

    @Test
    fun `test fetchBookmarkNews success with empty data`() = runTest {
        // Arrange: Siapkan data kosong
        val newsList: List<News> = emptyList()
        `when`(userRepository.fetchBookmarkNews("token")).thenReturn(flowOf(newsList))

        // Act: Panggil fungsi fetchBookmarkNews
        bookmarkViewModel.fetchBookmarkNews("token")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Success dan data kosong
        assert(bookmarkViewModel.uiState.value is UiState.Success)
        val result = (bookmarkViewModel.uiState.value as UiState.Success).data
        assert(result.isEmpty())
    }

    @Test
    fun `test addBookmarkNew success`() = runTest {
        // Arrange: Siapkan response sukses
        val response = RegisterResponse("success", "Bookmarked successfully")
        `when`(userRepository.addBookmark("token", 1)).thenReturn(flowOf(response))

        // Act: Panggil fungsi addBookmarkNew
        bookmarkViewModel.addBookmarkNew("token", 1)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan bookmarkState menjadi Success dengan response yang benar
        assert(bookmarkViewModel.bookmarkState.value is UiState.Success)
        val result = (bookmarkViewModel.bookmarkState.value as UiState.Success).data
        assert(response == result)
    }

    @Test
    fun `test addBookmarkNew data empty`() = runTest {
        // Arrange
        val response = RegisterResponse("", "")
        `when`(userRepository.addBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        bookmarkViewModel.addBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(bookmarkViewModel.bookmarkState.value is UiState.Success)
        val result = (bookmarkViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
        result.message?.let { assert(it.isEmpty()) }
    }

    @Test
    fun `test deleteBookmarkNew success`() = runTest {
        // Arrange: Siapkan response sukses
        val response = RegisterResponse("success", "Bookmark deleted successfully")
        `when`(userRepository.deleteBookmark("token", 1)).thenReturn(flowOf(response))

        // Act: Panggil fungsi deleteBookmarkNew
        bookmarkViewModel.deleteBookmarkNew("token", 1)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan bookmarkState menjadi Success dengan response yang benar
        assert(bookmarkViewModel.bookmarkState.value is UiState.Success)
        val result = (bookmarkViewModel.bookmarkState.value as UiState.Success).data
        assert(response == result)
    }

    @Test
    fun `test deleteBookmarkNew data empty`() = runTest {
        // Arrange
        val response = RegisterResponse("", "")
        `when`(userRepository.deleteBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        bookmarkViewModel.deleteBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(bookmarkViewModel.bookmarkState.value is UiState.Success)
        val result = (bookmarkViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
        result.message?.let { assert(it.isEmpty()) }
    }

}