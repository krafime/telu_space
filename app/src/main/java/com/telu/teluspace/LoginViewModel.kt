package com.telu.teluspace

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.*

class LoginViewModel: ViewModel() {
    private val _loginResponse = MutableLiveData<Int>()
    val loginResponse: LiveData<Int> = _loginResponse
    fun setLogin(context: Context, email: String, password: String, userPreference: UserPreference){
        ApiConfig.getApiServices().login(email, password).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    _loginResponse.postValue(response.code())
                    viewModelScope.launch {
                        userPreference.saveUser(
                            (response.body()?.id) as Int,
                            response.body()?.name ?: "",
                            true
                        )
                    }
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}