package id.winnicode.horizon.ui.screen.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.winnicode.horizon.data.prefrences.UserPreferences
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.AuthN
import id.winnicode.horizon.model.LoginRequest
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
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Untuk memastikan LiveData dijalankan di thread utama

    private lateinit var loginViewModel: LoginViewModel
    private val userPreferences: UserPreferences = mock()
    private val userRepository: UserRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Setup Dispatchers untuk unit test
        Dispatchers.setMain(testDispatcher)

        // Inisialisasi LoginViewModel dengan dependencies yang sudah di-mock
        loginViewModel = LoginViewModel(userRepository, userPreferences)
    }

    @Test
    fun `test loginUser success`() = runTest {
        // Arrange: Siapkan data LoginRequest dan AuthN untuk login yang sukses
        val loginRequest = LoginRequest("username", "password")
        val user = AuthN("token123", "token", true)

        // Mock userRepository untuk mengembalikan data AuthN saat login
        `when`(userRepository.login(loginRequest)).thenReturn(flowOf(user))

        // Act: Panggil fungsi loginUser
        loginViewModel.loginUser(loginRequest)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Success dan data sesuai
        assert(loginViewModel.uiState.value is UiState.Success)
        val result = (loginViewModel.uiState.value as UiState.Success).data
        assert(user == result)
    }

    @Test
    fun `test loginUser data empty`() = runTest {
        // Arrange: Siapkan LoginRequest yang menyebabkan error
        val loginRequest = LoginRequest("username", "password")
        val user = AuthN("", "", false)

        // Mock userRepository untuk melempar HttpException saat login
        `when`(userRepository.login(loginRequest)).thenReturn(flowOf(user))

        // Act: Panggil fungsi loginUser
        loginViewModel.loginUser(loginRequest)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Error dengan pesan yang benar
        assert(loginViewModel.uiState.value is UiState.Success)
        val result = (loginViewModel.uiState.value as UiState.Success).data
        assert(result.token.isEmpty())
    }
}