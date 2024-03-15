package com.example.movieapp.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.database.entities.Booking
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirebaseOperationsViewModel @Inject constructor(var collectionBooking: CollectionReference) :
    ViewModel() {

    fun saveBooking(movieId: String, userId: String, date: String, time: String) {
        val booking = Booking("", userId, movieId, date, time)
        val saveBookingTask = collectionBooking.document().set(booking)

        saveBookingTask.addOnSuccessListener {
            Log.e("Dante", "Created")
        }.addOnFailureListener {
            Log.e("Dante", "Not Created")
        }


    }

    fun checkAndSaveBooking(movieId: String, userId: String, date: String, time: String) {
        collectionBooking.whereEqualTo("movieId", movieId)
            .whereEqualTo("bookingDate", date)
            .whereEqualTo("bookingTime", time)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    saveBooking(movieId, userId, date, time)
                } else {
                    Log.e("Dante", "Already exists")
                }

            }

    }

}