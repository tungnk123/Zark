//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.tungnk123.zark.data.dto.calendar.CalendarDto
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import java.time.LocalDateTime
//import javax.inject.Inject
//
//@HiltViewModel
//class CalendarViewModel @Inject constructor(
//) : ViewModel() {
//
//    private val _events = MutableStateFlow<List<CalendarDto>>(emptyList())
//    val events: StateFlow<List<CalendarDto>> = _events
//
//    init {
//        loadSampleEvents()
//    }
//
//    private fun loadSampleEvents() {
//        viewModelScope.launch {
//            val sampleEvents = listOf(
//                CalendarDto(
//                    id = "1",
//                    title = "Team Meeting",
//                    startTime = LocalDateTime.now()
//                        .withHour(14)
//                        .withMinute(30),
//                    endTime = LocalDateTime.now()
//                        .withHour(15)
//                        .withMinute(30),
//                ),
//                CalendarDto(
//                    id = "2",
//                    title = "Code Review",
//                    startTime = LocalDateTime.now()
//                        .withHour(9)
//                        .withMinute(0),
//                    endTime = LocalDateTime.now()
//                        .withHour(10)
//                        .withMinute(0),
//                )
//            )
//            _events.value = sampleEvents
//        }
//    }
//}
