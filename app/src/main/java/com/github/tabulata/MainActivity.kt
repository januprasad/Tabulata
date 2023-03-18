package com.github.tabulata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.tabulata.ui.theme.TabulataTheme

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
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        RadioButtonSample(radioButtonPool, radioButtonPool.first())
//                        RadioButtonSample3()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Composable
fun RadioButtonSample3() {
    val radioButtonHolderState = remember {
        mutableStateListOf<RadioButtonHolder>()
    }
    LaunchedEffect(key1 = true) {
        radioButtonHolderState.addAll(radioButtonPool)
    }
    var cachedRadioButton by remember {
        mutableStateOf(radioButtonPool.first())
    }

    fun set(item: RadioButtonHolder, state: Boolean = true) {
        val position = radioButtonHolderState.indexOfFirst { element ->
            element.name == item.name
        }
        radioButtonHolderState[position] = radioButtonHolderState[position].copy(
            selected = state,
        )
    }
    Column {
        radioButtonHolderState.forEach { item ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (item.selected),
                        onClick = {
                            set(item)
                            if (cachedRadioButton.name != item.name) {
                                set(cachedRadioButton, false)
                                cachedRadioButton = item
                            }
                        },
                    )
                    .padding(horizontal = 16.dp),
            ) {
                RadioButton(
                    selected = (item.selected),
                    onClick = {
                        set(item)
                        if (cachedRadioButton.name != item.name) {
                            set(cachedRadioButton, false)
                            cachedRadioButton = item
                        }
                    },
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge.merge(),
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        }
    }
}

@Composable
fun RadioButtonSample2(viewModel: RadioButtonVM = viewModel()) {
    Column {
        viewModel.itemsState.forEach { item ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (item.selected),
                        onClick = {
                            viewModel.execute(UIEvents.OnOptionSelected(item))
                        },
                    )
                    .padding(horizontal = 16.dp),
            ) {
                RadioButton(
                    selected = (item.selected),
                    onClick = {
                        viewModel.execute(UIEvents.OnOptionSelected(item))
                    },
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge.merge(),
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        }
    }
}

@Composable
fun RadioButtonSample(radioButtonPool: MutableList<RadioButtonHolder>, defaultRadioButton: RadioButtonHolder) {
    val (selectedRadioButtonValue, onRadioButtonSelected) = remember { mutableStateOf(defaultRadioButton) }
    Column {
        radioButtonPool.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedRadioButtonValue),
                        onClick = {
                            onRadioButtonSelected(text)
                        },
                    )
                    .padding(horizontal = 16.dp),
            ) {
                RadioButton(
                    selected = (text == selectedRadioButtonValue),
                    onClick = { onRadioButtonSelected(text) },
                )
                Text(
                    text = text.name,
                    style = MaterialTheme.typography.bodyLarge.merge(),
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TabulataTheme {
        Greeting("Android")
    }
}
