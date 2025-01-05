package id.winnicode.horizon.ui.screen.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.winnicode.horizon.data.remote.response.RegisterResponse
import id.winnicode.horizon.data.repository.UserRepository
import id.winnicode.horizon.model.RegisterRequest
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
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Untuk memastikan LiveData dijalankan di thread utama

    private lateinit var registerViewModel: RegisterViewModel
    private val userRepository: UserRepository = mock()

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        // Setup Dispatchers untuk unit test
        Dispatchers.setMain(testDispatcher)

        // Inisialisasi RegisterViewModel dengan mock UserRepository
        registerViewModel = RegisterViewModel(userRepository)
    }

    @Test
    fun `test registerUser success`() = runTest {
        // Arrange: Siapkan data RegisterRequest dan RegisterResponse untuk pendaftaran yang berhasil
        val registerRequest = RegisterRequest("username", "Jhon","Doe", "email@example.com", "rahasia123")
        val registerResponse = RegisterResponse("success", "Registration successful")

        // Mock userRepository untuk mengembalikan RegisterResponse saat register
        `when`(userRepository.register(registerRequest)).thenReturn(flowOf(registerResponse))

        // Act: Panggil fungsi registerUser
        registerViewModel.registerUser(registerRequest)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Success dan data sesuai
        assert(registerViewModel.uiState.value is UiState.Success)
        val result = (registerViewModel.uiState.value as UiState.Success).data
        assert(registerResponse == result)
    }

    @Test
    fun `test registerUser error`() = runTest {
        // Arrange: Siapkan data RegisterRequest dan RegisterResponse untuk pendaftaran yang berhasil
        val registerRequest = RegisterRequest("username", "Jhon","Doe", "email@example.com", "rahasia123")
        val registerResponse = RegisterResponse("error", "Unknown error")

        // Mock userRepository untuk mengembalikan RegisterResponse saat register
        `when`(userRepository.register(registerRequest)).thenReturn(flowOf(registerResponse))

        // Act: Panggil fungsi registerUser
        registerViewModel.registerUser(registerRequest)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: Pastikan uiState menjadi Success dan data sesuai
        println(registerViewModel.uiState.value)
        assert(registerViewModel.uiState.value is UiState.Error)
        val errorMessage = (registerViewModel.uiState.value as UiState.Error).errorMessage
        assert(errorMessage.contains("Unknown error"))

    }
}