package ru.cpro.ktordomofon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.cpro.ktordomofon.data.repository.IntercomRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: IntercomRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository = repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
