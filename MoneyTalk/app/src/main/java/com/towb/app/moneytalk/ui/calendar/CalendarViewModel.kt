package com.towb.app.moneytalk.ui.calendar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towb.app.moneytalk.MoneyTalkApp
import com.towb.app.moneytalk.data.model.EventItem
import com.towb.app.moneytalk.data.remote.EventItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
open class CalendarViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val eventItemRepository: EventItemRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    open val selectedDate = _selectedDate.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val eventItems = selectedDate
        .flatMapLatest { date ->
            eventItemRepository.getTodayEventItems(date)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _timeTable = MutableStateFlow<Map<LocalDate, List<String>>>(emptyMap())
    open val timeTable = _timeTable.asStateFlow()

    open fun selectDate(date: LocalDate) {
        Timber.tag(this.javaClass.simpleName).d("selectDate, date $date")

        _selectedDate.value = date
    }

    fun addEvent(event: EventItem) {
        Timber.tag(this.javaClass.simpleName).d("addEvent, event $event")

        viewModelScope.launch {
            eventItemRepository.insert(event)
        }
    }

    open fun deleteEvent(event: EventItem) {
        Timber.tag(this.javaClass.simpleName).d("deleteEvent, event $event")

        viewModelScope.launch {
            eventItemRepository.delete(event)
        }
    }

    fun logAppName() {
        val app = context.applicationContext as MoneyTalkApp
        Timber.d("앱 이름: ${app.packageName}")
    }
}