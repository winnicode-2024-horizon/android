package id.winnicode.horizon.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.winnicode.horizon.MainApplication
import id.winnicode.horizon.R
import id.winnicode.horizon.factory.ViewModelFactory
import id.winnicode.horizon.model.UserProfile
import id.winnicode.horizon.ui.common.UiState
import id.winnicode.horizon.ui.theme.GreyDark

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    )
){
    val userSession = viewModel.userSession.collectAsState().value

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LaunchedEffect(userSession.token) {
                    if (userSession.token.isNotEmpty()) {
                        viewModel.fetchUserProfile(userSession.token)
                    }
                }
                Box (modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                Column {
                    Spacer(modifier = modifier.height(4.dp))
                    ProfileContent(profile = uiState.data)
                }

            }

            is UiState.Error -> {
                Box (modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    Text(text = stringResource(R.string.error_profile_messages), color = GreyDark)
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(
    profile: UserProfile,
    modifier: Modifier = Modifier,

){
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(top = 35.dp, start = 16.dp)
        ){

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Color.Gray,
            modifier = modifier
                .padding(4.dp)
                .size(96.dp)
        )
        Column {
            Text(
                text = "${profile.firstName} " + profile.lastName,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(
                Modifier
            ) {
                Text(
                    text = "@${profile.username}",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = profile.email,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                )
            }
        }
    }

        Spacer(modifier = modifier.height(4.dp))
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
        )
        
    }

}