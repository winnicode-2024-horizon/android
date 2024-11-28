package id.winnicode.horizon

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.winnicode.horizon.factory.ViewModelFactory
import id.winnicode.horizon.ui.common.SharedViewModel
import id.winnicode.horizon.ui.common.UiState
import id.winnicode.horizon.ui.navigation.NavigationItem
import id.winnicode.horizon.ui.navigation.Screen
import id.winnicode.horizon.ui.screen.bookmark.BookmarkScreen
import id.winnicode.horizon.ui.screen.detail.DetailScreen
import id.winnicode.horizon.ui.screen.home.CustomSearchView
import id.winnicode.horizon.ui.screen.home.HomeScreen
import id.winnicode.horizon.ui.screen.login.LoginScreen
import id.winnicode.horizon.ui.screen.register.RegisterScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizonApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: SharedViewModel = viewModel(
        factory = ViewModelFactory(MainApplication.injection)
    )
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val userSession = viewModel.userSession.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }
    var isLogoutDialog = remember { mutableStateOf(false) }
    var isLogout = remember { mutableStateOf(false) }
    val isLogin by rememberUpdatedState(newValue = userSession)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            when (currentRoute) {
                Screen.Home.route -> {
                    if (!isSearchVisible) {
                        TopAppBar(
                            title = {
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) {
                                            drawerState.open()
                                        } else {
                                            drawerState.close()
                                        }
                                    }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            },
                            actions = {
                                IconButton(onClick = { isSearchVisible = true }) {
                                    Icon(Icons.Default.Search, contentDescription = "Search")
                                }
                            },
                        )
                    }
                }

                Screen.Bookmark.route -> {
                    TopAppBar(
                        title = { /* Empty or customized title */ },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {},
                    )
                }

                Screen.Profile.route -> {
                    TopAppBar(
                        title = { /* Empty or customized title */ },
                        navigationIcon = {
                            IconButton(onClick = { /* Handle menu icon click */ }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* Handle search icon click */ }) {
                                Icon(Icons.Default.Settings, contentDescription = "Setting")
                            }
                        },
                    )
                }
            }
        },
        bottomBar = {
            if (currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Bookmark.route,
                    Screen.Profile.route
                )
            )
                BottomBar(navController)
        },

        ) { innerPadding ->
        ModalNavigationDrawer(
            modifier = Modifier.padding(innerPadding),
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = RectangleShape,
                ) {
                Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { /* Handle logout action */ })
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 12.dp),
                        )
                        Text(
                            text = "Setting",
                            style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { isLogoutDialog.value = true })
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.padding(end = 12.dp),
                        )
                        Text(
                            text = "Log Out",
                            style = TextStyle(fontSize = 18.sp, color = Color.Red)
                        )
                    }

                }
            }
        ) {
            if (isLogoutDialog.value){
                MessageDialog(
                    logoutDialog = isLogoutDialog,
                    color = MaterialTheme.colorScheme.error,
                    message = stringResource(id = R.string.log_out_message),
                    logout = {
                        scope.launch {
                            drawerState.close()
                        }
                        isLogout.value = true
                        viewModel.logout(userSession.value.token)
                    })
            }
            if (isLogout.value){
                viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            Box (modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center){
                                CircularProgressIndicator()
                            }
                        }

                        is UiState.Success -> {
                            val data = uiState.data
                            Toast.makeText(
                                context,
                                data.status,
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.deleteSession()
                            navController.navigate(Screen.Login.route)
                            isLogout.value = false
                        }

                        is UiState.Error -> {
                            val errorMessage = uiState.errorMessage
                            Toast.makeText(
                                context,
                                errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                            isLogout.value = false
                        }
                    }
                }
            }
            NavHost(
                navController = navController,
                startDestination =
                if (isLogin.value.isLogin){
                    Screen.Home.route
                }
                else {
                    Screen.Login.route
                },
                enterTransition = {
                    // Transisi masuk: geser ke kanan
                    slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
                },
                exitTransition = {
                    // Transisi keluar: geser ke kiri
                    slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
                },
                popEnterTransition = {
                    // Transisi masuk saat pop: geser ke kiri
                    slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
                },
                popExitTransition = {
                    // Transisi keluar saat pop: geser ke kanan
                    slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
                },
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Register.route) {
                    RegisterScreen(navigateToLogin = { navController.navigate(Screen.Login.route) })
                }
                composable(Screen.Login.route) {
                    LoginScreen(
                        navigateToHome = { navController.navigate(Screen.Home.route) },
                        navigateToRegister = {navController.navigate(Screen.Register.route)}
                    )
                }
                composable(Screen.Home.route) {
                    if (isSearchVisible) {
                        CustomSearchView(
                            isSearchVisible = isSearchVisible,
                            onSearchVisibilityChange = { isSearchVisible = it },
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it }
                        )
                    } else {
                        HomeScreen(query = searchQuery,
                            navigateToDetail = { title ->
                                navController.navigate(Screen.DetailNew.createRoute(title))
                            },
                            navController = navController)
                    }
                }
                composable(
                    route = Screen.DetailNew.route,
                    arguments = listOf(navArgument("title") { type = NavType.IntType }),
                ) {
                    val newsId = it.arguments?.getInt("title") ?: -1
                    DetailScreen(NewsId = newsId)
                }
                composable(Screen.Bookmark.route) {
                    BookmarkScreen(navigateToDetail = { title ->
                        navController.navigate(Screen.DetailNew.createRoute(title))
                    })
                }
                composable(Screen.Profile.route) {
                }
            }
        }

    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Outlined.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.bookmark),
                selectedIcon = Icons.Default.Bookmark,
                unselectedIcon = Icons.Outlined.BookmarkBorder,
                screen = Screen.Bookmark
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                selectedIcon = Icons.Default.AccountCircle,
                unselectedIcon = Icons.Outlined.AccountCircle,
                screen = Screen.Profile
            ),
        )
        val selectedItem = rememberSaveable { mutableStateOf(0) }

        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (index == selectedItem.value)
                            item.selectedIcon
                        else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = index == selectedItem.value,
                onClick = {
                    selectedItem.value = index
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDialog(
    modifier: Modifier = Modifier,
    logoutDialog: MutableState<Boolean>,
    color: Color,
    message: String,
    logout: () -> Unit
){
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
                        imageVector =  Icons.Default.Logout,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary)

                    Spacer(modifier = Modifier.width(8.dp)) // Spasi antara ikon dan judul
                    Text(
                        text = stringResource(R.string.log_out),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = { logoutDialog.value = false
                                  },
                    ) {
                        Text(text = stringResource(R.string.cancel), color = MaterialTheme.colorScheme.primary)
                    }
                    TextButton(
                        onClick = {
                            logoutDialog.value = false
                            logout()
                            },
                    ) {
                        Text(stringResource(R.string.log_out), color = color)
                    }
                }

            }
        }
    }
}