package com.towb.app.moneytalk.ui.detaildate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.towb.app.moneytalk.ui.theme.MoneyTalkTheme

class DetailDateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun DetailCalendar(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun CalendarDetailPreview() {
    MoneyTalkTheme {
        DetailCalendar(
            modifier = Modifier.fillMaxSize()
        )
    }
}