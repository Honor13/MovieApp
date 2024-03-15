package com.example.movieapp.di

import com.example.movieapp.presentation.ui.viewmodels.FirebaseOperationsViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideCollectionBooking(collectionBooking: CollectionReference): FirebaseOperationsViewModel {
        return FirebaseOperationsViewModel(collectionBooking)
    }


    @Provides
    @Singleton
    fun provideCollectionReference(): CollectionReference {
        return Firebase.firestore.collection("Booking")
    }
}