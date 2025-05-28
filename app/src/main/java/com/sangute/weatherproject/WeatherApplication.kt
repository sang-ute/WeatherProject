package com.sangute.weatherproject

import android.app.Application
import android.widget.Toast
import com.google.firebase.FirebaseApp
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }

    fun showToast(message: String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}