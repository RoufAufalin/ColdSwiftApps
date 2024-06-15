package com.bangkit.coldswiftapps.di

import android.content.Context
import com.bangkit.coldswiftapps.data.EventRepository
import com.bangkit.coldswiftapps.data.preference.UserPreference
import com.bangkit.coldswiftapps.data.preference.dataStore
import com.bangkit.coldswiftapps.data.remote.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    private var repository: EventRepository? = null
    fun provideRepository(context: Context): EventRepository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val usertoken = runBlocking { preferences.getSession().first() }
        val apiService = ApiConfig.getApiService(usertoken.token)
        return EventRepository(apiService, preferences)
    }
}