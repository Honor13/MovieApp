package com.example.movieapp.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor():ViewModel() {

    private val _selectedItem = MutableLiveData<String>()
    val selectedItem: LiveData<String>
        get() = _selectedItem

    private val _items = MutableLiveData<List<String>>()
    val items: LiveData<List<String>>
        get() = _items

    init {
        loadItems()
    }

     fun loadItems() {
         val dateArray = ArrayList<String>()
         val calendar = Calendar.getInstance()

         val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
         dateArray.add(dateFormat.format(calendar.time)) // Bugünün tarihini ekle

         repeat(5) {
             calendar.add(Calendar.DATE, 1) // Sonraki günü ekle
             dateArray.add(dateFormat.format(calendar.time))
         }

         _items.value = dateArray
    }

    fun onItemSelected(position: Int) {
        if (position > 0) {
            _selectedItem.value = _items.value?.get(position)
        }
    }
}