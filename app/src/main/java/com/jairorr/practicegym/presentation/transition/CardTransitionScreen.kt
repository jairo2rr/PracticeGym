package com.jairorr.practicegym.presentation.transition

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CardTransitionScreen(modifier: Modifier = Modifier, viewModel: CardViewModel) {
    val currentPosition by viewModel.currentPosition.observeAsState(0)
    val currentProgress by viewModel.currentProgress.observeAsState(0.0f)
    val optionsMarked by viewModel.mapOptionsSelected.collectAsState()
    val items by viewModel.listItems.collectAsState()
    val isFormCompleted by viewModel.isFormCompleted.observeAsState(false)
    val showButtons by viewModel.showButtons.observeAsState(false)
    Surface(modifier = modifier){
        if(isFormCompleted){
            Column(verticalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Form completed successfully")
                Text(text = "Form completed successfully!", color = Color.Green, fontWeight = FontWeight.Bold)
            }
        }else {
            Column {
                ProgressBarCard(currentProgress = currentProgress)

                TitleCard()
//            CardContent(items = items, saveAnswer = {}, indexPassed = currentPosition,changeIndex = {newIndex -> viewModel.updatePosition(newIndex)})
                CardContent(items = items, saveAnswer = {}, viewModel = viewModel)

                ProgressIndicatorCard(
                    totalItems = items.size,
                    currentPosition = currentPosition,
                    moveToPosition = { position -> viewModel.updatePosition(position) })

                ArrowsNavigation(moveIntoArrow = { viewModel.moveIntoArrow(it) })

                if (showButtons) {
                    RowButtons(
                        onClear = { viewModel.onClear() },
                        onSubmit = { viewModel.onSubmit() }
                    )
                }
            }
        }

    }
}

@Composable
fun RowButtons(modifier:Modifier = Modifier, onSubmit:()->Unit, onClear:()->Unit) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        FilledTonalButton(onClick = {
            onSubmit()
        }) {
            Text(text = "Submit")
        }
        OutlinedButton(onClick = {
            onClear()
        }) {
            Text(text = "Clear answers")
        }
    }
}

@Composable
fun ArrowsNavigation(modifier:Modifier = Modifier, moveIntoArrow:(Int)->Unit) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        OutlinedButton(modifier = Modifier.padding(horizontal = 8.dp), onClick = { moveIntoArrow(-1) }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Move to left")
        }
        OutlinedButton(modifier = Modifier.padding(horizontal = 8.dp), onClick = { moveIntoArrow(1) }) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Move to right")
        }
    }
}

@Composable
fun ProgressIndicatorCard(modifier:Modifier = Modifier, totalItems: Int, currentPosition:Int, moveToPosition:(Int)->Unit) {
    val localContext = LocalContext.current
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 80.dp), horizontalArrangement = Arrangement.SpaceBetween){
        (1..totalItems).forEachIndexed { index, _ ->
            Box(modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .aspectRatio(1f)
                .background(color = if (index == currentPosition) Color.Red else Color.Gray)
                .clickable {
                    if (currentPosition != index) moveToPosition(index) else Toast
                        .makeText(localContext, "You are in the position!", Toast.LENGTH_SHORT)
                        .show()
                })
        }
    }
}

@Composable
fun TitleCard(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp), horizontalArrangement = Arrangement.Center){
        Text("Card questions", fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ProgressBarCard(modifier:Modifier = Modifier,currentProgress: Float) {
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        progress = currentProgress
    )
}
