package com.omen.chitrakavya.ui.about


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val originalText = "Chitrakavya is a poetry app featuring classical William Shakespeare poems rebuilt using Kotlin. It leverages the PoetryDB API, employing Retrofit for remote data and Room for local storage. The app follows an MVVM architecture, with ViewModels retrieving data from a central repository for seamless display in activities or fragments."

    init {
        viewModelScope.launch {
            simulateTypingAnimation()
        }
    }

    private suspend fun simulateTypingAnimation() {
        originalText.forEachIndexed { index, _ ->
            delay(50) // Adjust the delay between characters here
            _text.postValue(originalText.substring(0, index + 1))
        }
    }
}
