package com.towb.app.moneytalk.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CategoryTag(
    text: String,
    backgroundColor: Color,
    textColor: Color = Color(0xFF2E2E2E),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

fun getCategoryColor(category: String): Color {
    return when (category.trim().lowercase()) {
        "식비" -> Color(0xFFFCEEEE)    // 연한 코랄핑크
        "교통" -> Color(0xFFE3F4F5)    // 연한 민트블루
        "문화" -> Color(0xFFECE8F6)    // 연한 퍼플
        "기타" -> Color(0xFFF0F2F5)    // 기본 회색
        else -> Color(0xFFF0F2F5)     // 기본 회색
    }
}
