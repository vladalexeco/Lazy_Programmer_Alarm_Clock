package ru.vladalexeco.lazyprogrammer.presentation.state

sealed interface CreateTaskScreenSideEffect {

    data class ShowMessage(val message: String) : CreateTaskScreenSideEffect
}