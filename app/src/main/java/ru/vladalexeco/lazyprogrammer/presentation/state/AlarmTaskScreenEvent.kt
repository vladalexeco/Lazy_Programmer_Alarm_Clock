package ru.vladalexeco.lazyprogrammer.presentation.state

sealed interface AlarmTaskScreenEvent {
    data class ProcessUserTaskResponse(val isCorrectAnswer: Boolean) : AlarmTaskScreenEvent
    data object RenderNewTaskOrCompleteSession : AlarmTaskScreenEvent
    data object ProcessWithEmergencyCompletionWithTask : AlarmTaskScreenEvent
}