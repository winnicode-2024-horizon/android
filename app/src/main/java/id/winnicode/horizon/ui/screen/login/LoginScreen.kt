package id.winnicode.horizon.ui.screen.login

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.winnicode.horizon.R
import id.winnicode.horizon.ui.theme.Black
import id.winnicode.horizon.ui.theme.GreyDark
import id.winnicode.horizon.ui.theme.GreyLight
import id.winnicode.horizon.ui.theme.GreyLowDark
import id.winnicode.horizon.ui.theme.HorizonTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen (
    modifier: Modifier = Modifier,
){
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val emailIsError = rememberSaveable{ mutableStateOf(true) }
    val passwordIsError = rememberSaveable { mutableStateOf(true) }
    val loginDialog = remember { mutableStateOf(false) }

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
                        Text(
                            text = "Anda Berhasil Login dengan email ${email.value}. " +
                                    "Meyiapkan berita untuk Anda....",
                        )
                        Spacer(modifier = modifier.height(24.dp))
                        TextButton(
                            onClick = { loginDialog.value = false },
                            modifier = modifier.align(Alignment.End)
                        ) {
                            Text("Lanjut", color = Black)
                        }
                    }
                }
            }
    }

    LoginContent(
        email = email,
        password = password,
        passwordIsError = passwordIsError,
        emailIsError = emailIsError,
        loginDialog = loginDialog,
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    password : MutableState<String>,
    emailIsError: MutableState<Boolean>,
    passwordIsError: MutableState<Boolean>,
    loginDialog: MutableState<Boolean>
){

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        val emailPattern = email.value.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
        val passwordPattern = password.value.length > 7

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
            label = { Text(text = "Email") },
            value = email.value,
            colors = outlinedTextFieldColors,
            onValueChange = {
                email.value = it
                emailIsError.value = emailPattern },
            isError = !emailIsError.value,
            maxLines = 1,
            modifier = modifier
                .width(377.dp)
        )

        Spacer(modifier = modifier.height(16.dp))
        OutlinedTextField(
            label = { Text(text = "Password") },
            value = password.value,
            colors = outlinedTextFieldColors,
            visualTransformation =
            if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            onValueChange = { password.value = it
                            passwordIsError.value = passwordPattern},
            isError = !passwordIsError.value,
            maxLines = 1,
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
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
                onClick = { loginDialog.value = true },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Black,
                    contentColor = Color.White,
                    disabledContainerColor = GreyDark,
                    disabledContentColor = Color.White
                ),
                enabled = emailPattern && passwordPattern && passwordIsError.value,
                modifier = modifier
                    .height(50.dp)
                    .width(377.dp)
            ) {
                Text(text = "Login")
            }
        }
        Spacer(modifier = modifier.height(6.dp))
        Row (modifier = modifier.padding(24.dp, 0.dp, 24.dp, 0.dp)){

            HorizontalDivider(
                color = Black,
                modifier = modifier
                    .weight(2f)
                    .height(1.dp)
                    .align(Alignment.CenterVertically)

            )
            Text(text = "OR", modifier
                .weight(1f),
                textAlign = TextAlign.Center)
            HorizontalDivider(
                color = Black,
                modifier = modifier
                    .weight(2f)
                    .height(1.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = modifier.height(16.dp))
            ElevatedButton(
                onClick = { },
                shape = RoundedCornerShape(10.dp),
                modifier = modifier
                    .height(50.dp)
                    .width(377.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Black
                )
            ) {
                Icon(
                    painterResource(id = R.drawable.google),
                    contentDescription = "google",
                    modifier.size(ButtonDefaults.IconSize),
                    tint = Color.Black
                )
                Spacer(modifier = modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = "Login With Google",
                    color = Color.Gray
                )
        }

        Spacer(modifier = modifier.height(20.dp))
    }


}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    HorizonTheme {
        LoginScreen()
    }
}



private val outlinedTextFieldColors: TextFieldColors
    @Composable
    get() = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = GreyLight,
        unfocusedContainerColor = GreyLight,
        cursorColor = GreyDark,
        focusedLabelColor = GreyDark,
        unfocusedLabelColor = GreyDark,
        focusedTrailingIconColor = GreyDark,
        unfocusedTrailingIconColor = GreyDark,
        focusedBorderColor = GreyLowDark,
        unfocusedBorderColor = GreyLowDark,
    )