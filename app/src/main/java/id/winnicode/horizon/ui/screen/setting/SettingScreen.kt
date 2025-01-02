package id.winnicode.horizon.ui.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.winnicode.horizon.MainApplication
import id.winnicode.horizon.factory.ViewModelFactory
import id.winnicode.horizon.ui.theme.HorizonTheme

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    ),
){
    var switchTheme by remember { mutableStateOf(true) }
    viewModel.userSession.collectAsState().value.let { isDarkModeActive ->

    }
    if (switchTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {

                })
                .padding(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
            )
            Text(
                text = "Dark Mode",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .weight(5f)
                    .padding(4.dp)
            )

            Switch(
                checked = switchTheme,
                onCheckedChange = {
                    switchTheme = it
                    viewModel.saveThemeSetting(it)

                },
                Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { })
                .padding(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Password,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .weight(1f),
            )
            Text(
                text = "Change Password",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.weight(6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    HorizonTheme {
        SettingScreen()
    }
}