package dev.supersam.app.ui.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun LARGETEXT(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(text = text, modifier = modifier, fontSize = 16.sp)
}


@Composable
fun SOMEMETHOD(modifier: Modifier = Modifier, text: String) {
    Text(text = text, modifier = modifier)
}