package com.ipoy.storyapp_v1.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.ipoy.storyapp_v1.MainActivity
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isRemembered = false
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean(CHECKBOX, false)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        hasLogin(isRemembered)

        binding.btnSignIn.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener(this)
        binding.settingLanguage.setOnClickListener(this)
    }


    private fun hasLogin(boolean: Boolean) {
        if(boolean) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_sign_in -> {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etLoginPassword.text.toString().trim()
                login(email, password)
                viewModel.loginResponse.observe(this){
                    it?.apply {
                        inputLogin(userId, name, token)
                        val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(mainIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@LoginActivity).toBundle())
                        finish()
                    }
                }
                viewModel.isLoading.observe(this){
                    showLoading(it)
                }
            }
            R.id.btn_sign_up -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.setting_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
    }

    private fun validateLogin(email: String, password: String): Boolean{
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && password.length >= 8 && password.isNotEmpty() && email.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun inputLogin(userId: String, name: String, token: String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(NAME, name)
        editor.putString(USER_ID, userId)
        editor.putString(TOKEN, token)
        editor.putBoolean(CHECKBOX, binding.rememberMe.isChecked)
        editor.apply()
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) binding.loginProgressBar.visibility = View.VISIBLE
        else binding.loginProgressBar.visibility = View.GONE
    }

    private fun login(email: String, password: String){
        if(validateLogin(email, password)) {
            viewModel.login(email, password, this)
        } else {
            Toast.makeText(this, applicationContext.getString(R.string.data_have_to_be_valid), Toast.LENGTH_SHORT).show()
        }

    }
    companion object {
        const val SHARED_PREFERENCES = "shared_preferences"
        const val CHECKBOX = "checkbox"
        const val NAME = "name"
        const val USER_ID = "user_id"
        const val TOKEN = "token"
    }
}