package id.winnicode.horizon.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.winnicode.horizon.MainApplication
import id.winnicode.horizon.R
import id.winnicode.horizon.factory.ViewModelFactory
import id.winnicode.horizon.model.LoginRequest
import id.winnicode.horizon.ui.common.UiState
import id.winnicode.horizon.ui.pattern.Pattern
import id.winnicode.horizon.ui.theme.Black
import id.winnicode.horizon.ui.theme.GreyDark
import id.winnicode.horizon.ui.theme.HorizonTheme
import id.winnicode.horizon.ui.theme.outlinedTextFieldColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen (
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    ),
    navigateToHome: () -> Unit
){
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val emailIsError = rememberSaveable{ mutableStateOf(true) }
    val passwordIsError = rememberSaveable { mutableStateOf(true) }
    val loginDialog = remember { mutableStateOf(false) }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    val uiState = viewModel.uiState.collectAsState(initial = UiState.Loading)
    val userSession = viewModel.userSession.collectAsState()

    val onLoginClick = {
        loginDialog.value = true
        viewModel.loginUser(
            LoginRequest(
                email = email.value,
                password = password.value
            )
        )
    }

    uiState.value.let { uiStateValue ->
        when (uiStateValue) {

            is UiState.Success -> {
                viewModel.saveUserSession(
                    uiStateValue.data
                )
                if (loginDialog.value) {
                    BasicAlertDialog(onDismissRequest = { }
                    ) {
                        Surface(
                            modifier = modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            shape = MaterialTheme.shapes.large,
                            tonalElevation = AlertDialogDefaults.TonalElevation
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector =  Icons.Filled.CheckCircle, contentDescription = null )
                                    Spacer(modifier = Modifier.width(8.dp)) // Spasi antara ikon dan judul
                                    Text(text = stringResource(R.string.login_success_title), style = MaterialTheme.typography.headlineSmall)
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text =
//                                    stringResource(
//                                        id = R.string.registration_success,
//                                        email.value),
                                    userSession.value.token,
                                )
                                Spacer(modifier = modifier.height(24.dp))
                                TextButton(
                                    onClick = { loginDialog.value = false
                                              navigateToHome()},
                                    modifier = modifier.align(Alignment.End)
                                ) {
                                    Text(stringResource(R.string.next), color = Black)
                                }
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                val errorMessage = uiStateValue.errorMessage
                if (loginDialog.value){
                    BasicAlertDialog(
                        onDismissRequest = {  }
                    ) {
                        Surface(
                            modifier = modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            shape = MaterialTheme.shapes.large,
                            tonalElevation = AlertDialogDefaults.TonalElevation
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector =  Icons.Filled.Dangerous,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error)

                                    Spacer(modifier = Modifier.width(8.dp)) // Spasi antara ikon dan judul
                                    Text(
                                        text = stringResource(R.string.login_fail_title),
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = errorMessage,
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = modifier.height(24.dp))
                                TextButton(
                                    onClick = { loginDialog.value = false },
                                    modifier = modifier.align(Alignment.End)
                                ) {
                                    Text(stringResource(R.string.next), color = Black)
                                }
                            }
                        }
                    }
                }

            }

            else -> {}
        }
    }

    LoginContent(
        email = email,
        password = password,
        passwordIsError = passwordIsError,
        emailIsError = emailIsError,
        passwordVisible = passwordVisible,
        loginDialog = loginDialog,
        onLoginClick = onLoginClick
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    password : MutableState<String>,
    emailIsError: MutableState<Boolean>,
    passwordIsError: MutableState<Boolean>,
    passwordVisible: MutableState<Boolean>,
    loginDialog: MutableState<Boolean>,
    onLoginClick: () -> Unit
){

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login",
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = modifier.height(20.dp))
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.email)) },
            value = email.value,
            colors = outlinedTextFieldColors(),
            onValueChange = {
                email.value = it
                emailIsError.value = Pattern.emailPattern(it) },
            isError = !emailIsError.value,
            supportingText = {
                if (!emailIsError.value){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.email_error_massage),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            maxLines = 1,
            modifier = modifier
                .width(377.dp)
        )
        Spacer(modifier = modifier.height(16.dp))
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.password)) },
            value = password.value,
            colors = outlinedTextFieldColors(),
            visualTransformation =
            if (passwordVisible.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            onValueChange = { password.value = it
                            passwordIsError.value = Pattern.passwordPattern(it)},
            isError = !passwordIsError.value,
            supportingText = {
                if (!emailIsError.value){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.password_error_message),
                        color = MaterialTheme.colorScheme.error

                    )
                }
            },
            maxLines = 1,
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible.value) "Hide password" else "Show password"
                IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                    Icon(imageVector  = image, description)
                }
            },
            modifier = modifier
                .width(377.dp)
        )
        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = { },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            ),
            modifier = modifier
                .align(Alignment.End)
                .padding(top = 8.dp, end = 24.dp)
        )
        Spacer(modifier = modifier.height(20.dp))
        Box(modifier = modifier.padding(20.dp)) {
            Button(
                onClick = { onLoginClick() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Black,
                    contentColor = Color.White,
                    disabledContainerColor = GreyDark,
                    disabledContentColor = Color.White
                ),
                enabled = emailIsError.value && email.value.isNotEmpty()
                        && passwordIsError.value && password.value.isNotEmpty(),
                modifier = modifier
                    .height(50.dp)
                    .width(377.dp)
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
        Spacer(modifier = modifier.height(20.dp))
    }


}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    HorizonTheme {
        LoginScreen(navigateToHome = {})
    }
}