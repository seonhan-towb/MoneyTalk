package com.towb.app.moneytalk.ui.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.towb.app.moneytalk.data.model.CalendarDayOfWeek
import com.towb.app.moneytalk.data.model.CalendarInitData
import com.towb.app.moneytalk.data.model.EventItem
import com.towb.app.moneytalk.ui.component.CategoryDropdown
import com.towb.app.moneytalk.ui.component.DropdownMenuBox
import com.towb.app.moneytalk.ui.component.TimePickerField
import com.towb.app.moneytalk.ui.theme.MoneyTalkTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyTalkTheme {
                CalendarNavHost()
            }
        }
    }
}

@Composable
fun CalendarNavHost() {
    val navController = rememberNavController()

    NavHost(navController, "Calendar") {

        composable("Calendar") {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MainCalendar(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                    ) { selectedDate ->
                        Timber.tag(this.javaClass.simpleName).e("selectedDate : ${selectedDate}")
                    }
                }
            }
        }

        composable("AddEvent") {
            AddEventScreenWrapper(navController)
        }
    }
}

@Composable
fun AddEventScreenWrapper(navController: NavController) {
    val viewModel = hiltViewModel<CalendarViewModel>()

    AddEventScreen(
        onSave = { savedItem ->
            viewModel.addEvent(savedItem)
            navController.popBackStack()
        },
        onCancel = {
            navController.popBackStack()
        }
    )
}

@Composable
fun MainCalendar(
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now(),
    config: CalendarInitData = CalendarInitData(),
    calendarModel: CalendarViewModel = hiltViewModel(),
    navController: NavController? = null,
    onSelectedDate: (LocalDate) -> Unit,
) {

    val selectedDate by calendarModel.selectedDate.collectAsState()
    val eventItems by calendarModel.eventItems.collectAsState()

    var selectedYearMonth by remember { mutableStateOf(YearMonth.of(currentDate.year, currentDate.month)) }
    var startDayOfWeek by remember { mutableStateOf(0) } // 0 = Sunday, 1 = Monday

    // 해당 월의 날짜 구성
    val daysInMonth = (1..selectedYearMonth.lengthOfMonth()).map { day ->
        LocalDate.of(selectedYearMonth.year, selectedYearMonth.month, day)
    }

    // 선택된 시작 요일(일요일 또는 월요일)을 기준으로 해당 월이 어떤 요일로 시작하는지 결정
    val firstDayOfWeek = daysInMonth.first().dayOfWeek.value
    val adjustedStartDay = if (startDayOfWeek == 1) {
        (firstDayOfWeek - 1 + 7) % 7    // 월요일을 첫 번째 요일로 설정하기 위해 날짜 이동
    } else {
        firstDayOfWeek % 7              // 일요일을 한 주의 첫 번째 요일로 사용
    }

    // 월의 첫 번째 날 전까지 몇 개의 null 날짜를 채워야 하는지 계산
    val startPadding = adjustedStartDay
    val endPadding = (7 - (startPadding + daysInMonth.size) % 7) % 7
    val calendarDays = List(startPadding) { null } + daysInMonth + List(endPadding) { null }

    // 월과 년 선택 옵션
    val months = java.time.Month.entries.map { it.name.lowercase().replaceFirstChar { c -> c.titlecase() } }
    val years = config.yearRange.toList()

    val today = LocalDate.now()
    val displayDate = selectedDate ?: today // 선택된 날짜 없으면 오늘 날짜 사용

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownMenuBox(
                options = months,
                selectedOption = selectedYearMonth.month.name.lowercase().replaceFirstChar { it.titlecase() },
                onOptionSelected = { selectedMonth ->
                    selectedYearMonth = YearMonth.of(selectedYearMonth.year, java.time.Month.valueOf(selectedMonth.uppercase()))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DropdownMenuBox(
                options = years.map { it.toString() },
                selectedOption = selectedYearMonth.year.toString(),
                onOptionSelected = { selectedYear ->
                    selectedYearMonth = YearMonth.of(selectedYear.toInt(), selectedYearMonth.month)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            DropdownMenuBox(
                options = listOf("Sunday", "Monday"),
                selectedOption = if (startDayOfWeek == 1) "Monday" else "Sunday",
                onOptionSelected = { selectedDay ->
                    startDayOfWeek = if (selectedDay == "Monday") 1 else 0
                }
            )
        }

        // 요일 헤더 생성 (선택된 시작 요일에 맞게 조정)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            val weekDays = CalendarDayOfWeek.getWeekDays(startDayOfWeek)
            weekDays.forEach { day ->
                Text(
                    text = day.take(3),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // 캘린더 그리드 생성
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .heightIn(max = 300.dp)
        ) {
            items(calendarDays.chunked(7)) { week ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    week.forEach { date ->
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clickable {
                                    date?.let {
                                        calendarModel.selectDate(it)
                                        onSelectedDate(it)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            // 동그라미 표시 (선택된 날짜)
                            if (date == selectedDate) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                            shape = CircleShape
                                        )
                                ) {
                                    Text(
                                        text = date?.dayOfMonth?.toString() ?: "",
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            } else {
                                Text(text = date?.dayOfMonth?.toString() ?: "")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 선택된 날짜의 타임 테이블
        TimeTableView(
            date = selectedDate,
            timeTable = eventItems,
            onAddEventClick = { navController?.navigate("AddEvent") },
            onDeleteEvent = { calendarModel.deleteEvent(it) }
        )
    }
}

/**
 * Preview 생성을 위한 더미 ViewModel
 * 메인 프리뷰 제거
 */
//class FakeCalendarViewModel(context: Context)
//    : CalendarViewModel(context) {
//
//    private val _selectedDate = MutableStateFlow<LocalDate?>(LocalDate.of(2025, 5, 19))
//    override val selectedDate: StateFlow<LocalDate?> = _selectedDate
//
//    private val _timeTable = MutableStateFlow(
//        mapOf(
//            LocalDate.of(2025, 5, 19) to listOf(
//                "09:00 - 회의",
//                "12:30 - 점심 식사",
//                "15:00 - 클라이언트 콜"
//            )
//        )
//    )
//    override val timeTable: StateFlow<Map<LocalDate, List<String>>> = _timeTable
//
//    override fun selectDate(date: LocalDate) {
//        _selectedDate.value = date
//    }
//
//    override fun deleteEvent(date: LocalDate, event: String) {
//        _timeTable.update {
//            it.toMutableMap().apply { get(date)?.let { list -> put(date, list - event) } }
//        }
//    }
//}
//
//@Preview(
//    showBackground = true,
//    backgroundColor = 0xFFFFFF
//)
//@Composable
//fun MainCalendarPreview() {
//    MoneyTalkTheme {
//        val context = LocalContext.current
//        val fakeVM = remember { FakeCalendarViewModel(context) }
//
//        MainCalendar(
//            modifier = Modifier.fillMaxSize(),
//            currentDate = LocalDate.of(2025, 5, 19),
//            config = CalendarInitData(
//                yearRange = 2020..2030
//            ),
//            onSelectedDate = {},
//            calendarModel = fakeVM
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun TimeTableViewPreview() {
    val dummyDate = LocalDate.of(2025, 5, 19)
    val dummyEvents = listOf(
        EventItem(
            id = 1,
            eventTitle = "회의",
            eventPrice = 0,
            eventCategory = "업무",
            eventTime = LocalTime.of(9, 0),
            eventDate = dummyDate
        ),
        EventItem(
            id = 2,
            eventTitle = "점심 식사",
            eventPrice = 12000,
            eventCategory = "식비",
            eventTime = LocalTime.of(12, 30),
            eventDate = dummyDate
        ),
        EventItem(
            id = 3,
            eventTitle = "클라이언트 콜",
            eventPrice = 0,
            eventCategory = "업무",
            eventTime = LocalTime.of(15, 0),
            eventDate = dummyDate
        )
    )

    TimeTableView(
        date = dummyDate,
        timeTable = dummyEvents,
        onAddEventClick = {},
        onDeleteEvent = {}
    )
}


@Preview(showBackground = true)
@Composable
fun AddEventScreenPreview() {
    MoneyTalkTheme {
        AddEventScreen(
            onSave = { println("저장됨: $it") },
            onCancel = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDropdownPreview() {
    var selectedCategory by remember { mutableStateOf("식비") }
    val options = listOf("식비", "교통", "문화", "기타")

    MoneyTalkTheme {
        CategoryDropdown(
            selectedCategory = selectedCategory,
            options = options,
            onCategorySelected = { selectedCategory = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimePickerFieldPreview() {
    var time by remember { mutableStateOf(LocalTime.of(14, 30)) }

    TimePickerField(
        selectedTime = time,
        onTimeSelected = { time = it }
    )
}