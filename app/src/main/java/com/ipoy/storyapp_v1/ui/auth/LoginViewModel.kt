package com.ipoy.storyapp_v1.ui.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ipoy.storyapp_v1.model.LoginResult
import com.ipoy.storyapp_v1.model.UserLoginResponse
import com.ipoy.storyapp_v1.network.StoryAppRetrofitInstance
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {

    private var _loginResponse = MutableLiveData<LoginResult>()
    val loginResponse = _loginResponse

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    fun login(email: String, password: String, context: Context) {
        _isLoading.value = true
        StoryAppRetrofitInstance.apiService!!
            .getLoginUser(email, password)
            .enqueue(object: Callback<UserLoginResponse> {
                override fun onResponse(
                    call: Call<UserLoginResponse>,
                    response: Response<UserLoginResponse>
                ) {
                    if(response.isSuccessful) {
                        _loginResponse.postValue(response.body()?.loginResult)
                        _isLoading.value = false
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        Toast.makeText(context, jsonObj.getString("message"), Toast.LENGTH_LONG).show()
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Failed request to server", Toast.LENGTH_SHORT).show()
                }
            })
    }
}