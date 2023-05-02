package com.ipoy.storyapp_v1.ui.authentication.login

import androidx.lifecycle.ViewModel
import com.ipoy.storyapp_v1.data.source.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun login(email: String, password: String) = storyRepository.postLogin(email, password)
}