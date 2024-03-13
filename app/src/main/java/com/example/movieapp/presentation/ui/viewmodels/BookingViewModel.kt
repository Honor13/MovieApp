package com.example.movieapp.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor() : ViewModel() {

    private val _selectedDate = MutableLiveData<Date>()
    val selectedDay: LiveData<Date>
        get() = _selectedDate

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
    }

    /////////////////////////////
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

    fun disableChipsBasedOnTime(chipGroup: ChipGroup, readDate: Date) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip

            // Chip'in textinden saat bilgisini al
            val hour = chip.text.toString().substringBefore(":").toInt()
            Log.e("Dante", "currentHour: $currentHour")

            val chipDate = Calendar.getInstance().apply {
                time = readDate
            }.get(Calendar.DAY_OF_MONTH)

            if (currentDay == chipDate && hour <= currentHour) {
                chip.isEnabled = false


            } else {
                chip.isEnabled = true


            }
        }
    }
}