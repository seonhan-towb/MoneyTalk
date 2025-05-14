package com.towb.app.moneytalk.ui.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.towb.app.moneytalk.ui.calendar.MainActivity
import com.towb.app.moneytalk.ui.theme.MoneyTalkTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {
            MoneyTalkTheme {
                SplashScreenComposable()
            }
        }
    }
}

@Composable
fun SplashScreenComposable() {
    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(true) {
        delay(2000)
        activity?.startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Welcome to My App!", style = MaterialTheme.typography.headlineSmall)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoneyTalkTheme {
        SplashScreenComposable()
    }
}