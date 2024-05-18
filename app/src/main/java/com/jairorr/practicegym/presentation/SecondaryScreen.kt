package com.jairorr.practicegym.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.jairorr.practicegym.data.User

@Composable
fun SecondaryScreen(modifier:Modifier = Modifier, viewModel: SecondaryViewModel, backToHome:()->Unit){
    val userFound by viewModel.user.collectAsState()
    val queryState by viewModel.queryState.collectAsState()
    Column(modifier = modifier) {
        Text(text = userFound?.firstName.toString(), color = Color.Red)
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = queryState, color = Color.Gray, fontStyle = FontStyle.Italic)
        FilledTonalButton(onClick = { backToHome() }) {
            Text(text = "Back to Home")
        }
    }
}