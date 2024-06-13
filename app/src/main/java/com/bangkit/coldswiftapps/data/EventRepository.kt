package com.bangkit.coldswiftapps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.coldswiftapps.data.preference.UserModel
import com.bangkit.coldswiftapps.data.preference.UserPreference
import com.bangkit.coldswiftapps.data.remote.ApiService
import com.bangkit.coldswiftapps.data.remote.response.ListEventResponse
import com.bangkit.coldswiftapps.data.remote.response.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository(private val apiService: ApiService, private val userPreference: UserPreference) {

    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> {
        val result = MutableLiveData<Result<LoginResponse>>()

        apiService.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val token = responseBody?.token ?: ""

                    if (responseBody != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            saveUser(UserModel(token, true))
                        }
                        result.postValue(Result.success(responseBody))
                    } else {
                        result.postValue(Result.failure(Exception("Response body is null")))
                    }

                } else {
                    result.postValue(Result.failure(Exception(response.message())))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                result.postValue(Result.failure(t))
            }
        })
        return result
    }

    fun getAllEvents(callback: (Result<List<ListEventResponse>>) -> Unit) {
        apiService.getAllEvent().enqueue(object : Callback<List<ListEventResponse>> {
            override fun onResponse(
                call: Call<List<ListEventResponse>>,
                response: Response<List<ListEventResponse>>
            ) {
                if (response.isSuccessful) {
                    val events = response.body()
                    if (events != null) {
                        callback(Result.success(events))
                    } else {
                        callback(Result.failure(Exception("Response body is null")))
                    }
                } else {
                    callback(Result.failure(Exception(response.message())))
                }
            }

            override fun onFailure(call: Call<List<ListEventResponse>>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    private suspend fun saveUser(user: UserModel) {
        userPreference.saveUser(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logoutUser() {
        userPreference.clearUser()
    }

}
