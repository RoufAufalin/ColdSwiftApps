package com.bangkit.coldswiftapps.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coldswiftapps.data.EventRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: EventRepository) : ViewModel() {

    fun logout(){
        viewModelScope.launch {
            repository.logoutUser()
        }
    }
}