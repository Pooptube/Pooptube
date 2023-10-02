package com.example.pooptube.main

import java.util.Date

object Utils {
    fun formatViewCount(viewCount: Int): String {
        return when {
            viewCount == 0 -> "없음"
            viewCount >= 100000000 -> "${viewCount / 100000000}.${viewCount % 100000000 / 10000000}억"
            viewCount >= 10000 -> "${viewCount / 10000}.${viewCount % 10000 / 1000}만"
            viewCount >= 1000 -> "${viewCount / 1000}.${viewCount % 1000 / 100}천"
            else -> viewCount.toString()
        }
    }
    fun formatTimeDifference(currentTime: Date, postedTime: Date): String {
        val timeDifferenceMillis = currentTime.time - postedTime.time
        // 현재 시간과 게시된 시간 사이의 차이를 계산합니다.
        val timeDifferenceSeconds = timeDifferenceMillis / 1000
        val timeDifferenceMinutes = timeDifferenceSeconds / 60
        val timeDifferenceHours = timeDifferenceMinutes / 60
        val timeDifferenceDays = timeDifferenceHours / 24
        val timeDifferenceMonths = timeDifferenceDays / 30 // 한 달을 30일로 가정
        val timeDifferenceYears = timeDifferenceDays / 365 // 1년을 365일로 가정

        return when {
            timeDifferenceYears >= 1 -> "${(timeDifferenceYears + 0.5).toInt()}년 전"
            timeDifferenceMonths >= 1 -> "${(timeDifferenceMonths + 0.5).toInt()}개월 전"
            timeDifferenceDays >= 1 -> "${(timeDifferenceDays + 0.5).toInt()}일 전"
            timeDifferenceHours >= 1 -> "${(timeDifferenceHours + 0.5).toInt()}시간 전"
            timeDifferenceMinutes >= 1 -> "${(timeDifferenceMinutes + 0.5).toInt()}분 전"
            else -> "방금 전"
        }
    }
}