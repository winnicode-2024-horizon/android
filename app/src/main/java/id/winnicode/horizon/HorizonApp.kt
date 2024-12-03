package id.winnicode.horizon

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
import id.winnicode.horizon.data.remote.response.CommentItem
import id.winnicode.horizon.factory.ViewModelFactory
import id.winnicode.horizon.model.CommentRequest
import id.winnicode.horizon.ui.common.SharedViewModel
import id.winnicode.horizon.ui.common.UiState
import id.winnicode.horizon.ui.navigation.NavigationItem
import id.winnicode.horizon.ui.navigation.Screen
import id.winnicode.horizon.ui.screen.bookmark.BookmarkScreen
import id.winnicode.horizon.ui.screen.detail.DetailScreen
import id.winnicode.horizon.ui.screen.home.CustomSearchView
import id.winnicode.horizon.ui.screen.home.HomeScreen
import id.winnicode.horizon.ui.screen.login.LoginScreen
import id.winnicode.horizon.ui.screen.profile.ProfileScreen
import id.winnicode.horizon.ui.screen.register.RegisterScreen
import id.winnicode.horizon.ui.theme.GreyDark
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
    val userProfile = viewModel.profileUiState.collectAsState().value
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }
    var isLogoutDialog by remember { mutableStateOf(false) }
    var isLogout by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val isExpiredSession = remember { mutableStateOf(false) }
    var commentSize by remember { mutableStateOf(0) }
    val newId = remember { mutableStateOf(-1) }
    val isLogin by rememberUpdatedState(newValue = userSession)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sheetState = rememberBottomSheetScaffoldState(
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
    )
    val detailScrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val detailMaxScroll by remember {
        derivedStateOf {
            mutableStateOf(detailScrollState.value <= 2)
        }
    }


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
                        title = {
                            Text(
                                text = stringResource(id = R.string.menu_profile),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        navigationIcon = {

                        },
                        actions = {
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
                BottomBar(navController = navController, currentRoute = currentRoute)
        },
        floatingActionButton = {
            when (currentRoute) {
                Screen.DetailNew.route -> {
                    if (detailMaxScroll.value) {
                        AnimatedVisibility(
                            modifier = Modifier,
                            visible = detailMaxScroll.value,
                            enter = slideInHorizontally(
                                initialOffsetX = { it * 2 },
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = FastOutSlowInEasing
                                ) // Durasi dan easing untuk masuk
                            ),
                            exit = slideOutHorizontally(
                                targetOffsetX = { it * 2 },
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = FastOutSlowInEasing
                                ) // Durasi dan easing untuk keluar
                            ),
                        ) {
                            ExtendedFloatingActionButton(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.primary,
                                text = { Text(text = commentSize.toString()) },
                                onClick = {
                                    showBottomSheet = true
                                    scope.launch {
                                        sheetState.bottomSheetState.expand()
                                        detailScrollState.scrollTo(detailScrollState.maxValue)
                                    }
                                },
                                icon = { Icon(Icons.Filled.Comment, "Add Payment") }
                            )
                        }
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) { innerPadding ->
        ModalNavigationDrawer(
            modifier = Modifier.padding(innerPadding),
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = RectangleShape,
                ) {
                    LaunchedEffect(key1 = userSession.value.isLogin) {
                        if (userSession.value.isLogin) {
                            viewModel.fetchUserProfile(userSession.value.token)
                            isExpiredSession.value =
                                expiredSessionCountdown(userSession.value.expiredAt)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = modifier
                                .padding(4.dp)
                                .size(65.dp)
                        )
                        Column {
                            Text(
                                text = "${userProfile.firstName} " + userProfile.lastName,
                                style = MaterialTheme.typography.headlineSmall,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                            )
                            Text(
                                text = userProfile.email,
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                                modifier = modifier
                            )
                        }
                    }


                    Divider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                            })
                            .padding(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 12.dp),
                        )
                        Text(
                            text = "Setting",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { isLogoutDialog = true })
                            .padding(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
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
            if (isLogoutDialog) {
                MessageDialog(
                    logoutDialog = { isLogoutDialog = it },
                    color = MaterialTheme.colorScheme.error,
                    message = stringResource(id = R.string.log_out_message),
                    logout = {
                        scope.launch {
                            drawerState.close()
                        }
                        isLogout = true
                        viewModel.deleteSession()
                        viewModel.logout(userSession.value.token)
                    })
            }
            if (isLogout) {
                viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            Box(
                                modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
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
                            navController.navigate(Screen.Login.route)
                            isLogout = false
                        }

                        is UiState.Error -> {
                            val errorMessage = uiState.errorMessage
                            Toast.makeText(
                                context,
                                errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(Screen.Login.route)
                            isLogout = false
                        }
                    }
                }
            }
            if (isExpiredSession.value && userSession.value.expiredAt.isNotEmpty()) {
                MessageDialog(
                    logoutDialog = { isExpiredSession.value = it },
                    color = MaterialTheme.colorScheme.error,
                    message = "Sesi Login Anda sudah habis",
                    logout = {
                        scope.launch {
                            drawerState.close()
                        }
                        isLogout = true
                        viewModel.deleteSession()
                        viewModel.logout(userSession.value.token)
                    })
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .imePadding(),
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState.bottomSheetState
                ) {
                    LaunchedEffect(key1 = newId.value) {
                        val token = userSession.value.token
                        if (token.isNotEmpty() && newId.value != -1) {
                            viewModel.fetchNewsById(token, newId.value)
                        }
                    }
                    Box(
                        modifier = Modifier
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .align(Alignment.TopCenter)
                        ) {
                            Text(
                                text = stringResource(R.string.comment),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                            )
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            viewModel.newsUiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                                when (uiState) {
                                    is UiState.Loading -> {
                                        Box(
                                            modifier.fillMaxSize(0.9f),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }

                                    is UiState.Success -> {
                                        if (uiState.data.comments.isNotEmpty()) {
                                            BottomSheetComment(
                                                comments = uiState.data.comments,
                                                sheetState = sheetState.bottomSheetState
                                            )
                                        } else {
                                            Box(
                                                modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(
                                                        text = stringResource(id = R.string.empty_commment),
                                                        color = GreyDark,
                                                        style = MaterialTheme.typography.headlineSmall,
                                                        textAlign = TextAlign.Center
                                                    )
                                                    Text(
                                                        text = stringResource(id = R.string.empty_commment_message),
                                                        color = GreyDark,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    is UiState.Error -> {
                                        Box(
                                            modifier.fillMaxSize(0.9f),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = stringResource(R.string.error_home_screen),
                                                color = GreyDark
                                            )
                                        }
                                    }
                                }
                            }

                        }

                        var commentValue by remember {
                            mutableStateOf("")
                        }
                        Row(
                            modifier = Modifier
                                .shadow(16.dp, RectangleShape)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(color = Color.White),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(32.dp)
                            )
                            CustomOutlinedTextField(
                                value = commentValue,
                                onValueChange = { commentValue = it },
                                placeholder = {
                                    Text(text = stringResource(R.string.add_comment))
                                },
                                modifier = Modifier
                                    .padding(10.dp)
                                    .navigationBarsPadding()
                                    .imePadding()
                                    .weight(1f),
                                isError = false
                            )
                            IconButton(
                                onClick = {
                                    viewModel.commentNew(
                                        token = userSession.value.token,
                                        newsId = newId.value,
                                        request = CommentRequest(
                                            commentValue
                                        )
                                    )
                                    val token = userSession.value.token
                                    if (token.isNotEmpty() && newId.value != -1) {
                                        viewModel.fetchNewsById(token, newId.value)
                                    }
                                    commentValue = ""
                                },
                                enabled = commentValue.isNotEmpty(),
                                colors = IconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    disabledContainerColor = Color.White,
                                    disabledContentColor = GreyDark
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Send Comment",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                    }

                }
            }
            NavHost(
                navController = navController,
                startDestination =
                if (isLogin.value.isLogin) {
                    Screen.Home.route
                } else {
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
                        navigateToRegister = { navController.navigate(Screen.Register.route) }
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
                        HomeScreen(
                            query = searchQuery,
                            navigateToDetail = { title ->
                                navController.navigate(Screen.DetailNew.createRoute(title))
                            })
                    }
                }
                composable(
                    route = Screen.DetailNew.route,
                    arguments = listOf(navArgument("title") { type = NavType.IntType }),
                ) {
                    val newsId = it.arguments?.getInt("title") ?: -1
                    newId.value = newsId
                    DetailScreen(
                        NewsId = newsId,
                        detailScrollState = detailScrollState,
                        commentSize = { size ->
                            commentSize = size
                        },
                        showBottomSheet = { value ->
                            showBottomSheet = value
                        })
                }
                composable(Screen.Bookmark.route) {
                    BookmarkScreen(navigateToDetail = { title ->
                        navController.navigate(Screen.DetailNew.createRoute(title))
                    })
                }
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
            }
        }
        BackHandler(drawerState.isOpen) {
            scope.launch {
                drawerState.close()
            }
        }

        BackHandler(sheetState.bottomSheetState.isVisible) {
            scope.launch {
                sheetState.bottomSheetState.hide()
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    currentRoute: String?
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

        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.screen.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BottomSheetComment(
    comments: List<CommentItem>,
    sheetState: SheetState
) {
    LazyColumn(
        modifier = Modifier
            .imeNestedScroll()
    ) {
        itemsIndexed(comments) { index, comments ->
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(30.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "@${comments.user.username}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )
                    Text(
                        text = comments.comment,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDialog(
    modifier: Modifier = Modifier,
    logoutDialog: (Boolean) -> Unit,
    color: Color,
    message: String,
    logout: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { }
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
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(8.dp))
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
                        onClick = {
                            logoutDialog(false)
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    TextButton(
                        onClick = {
                            logoutDialog(false)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = Int.MAX_VALUE,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp)) // Rounded corners like OutlinedTextField
            .border(
                width = 1.dp,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp) // Padding inside the border
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = maxLines,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    placeholder = placeholder,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(0.dp),
                ) {

                }
            },
            modifier = Modifier.fillMaxWidth() // Fill the available width inside the box
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun expiredSessionCountdown(expiredAt: String): Boolean {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val expirationDate = ZonedDateTime.parse(expiredAt, formatter)
    val currentTime = ZonedDateTime.now(ZoneOffset.UTC)
    return currentTime.isAfter(expirationDate)
}
