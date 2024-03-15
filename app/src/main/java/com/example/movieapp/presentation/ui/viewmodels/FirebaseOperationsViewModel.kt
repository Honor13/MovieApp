package com.example.movieapp.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.database.entities.Booking
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirebaseOperationsViewModel @Inject constructor(var collectionBooking: CollectionReference) :
    ViewModel() {

    var listBookingSeats = MutableLiveData<Booking>()
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
                    saveBooking(movieId, userId, date, time) // saveBookingFuntion
                } else {
                    Log.e("Dante", "Already exists")
                }

            }

    }

    fun getBooking(userId: String,movieId: String, date: String, time: String): MutableLiveData<Booking>{
        collectionBooking.addSnapshotListener{value, error ->
            if (value != null) {
                var list = Booking()

                for (d in value.documents) {
                    val seats = d.toObject(Booking::class.java)
                    if ( seats?.userId == userId && seats.movieId == movieId && seats.bookingDate == date && seats.bookingTime == time){
                        list=seats
                    }
                }
                listBookingSeats.value = list
            }
        }

        return listBookingSeats
    }



}