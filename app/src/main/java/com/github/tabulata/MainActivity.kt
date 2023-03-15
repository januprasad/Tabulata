package com.github.tabulata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tabulata.ui.theme.TabulataTheme
import java.util.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabulataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column {
                        AnimatableSample()
                    }
                }
            }
        }
    }
}

private fun randomColor(): Color {
    val rnd = Random()
    return Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

private fun getColorsList(): MutableList<Color> {
    val colors = emptyList<Color>().toMutableList()
    repeat(2) {
        colors += randomColor()
    }
    return colors
}

@Composable
private fun AnimatableSample() {
    var isAnimated by remember { mutableStateOf(false) }
    val colors by remember { mutableStateOf(getColorsList()) }
    val color = remember { Animatable(Color.DarkGray) }

    // animate to green/red based on `button click`
    LaunchedEffect(isAnimated) {
        color.animateTo(
            if (isAnimated) colors.first() else colors.last(),
            animationSpec = tween(2000),
        )
    }

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .background(color.value),
    )
    Button(
        onClick = { isAnimated = !isAnimated },
        modifier = Modifier.padding(top = 10.dp),
    ) {
        Text(text = "Animate Color")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TabulataTheme {
        Greeting("Android")
    }
}
