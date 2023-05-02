package com.ipoy.storyapp_v1.other

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ipoy.storyapp_v1.injection.Injection
import com.ipoy.storyapp_v1.ui.authentication.login.LoginViewModel
import com.ipoy.storyapp_v1.ui.main.createstory.CreateStoryViewModel
import com.ipoy.storyapp_v1.ui.main.liststory.ListStoryViewModel
import com.ipoy.storyapp_v1.ui.authentication.signup.SignUpViewModel


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListStoryViewModel::class.java) -> {
                ListStoryViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(CreateStoryViewModel::class.java) -> {
                CreateStoryViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}