package id.winnicode.horizon.ui.navigation

sealed class Screen(val route: String) {
    object Register:Screen("register")
    object Login : Screen("login")
    object Home : Screen("home")
    object DetailNew : Screen("home/{title}") {
        fun createRoute(title: String) = "home/$title"
    }
    object Bookmark : Screen("bookmark")
    object Profile : Screen("profile")
}