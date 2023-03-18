package com.github.tabulata

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class RadioButtonHolder(
    val name: String,
    val selected: Boolean,
)

val radioButtonPool = mutableListOf(
    RadioButtonHolder("Android", true),
    RadioButtonHolder("Flutter", false),
    RadioButtonHolder("SwiftUI", false),
)

class RadioButtonVM : ViewModel() {
    var itemsState = mutableStateListOf<RadioButtonHolder>()
        private set

    var chachedType: RadioButtonHolder = radioButtonPool.first()

    init {
        "Hello!".p()
        itemsState.addAll(radioButtonPool)
    }

    fun execute(uiEvents: UIEvents) {
        when (uiEvents) {
            is UIEvents.OnOptionSelected -> {
                val item = uiEvents.type
                set(item)
                if (chachedType.name != item.name) {
                    set(chachedType, false)
                    chachedType = item
                }
            }
        }
    }

    private fun set(item: RadioButtonHolder, state: Boolean = true) {
        val position = itemsState.indexOfFirst { element ->
            element.name == item.name
        }
        itemsState[position] = itemsState[position].copy(
            selected = state,
        )
    }
}

fun Any.p() {
    Log.v(javaClass.name, this.toString())
}

sealed class UI()

sealed class UIEvents {
    data class OnOptionSelected(val type: RadioButtonHolder) : UIEvents()
}
