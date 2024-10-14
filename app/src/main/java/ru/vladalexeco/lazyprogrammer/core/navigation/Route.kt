package ru.vladalexeco.lazyprogrammer.core.navigation

interface LazyProgrammerDestination {
    val route: String
}

object Alarms: LazyProgrammerDestination {
    override val route = "Alarms"
}

object Task: LazyProgrammerDestination {
    override val route = "Task"
}

object Results: LazyProgrammerDestination {
    override val route = "Results"
}

object Settings: LazyProgrammerDestination {
    override val route = "Settings"
}