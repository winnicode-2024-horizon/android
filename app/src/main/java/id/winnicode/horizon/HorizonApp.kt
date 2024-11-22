package id.winnicode.horizon

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import id.winnicode.horizon.ui.navigation.NavigationItem
import id.winnicode.horizon.ui.navigation.Screen
import id.winnicode.horizon.ui.screen.home.CustomSearchView
import id.winnicode.horizon.ui.screen.home.HomeScreen
import id.winnicode.horizon.ui.screen.login.LoginScreen
import id.winnicode.horizon.ui.screen.register.RegisterScreen

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
    val isLogin by rememberUpdatedState(newValue = userSession)

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
                                IconButton(onClick = { }) {
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
                            IconButton(onClick = { /* Handle menu icon click */ }) {
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
        NavHost(
            navController = navController,
            startDestination =
                if (isLogin.value.isLogin){
                    Screen.Home.route
                }
                else {
                    Screen.Login.route
                },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Register.route) {
                RegisterScreen(navigateToLogin = { navController.navigate(Screen.Login.route) })
            }
            composable(Screen.Login.route) {
                LoginScreen(navigateToHome = { navController.navigate(Screen.Home.route) })
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
                        })
                }
            }
            composable(
                route = Screen.DetailNew.route,
                arguments = listOf(navArgument("title") { type = NavType.StringType }),
            ) {
                val title = it.arguments?.getString("title") ?: ""
            }
            composable(Screen.Bookmark.route) {
            }
            composable(Screen.Profile.route) {
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