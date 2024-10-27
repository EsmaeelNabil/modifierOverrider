package dev.supersam.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.supersam.app.ui.designsystem.LARGETEXT
import dev.supersam.app.ui.designsystem.SOMEMETHOD
import dev.supersam.app.ui.theme.GrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GrTheme {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.padding(16.dp))
                    Greeting()
                    Spacer(Modifier.padding(16.dp))
                    LARGETEXT(text = "Large text")
                    Spacer(Modifier.padding(16.dp))
                    TEST_FUNCTION_CRAZY_NAME()
                    Spacer(Modifier.padding(16.dp))
                    TEST_FUNCTION_CRAZY_NAME()
                }

            }
        }
    }
}

@Composable
fun TEST_FUNCTION_CRAZY_NAME(modifier: Modifier = Modifier) {
    Text(text = "Crazy named function", modifier = modifier)
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "Hello !",
        modifier = modifier
    )
}