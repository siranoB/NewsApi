package co.sirano.news.common.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtil {
    // API 응답 데이터 값을 타임스템프 값으로 변환함.
    fun convertToMilliSecond(dateStr: String): Long {
        if (dateStr.isEmpty()) return 0
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val dateTime = formatter.parse(dateStr)
        return dateTime?.time ?: 0
    }

    // API 응답 데이터 값을 화면에 표시하기 위한 값으로 변환함.
    fun convertToString(dateStr: String): String {
        if (dateStr.isEmpty()) return ""
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateTime: LocalDateTime = LocalDateTime.parse(dateStr, formatter)
        val formatter2: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")
        return dateTime.format(formatter2)
    }
}
