package com.towb.app.moneytalk.ui.calendar

import android.content.Context
import androidx.lifecycle.ViewModel
import com.towb.app.moneytalk.MoneyTalkApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _timeTable = MutableStateFlow<Map<LocalDate, List<String>>>(emptyMap())
    val timeTable = _timeTable.asStateFlow()

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun addEvent(date: LocalDate, event: String) {
        _timeTable.value = _timeTable.value.toMutableMap().apply {
            this[date] = (this[date] ?: emptyList()) + event
        }
    }

    fun deleteEvent(date: LocalDate, event: String) {
        _timeTable.value = _timeTable.value.toMutableMap().apply {
            this[date] = (this[date] ?: emptyList()) - event
        }
    }

    fun logAppName() {
        val app = context.applicationContext as MoneyTalkApp
        Timber.d("앱 이름: ${app.packageName}")
    }
}