package com.jairorr.practicegym.presentation.transition

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jairorr.practicegym.data.transition.ItemCard
import com.jairorr.practicegym.data.transition.Options
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardViewModel : ViewModel() {
    var listItems = MutableStateFlow<List<ItemCard>>(emptyList())
        private set

    private var sizeList = MutableLiveData<Int>(0)

    var currentPosition = MutableLiveData<Int>(0)
        private set

    var currentProgress = MutableLiveData<Float>(0.0f)
        private set

    private var _mapOptionsSelected = MutableStateFlow<Map<Int, Options>>(mapOf())
    val mapOptionsSelected:StateFlow<Map<Int,Options>> = _mapOptionsSelected

    var isFormCompleted = MutableLiveData<Boolean>(false)
    private set

    var showButtons = MutableLiveData<Boolean>(false)
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
            ItemCard("Subtitle 2", "Question 2", 2, listOptions),
            ItemCard("Subtitle 3", "Question 3", 3, listOptions),
            ItemCard("Subtitle 4", "Question 4", 4, listOptions),
            ItemCard("Subtitle 5", "Question 5", 5, listOptions)
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

    fun markOption(index:Int, option:Options){
        val extraMap = _mapOptionsSelected.value.toMutableMap()
        extraMap[index] = option
        _mapOptionsSelected.value = extraMap

        if(_mapOptionsSelected.value.size == listItems.value.size){
            showButtons.value = true
        }
        Log.d("CardViewModel::markOption","Index: $index - Option: ${option.description} | Map: ${mapOptionsSelected.value}")
    }

    fun onClear() {
        _mapOptionsSelected.value = mutableMapOf()
        isFormCompleted.value = false
        showButtons.value = false
    }

    fun onSubmit(){
        isFormCompleted.value = true
    }
}