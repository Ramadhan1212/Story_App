package com.ipoy.storyapp_v1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding.btnCreateAccount.setOnClickListener(this)
        binding.btnRegisToLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_create_account -> {
                if(validateCreateAccount()) {
                    val name = binding.etName.text.trim().toString()
                    val email = binding.etEmail.text?.trim().toString()
                    val password = binding.etCreatePassword.text?.trim().toString()
                    viewModel.requestCreateAccount(name, email, password, this@RegisterActivity)
                    viewModel.isLoading.observe(this){
                        showLoading(it)
                    }
                }
            }
            R.id.btn_regis_to_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun validateCreateAccount(): Boolean {
        return if(binding.etEmail.text!!.isNotEmpty()
            && binding.etCreatePassword.text!!.isNotEmpty()
            && binding.etName.text.isNotEmpty()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()
            && binding.etCreatePassword.text.toString().length > 8) {
            true
        } else {
            Toast.makeText(this, "Data have to be valid", Toast.LENGTH_SHORT).show()
            false
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }
}