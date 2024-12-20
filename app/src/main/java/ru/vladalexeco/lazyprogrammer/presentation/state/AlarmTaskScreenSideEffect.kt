package ru.vladalexeco.lazyprogrammer.presentation.state

sealed interface AlarmTaskScreenSideEffect {
    data object GoToAnotherScreen : AlarmTaskScreenSideEffect
}