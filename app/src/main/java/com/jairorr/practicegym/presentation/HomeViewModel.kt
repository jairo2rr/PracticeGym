package com.jairorr.practicegym.presentation

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jairorr.practicegym.data.User
import com.jairorr.practicegym.domain.AppDatabase
import com.jairorr.practicegym.domain.UserDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val userDao:UserDao):ViewModel() {
    private val _listUser = MutableStateFlow(listOf<User>())
    val listUser:StateFlow<List<User>> = _listUser.asStateFlow()

    init{
        viewModelScope.launch {
            userDao.getAll().collect(){listFromDb->
                _listUser.value = listFromDb
            }
        }
    }

    fun saveUser(userName:String, userLastName:String){
        viewModelScope.launch {
            userDao.insertUsers(User(firstName = userName, lastName = userLastName))
        }
    }
}