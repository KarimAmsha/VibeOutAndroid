package com.vibeout.talaa.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.vibeout.talaa.core.database.AppDatabase
import com.vibeout.talaa.core.database.CityDao
import com.vibeout.talaa.core.database.SavedPlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Firestore enables offline persistence by default on Android, which keeps
    // the app usable without a connection and reduces billed reads.
    @Provides @Singleton fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides @Singleton fun provideAuth(): FirebaseAuth = Firebase.auth

    @Provides @Singleton fun provideStorage(): FirebaseStorage = Firebase.storage

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "vibeout.db")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides fun provideCityDao(db: AppDatabase): CityDao = db.cityDao()
    @Provides fun provideSavedPlaceDao(db: AppDatabase): SavedPlaceDao = db.savedPlaceDao()
}
