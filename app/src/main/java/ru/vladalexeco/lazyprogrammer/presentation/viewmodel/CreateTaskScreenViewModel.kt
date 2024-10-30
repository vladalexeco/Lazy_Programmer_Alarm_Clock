package ru.vladalexeco.lazyprogrammer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.vladalexeco.lazyprogrammer.domain.usecase.SaveAlarmTaskToDatabaseUseCase
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
  private val saveAlarmTaskToDatabaseUseCase: SaveAlarmTaskToDatabaseUseCase
) : ViewModel() {

}