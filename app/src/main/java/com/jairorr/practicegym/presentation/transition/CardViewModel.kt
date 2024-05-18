package com.jairorr.practicegym.presentation.transition

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jairorr.practicegym.data.transition.ItemCard
import com.jairorr.practicegym.data.transition.Options
import kotlinx.coroutines.flow.MutableStateFlow

class CardViewModel : ViewModel() {
    var listItems = MutableStateFlow<List<ItemCard>>(emptyList())
        private set

    var sizeList = MutableLiveData<Int>(0)
        private set

    var currentPosition = MutableLiveData<Int>(0)
        private set

    var currentProgress = MutableLiveData<Float>(0.0f)
        private set

    init {
        // Inicializar el fetch de lo requerido
        val listOptions = listOf(
            Options("1", "Option 1", false),
            Options("2", "Option 2", false),
            Options("3", "Option 3", false),
            Options("4", "Option 4", true)
        )
        listItems.value = listOf(
            ItemCard("Subtitle 1", "Question 1", 1, listOptions),
            ItemCard("Subtitle 2", "Question 2", 1, listOptions),
            ItemCard("Subtitle 3", "Question 3", 1, listOptions),
            ItemCard("Subtitle 4", "Question 4", 1, listOptions),
            ItemCard("Subtitle 5", "Question 5", 1, listOptions)
        )
        sizeList.value = listItems.value.size
        calculateProgress()
    }

    fun updatePosition(newPosition: Int) {
        currentPosition.value = newPosition
        calculateProgress()
    }

    private fun calculateProgress() {
        val altPosition = currentPosition.value!!.plus(1).toFloat()
        currentProgress.value = altPosition.div(sizeList.value!!)
        Log.d(
            "CardViewModel::calculateProgress",
            "currentProgress: ${altPosition}/${sizeList.value} = ${currentProgress.value}"
        )
    }

    fun moveIntoArrow(arrow: Int) {
        val extra = currentPosition.value!!.plus(arrow)
        if (extra < 0 || extra >= sizeList.value!!) {
            return
        }
        currentPosition.value = extra
        calculateProgress()
    }
}