package com.jairorr.practicegym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.jairorr.practicegym.domain.AppDatabase
import com.jairorr.practicegym.domain.UserDao
import com.jairorr.practicegym.navigation.AppNavHost
import com.jairorr.practicegym.ui.theme.PracticeGymTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeGymTheme {
                val database =
                    Room.databaseBuilder(this, AppDatabase::class.java, "app_database").fallbackToDestructiveMigration().build()
                val userDao = database.userDao()
                MainView(Modifier.fillMaxSize(), userDao)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(modifier: Modifier = Modifier, userDao: UserDao) {
    var topBarTitle by remember { mutableStateOf("") }
    Scaffold(
        modifier = modifier,
        topBar = { CustomTopBar(topBarTitle=topBarTitle) },
        bottomBar = { CustomBottomBar() },
        contentColor = MaterialTheme.colorScheme.inverseOnSurface,
    ) { it ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AppNavHost(
                navController = rememberNavController(),
                modifier = Modifier.fillMaxSize(),
                onChangeRoute = { newTitle ->
                    topBarTitle = newTitle
                },
                userDao = userDao
            )
        }
    }
}

@Composable
fun CustomTopBar(modifier: Modifier = Modifier, topBarTitle:String) {
    Surface(modifier = modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            text = topBarTitle
        )
    }
}

@Composable
fun CustomBottomBar(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.secondary) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            text = "Custom bottom bar"
        )
    }
}

@Preview
@Composable
fun previewMainView() {
//    PracticeGymTheme {
//        MainView(Modifier.fillMaxSize(), userDao)
//    }
}