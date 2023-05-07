package com.ipoy.storyapp_v1.ui.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.model.UserRegisterResponse
import com.ipoy.storyapp_v1.network.StoryAppRetrofitInstance
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    fun requestCreateAccount(name: String, email: String, password: String, context: Context) {
        _isLoading.value = true
        StoryAppRetrofitInstance.apiService!!
            .createAccount(name, email, password)
            .enqueue(object: Callback<UserRegisterResponse> {
                override fun onResponse(
                    call: Call<UserRegisterResponse>,
                    response: Response<UserRegisterResponse>
                ) {
                    if(response.isSuccessful) {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        _isLoading.value = false
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        Toast.makeText(context, jsonObj.getString("message"), Toast.LENGTH_LONG).show()
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<UserRegisterResponse>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, context.getString(R.string.failed_make_account), Toast.LENGTH_SHORT).show()
                }

            })
    }
}