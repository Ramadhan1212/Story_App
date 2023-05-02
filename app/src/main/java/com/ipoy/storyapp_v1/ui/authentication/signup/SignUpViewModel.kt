package com.ipoy.storyapp_v1.ui.authentication.signup

import androidx.lifecycle.ViewModel
import com.ipoy.storyapp_v1.data.source.StoryRepository

class SignUpViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun signUp(name: String, email: String, password: String) = storyRepository.postSignUp(name, email, password)
}