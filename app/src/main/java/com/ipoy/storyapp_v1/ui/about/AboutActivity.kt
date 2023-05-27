package com.ipoy.storyapp_v1.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ipoy.storyapp_v1.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}