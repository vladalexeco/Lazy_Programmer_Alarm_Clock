package ru.vladalexeco.lazyprogrammer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.USER_ID
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.supportedProgrammingLanguages
import ru.vladalexeco.lazyprogrammer.core.util.util_functions.generateUniqueId
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import ru.vladalexeco.lazyprogrammer.domain.model.LanguageResult
import ru.vladalexeco.lazyprogrammer.domain.model.UserStatistics
import ru.vladalexeco.lazyprogrammer.domain.usecase.CancelAlarmUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.CreateWeeklyAlarmUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.DeleteAlarmFromDatabaseUseSase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetAllAlarmsFromDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetUserStatisticsByIdUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveLanguageResultToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveUserStatisticsToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmListScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmListScreenState
import javax.inject.Inject

@HiltViewModel
class AlarmListScreenViewModel @Inject constructor(
    private val saveAlarmToDatabaseUseCase: SaveAlarmToDatabaseUseCase,
    private val getAllAlarmsFromDatabaseUseCase: GetAllAlarmsFromDatabaseUseCase,
    private val deleteAlarmFromDatabaseUseSase: DeleteAlarmFromDatabaseUseSase,
    private val createWeeklyAlarmUseCase: CreateWeeklyAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase,
    private val getUserStatisticsByIdUseCase: GetUserStatisticsByIdUseCase,
    private val saveUserStatisticsToDatabaseUseCase: SaveUserStatisticsToDatabaseUseCase,
    private val saveLanguageResultToDatabaseUseCase: SaveLanguageResultToDatabaseUseCase
) : ViewModel() {

    init {
        getAlarmsFromDatabase()

        viewModelScope.launch(Dispatchers.IO) {

            val userStatistics = getUserStatisticsById(id = USER_ID)

            if (userStatistics == null) {

                saveUserStatisticsToDatabase(userStatistics = UserStatistics())

                saveAllSupportedLanguageResultsToDatabase(
                    supportedLanguageResults = supportedProgrammingLanguages
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(AlarmListScreenState())
    val uiState: StateFlow<AlarmListScreenState> = _uiState.asStateFlow()

    fun onEvent(alarmListScreenEvent: AlarmListScreenEvent) {

        when (alarmListScreenEvent) {

            is AlarmListScreenEvent.SaveAlarmEvent -> {
                viewModelScope.launch {
                    val saveJob = async { saveAlarmToDatabase(alarmListScreenEvent.alarm) }
                    saveJob.await()
                    getAlarmsFromDatabase()
                }

                createWeeklyAlarm(alarmListScreenEvent.alarm)
            }

            is AlarmListScreenEvent.DeleteAlarmEvent -> {
                viewModelScope.launch {
                    val deleteJob = async { deleteAlarmFromDatabase(alarmListScreenEvent.alarm) }
                    deleteJob.await()
                    getAlarmsFromDatabase()
                }

                cancelAlarm(alarmListScreenEvent.alarm)
            }

            is AlarmListScreenEvent.PauseAlarmEvent -> {
                cancelAlarm(alarmListScreenEvent.alarm)
            }

            is AlarmListScreenEvent.RestoreAlarmEvent -> {
                createWeeklyAlarm(alarmListScreenEvent.alarm)
            }

            is AlarmListScreenEvent.SaveAlarmToDatabaseWithoutCreatingAlarmActionEvent -> {
                viewModelScope.launch {
                    val saveJob = async { saveAlarmToDatabase(alarmListScreenEvent.alarm) }
                    saveJob.await()
                    getAlarmsFromDatabase()
                }
            }
        }
    }

    private fun getAlarmsFromDatabase() {
        viewModelScope.launch(Dispatchers.Main) {
            getAllAlarmsFromDatabaseUseCase().collect { alarmList ->
                if (alarmList.isNotEmpty()) {

                    val timeSortedAlarmList = alarmList.sortedBy { alarm ->
                        alarm.hour.toInt() * 60 + alarm.minute.toInt()
                    }

                    _uiState.update { alarmListScreenState ->
                        alarmListScreenState.copy(
                            alarms = timeSortedAlarmList,
                            alarmExtendedValueList = timeSortedAlarmList.map { alarm -> alarm.isExtended }
                        )
                    }
                } else {
                    _uiState.update { alarmListScreenState ->
                        alarmListScreenState.copy(
                            alarms = emptyList(),
                        )
                    }
                }
            }
        }
    }

    private suspend fun saveAlarmToDatabase(alarm: Alarm) {
        saveAlarmToDatabaseUseCase(alarm)
    }

    private suspend fun deleteAlarmFromDatabase(alarm: Alarm) {
        deleteAlarmFromDatabaseUseSase(alarm)
    }

    private fun createWeeklyAlarm(alarm: Alarm) {
        createWeeklyAlarmUseCase.invoke(alarm = alarm)
    }

    private fun cancelAlarm(alarm: Alarm) {
        cancelAlarmUseCase.invoke(alarm = alarm)
    }

    private suspend fun getUserStatisticsById(id: Int): UserStatistics? {
        val userStatistics = getUserStatisticsByIdUseCase(id = id)
        return userStatistics
    }

    private suspend fun saveUserStatisticsToDatabase(userStatistics: UserStatistics) {
        saveUserStatisticsToDatabaseUseCase(userStatistics = userStatistics)
    }

    private suspend fun saveAllSupportedLanguageResultsToDatabase(
        supportedLanguageResults: List<String>
    ) {
        supportedLanguageResults.forEach { languageName ->
            saveLanguageResultToDatabaseUseCase(
                LanguageResult(
                    id = generateUniqueId(),
                    name = languageName
                )
            )
        }
    }
}