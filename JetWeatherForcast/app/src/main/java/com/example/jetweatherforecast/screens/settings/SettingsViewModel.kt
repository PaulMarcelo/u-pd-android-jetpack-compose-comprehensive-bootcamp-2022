package com.example.jetweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.jetweatherforecast.model.Unit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {
 private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList =_unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect{listOfSett->
                    if(listOfSett.isEmpty()){
                        Log.d("TAG", ": Empty favs ")
                    }else{
                        _unitList.value=listOfSett
                        Log.d("FAVS", ":${unitList.value} ")
                    }
                }
        }
    }

    fun insertUnit(setting: Unit) =
        viewModelScope.launch { repository.insertUnit(setting) }

    fun updateUnit(setting: Unit) =
        viewModelScope.launch { repository.updateUnit(setting) }

    fun deleteUnit(setting: Unit) =
        viewModelScope.launch { repository.deleteUnit(setting) }

    fun deleteAllUnits()= viewModelScope.launch { repository.deleteAllUnits() }

}