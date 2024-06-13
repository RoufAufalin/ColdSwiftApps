package com.bangkit.coldswiftapps.di

import android.content.Context
import com.bangkit.coldswiftapps.data.EventRepository
import com.bangkit.coldswiftapps.data.preference.UserPreference
import com.bangkit.coldswiftapps.data.preference.dataStore
import com.bangkit.coldswiftapps.data.remote.ApiConfig

object Injection {
    private var repository: EventRepository? = null
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val preferences = UserPreference.getInstance(context.dataStore)
        return EventRepository(apiService, preferences)
    }
}