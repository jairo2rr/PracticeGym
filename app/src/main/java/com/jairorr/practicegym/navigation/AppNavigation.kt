package com.jairorr.practicegym.navigation

enum class Screen{
    HOME,
    SECONDARY,
    CARD_TRANSITION
}

sealed class NavigationItem(val route:String){
    object Home: NavigationItem(Screen.HOME.name)
    object Secondary: NavigationItem(Screen.SECONDARY.name)
    object CardTransition:NavigationItem(Screen.CARD_TRANSITION.name)
}