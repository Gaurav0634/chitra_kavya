package com.omen.chitrakavya.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omen.chitrakavya.models.PoemList
import com.omen.chitrakavya.models.PoemListItem
import com.omen.chitrakavya.repository.PoemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PoemRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPoems("Shakespeare", "Sonnet")
        }
    }

    val poems: MutableLiveData<List<PoemListItem>?>
        get() = repository.poems
}
