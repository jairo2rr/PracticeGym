package com.jairorr.practicegym.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jairorr.practicegym.data.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToSecond: (Int) -> Unit,
    listUser:List<User>,
    saveUser:(String,String)->Unit,
    navigateToTransition:()->Unit
) {
    var userName by remember { mutableStateOf("") }
    var userLastName by remember { mutableStateOf("") }
    var listUser = remember(listUser){ listUser }
    Column(modifier = modifier.padding(top = 15.dp)) {
        Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = "Home Screen")
        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = userName,
            onValueChange = { userName = it })
        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = userLastName,
            onValueChange = { userLastName = it })
        OutlinedButton(onClick = { saveUser(userName,userLastName) }) {
            Text(text = "Register user")
        }
        Button(onClick = { navigateToTransition() }) {
            Text(text = "Navigate to Cards")
        }
        LazyColumn(){
            items(listUser){user->
                Row {
                    Column {
                        Text(text = "${user.firstName}")
                        Text(text = "${user.lastName}")
                    }
                    Column {
                        IconButton(onClick = { navigateToSecond(user.uid) }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit user")
                        }
                    }
                }
            }
        }
    }
}