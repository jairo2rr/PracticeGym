package com.jairorr.practicegym.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jairorr.practicegym.data.User
import com.jairorr.practicegym.domain.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SecondaryViewModel(private val userDao:UserDao, private val uid:Int):ViewModel() {
    var user = MutableStateFlow<User?>(null)
        private set

    var queryState = MutableStateFlow("")
        private set

    init {
        viewModelScope.launch {
            userDao.getUserById(uid).collect { userFounded->
                user.value = userFounded
                queryState.value = if (userFounded!=null) "User founded!" else "User doesn't found!"
            }
        }
    }
}