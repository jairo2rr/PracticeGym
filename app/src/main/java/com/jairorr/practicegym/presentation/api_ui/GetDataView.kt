package com.jairorr.practicegym.presentation.api_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GetDataView(viewModel:ApiViewModel,modifier:Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "List of ...")
        LazyColumn{
            items(listOf("")){
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)) {
                    Text(text = it)
                }
            }
        }
    }
}