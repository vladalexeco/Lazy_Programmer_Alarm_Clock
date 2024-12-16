package ru.vladalexeco.lazyprogrammer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vladalexeco.lazyprogrammer.core.util.app_constants.USER_ID
import ru.vladalexeco.lazyprogrammer.domain.model.LanguageResult
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetAllLanguageResultsByUserStatisticsIdUseCase
import ru.vladalexeco.lazyprogrammer.domain.usecase.GetUserStatisticsByIdUseCase
import ru.vladalexeco.lazyprogrammer.presentation.state.StatisticsScreenState
import ru.vladalexeco.lazyprogrammer.presentation.state.getEmojiStatusWithStatusValue
import ru.vladalexeco.lazyprogrammer.presentation.state.getStringStatusWithStatusValue
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenViewModel @Inject constructor(
    private val getUserStatisticsByIdUseCase: GetUserStatisticsByIdUseCase,
    private val getAllLanguageResultsByUserStatisticsIdUseCase: GetAllLanguageResultsByUserStatisticsIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsScreenState())
    val uiState: StateFlow<StatisticsScreenState> = _uiState.asStateFlow()

    init {

        viewModelScope.launch {

            val userStatistics = getUserStatisticsByIdUseCase(id = USER_ID)

            if (userStatistics != null) {
                _uiState.update { statisticsScreenState ->
                    statisticsScreenState.copy(
                        numberOfSessions = userStatistics.numberOfSessions,
                        numberOfTasks = userStatistics.numberOfTasks,
                        numberOfMistakes = userStatistics.numberOfMistakes,
                        status = getStringStatusWithStatusValue(userStatistics.status),
                        emojiStatus = getEmojiStatusWithStatusValue(userStatistics.status)
                    )
                }
            }

            getAllLanguageResultsByUserStatisticsIdUseCase(userStatisticsId = USER_ID)
                .collect { languageResultList ->

                    if (languageResultList.isNotEmpty()) {

                    val dataMap = languageResultListToMap(languageResultList)

                    _uiState.update { statisticsScreenState ->
                        statisticsScreenState.copy(
                            languageResults = dataMap
                        )
                    }
                }
            }
        }
    }

    private fun languageResultListToMap(languageResultList: List<LanguageResult>): Map<String, Int> {
        val resultMap: MutableMap<String, Int> = linkedMapOf()

        languageResultList.forEach { languageResult ->
            resultMap[languageResult.name] = languageResult.value
        }

        return resultMap
    }


}