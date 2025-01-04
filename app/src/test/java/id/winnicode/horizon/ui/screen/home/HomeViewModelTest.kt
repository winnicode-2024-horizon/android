package id.winnicode.horizon.ui.screen.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.winnicode.horizon.DataDummy
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.AuthN
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
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var userRepository: UserRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {

        Dispatchers.setMain(testDispatcher)
        // Initialize mocks
        userPreferences = mock()
        userRepository = mock()

        // Mock userPreferences and userRepository as needed
        whenever(userPreferences.getUserSession()).thenReturn(flowOf(AuthN("token", "username", true)))

        // Initialize ViewModel
        homeViewModel = HomeViewModel(userPreferences, userRepository)
    }

    // Test for fetchNews success
    @Test
    fun `test fetchNews success`() = runTest {
        // Arrange: Menggunakan data dummy dari DataDummy
        val newsResponse = DataDummy.generateDummyNewsResponse()
        whenever(userRepository.fetchNews("token")).thenReturn(flowOf(newsResponse))

        // Act
        homeViewModel.fetchNews("token")

        testDispatcher.scheduler.advanceUntilIdle()

        assert(homeViewModel.uiState.value is UiState.Success)
        val result = (homeViewModel.uiState.value as UiState.Success).data
        assert(result == newsResponse)
    }

    // Test for fetchNews error
//    @Test
//    fun `test fetchNews error`() = runTest {
//        // Arrange
//        val exception = HttpException(Response.error<ResponseBody>(401 ,ResponseBody.create(
//            "plain/text".toMediaTypeOrNull(),"Unauthorized"))) // Create a mock HttpException
//        `when`(userRepository.fetchNews("token")).thenReturn(flowOf(exception))
//
//        // Act
//        homeViewModel.fetchNews("token")
//
//        testDispatcher.scheduler.advanceUntilIdle()
//        println(homeViewModel.uiState.value)
//        // Assert
//        assert(homeViewModel.uiState.value is UiState.Error)
//        val errorMessage = (homeViewModel.uiState.value as UiState.Error).errorMessage
//        assert(errorMessage.contains("Unauthorized"))
//
//    }

    @Test
    fun `test fetchNews data empty`() = runTest {
        val emptyNewsResponse = emptyList<News>()

        whenever(userRepository.fetchNews("token")).thenReturn(flowOf(emptyNewsResponse))

        // Act
        homeViewModel.fetchNews("token")

        testDispatcher.scheduler.advanceUntilIdle()
        // Assert
        assert(homeViewModel.uiState.value is UiState.Success)
        val result = (homeViewModel.uiState.value as UiState.Success).data
        assert(result.isEmpty())

    }

    // Test for addBookmarkNew success
    @Test
    fun `test addBookmarkNew success`() = runTest {
        // Arrange
        val response = RegisterResponse("success", "Bookmarked successfully")
        `when`(userRepository.addBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        homeViewModel.addBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(homeViewModel.bookmarkState.value is UiState.Success)
        val result = (homeViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
    }

    @Test
    fun `test addBookmarkNew data empty`() = runTest {
        // Arrange
        val response = RegisterResponse("", "")
        `when`(userRepository.addBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        homeViewModel.addBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(homeViewModel.bookmarkState.value is UiState.Success)
        val result = (homeViewModel.bookmarkState.value as UiState.Success).data
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
        homeViewModel.deleteBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(homeViewModel.bookmarkState.value is UiState.Success)
        val result = (homeViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
    }

    // Test for deleteBookmarkNew success
    @Test
    fun `test deleteBookmarkNew data empty`() = runTest {
        // Arrange
        val response = RegisterResponse("", "")
        `when`(userRepository.deleteBookmark("token", 1)).thenReturn(flowOf(response))

        // Act
        homeViewModel.deleteBookmarkNew("token", 1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assert(homeViewModel.bookmarkState.value is UiState.Success)
        val result = (homeViewModel.bookmarkState.value as UiState.Success).data
        assert(result == response)
        result.message?.let { assert(it.isEmpty()) }
    }

}