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
import ru.vladalexeco.lazyprogrammer.domain.model.Alarm
import ru.vladalexeco.lazyprogrammer.domain.usecase.DeleteAlarmFromDatabaseUseSase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetAllAlarmsFromDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmToDatabaseUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmListScreenEvent
import ru.vladalexeco.lazyprogrammer.presentation.state.AlarmListScreenState
import javax.inject.Inject

@HiltViewModel
class AlarmListScreenViewModel @Inject constructor(
    val saveAlarmToDatabaseUseCase: SaveAlarmToDatabaseUseCase,
    val getAllAlarmsFromDatabaseUseCase: GetAllAlarmsFromDatabaseUseCase,
    val deleteAlarmFromDatabaseUseSase: DeleteAlarmFromDatabaseUseSase
) : ViewModel() {

    init {
        getAlarmsFromDatabase()
    }

    private val _uiState = MutableStateFlow(AlarmListScreenState())
    val uiState: StateFlow<AlarmListScreenState> = _uiState.asStateFlow()

    fun onEvent(alarmListScreenEvent: AlarmListScreenEvent) {

        when (alarmListScreenEvent) {
            is AlarmListScreenEvent.SaveAlarmEvent -> {
                viewModelScope.launch {
                    val insertJob = async {
                        saveAlarmToDatabase(alarmListScreenEvent.alarm)
                    }
                    insertJob.await()
                    getAlarmsFromDatabase()
                }
            }

            is AlarmListScreenEvent.DeleteAlarmEvent -> {
                viewModelScope.launch {
                    val deleteJob = async {
                        deleteAlarmFromDatabase(alarmListScreenEvent.alarm)
                    }
                    deleteJob.await()
                    getAlarmsFromDatabase()
                }
            }
        }
    }

    private fun getAlarmsFromDatabase() {
        viewModelScope.launch(Dispatchers.Main) {
            getAllAlarmsFromDatabaseUseCase().collect { alarmList ->
                if (alarmList.isNotEmpty()) {
                    _uiState.update { alarmListScreenState ->
                        alarmListScreenState.copy(
                            alarms = alarmList
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
}