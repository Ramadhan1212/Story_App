package com.ipoy.storyapp_v1.ui.authentication.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ipoy.storyapp_v1.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()
    }
}