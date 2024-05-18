package com.jairorr.practicegym.presentation.api_ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ApiViewModel:ViewModel() {
    var data = MutableStateFlow(null)
        private set

    init {
        // Fetch data from api
    }
}