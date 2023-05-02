package com.ipoy.storyapp_v1.ui.authentication.signup

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.databinding.FragmentSignUpBinding
import com.ipoy.storyapp_v1.other.ViewModelFactory
import com.ipoy.storyapp_v1.data.source.Result
import com.ipoy.storyapp_v1.data.response.signup.SignUpResponse


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels{
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSignupHaveAccount.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.loginFragment))
        binding.edRegisterEmail.addTextChangedListener(SignTextWatcher)
        binding.edRegisterPassword.addTextChangedListener(SignTextWatcher)
        binding.btSignUp.setOnClickListener{ it ->
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            binding.btSignUp.isEnabled = true
            signUpViewModel.signUp(name, email, password).observe(requireActivity()) {
                if (it != null) {
                    when(it) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            processSignUp(it.data)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    private val SignTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val usernameInput: String = binding.edRegisterEmail.getText().toString().trim()
            val passwordInput: String = binding.edRegisterPassword.getText().toString().trim()
            binding.btSignUp.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty())
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun processSignUp(data: SignUpResponse) {
        if (data.error) {
            Toast.makeText(requireContext(), "Gagal Sign Up", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Sign Up berhasil, silahkan login!", Toast.LENGTH_LONG).show()
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment(isFromSignUp = true))
        }
    }


    private fun showLoading(state: Boolean) {
        binding.pbCreateSignup.isVisible = state
        binding.edRegisterEmail.isInvisible = state
        binding.edRegisterName.isInvisible = state
        binding.edRegisterPassword.isInvisible = state
        binding.textView2.isInvisible = state
        binding.tvSignupHaveAccount.isInvisible = state
        binding.btSignUp.isInvisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}