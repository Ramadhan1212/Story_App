package com.ipoy.storyapp_v1.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.ipoy.storyapp_v1.ui.about.AboutActivity
import com.ipoy.storyapp_v1.connection.SessionManager
import com.ipoy.storyapp_v1.databinding.FragmentProfileBinding
import com.ipoy.storyapp_v1.ui.login.LoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    private var _binding: FragmentProfileBinding? = null
    private lateinit var pref: SessionManager
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        pref = SessionManager.get(requireContext().dataStore)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        setProfile()
        setAbout()
        logut()
        setJoin()
    }

    fun getName(){
        val temp = pref.getName().asLiveData()
        var name = "Guest"
        temp.observe(viewLifecycleOwner){
            name = it
            binding.tilName.text = name
        }
    }

    fun setProfile() {
        binding.apply {
            getName()
        }
    }

    fun setAbout() {
        binding.apply {
            btAbout.setOnClickListener {
                Intent(requireContext(), AboutActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    fun logut() {
        binding.btLg.setOnClickListener {
            GlobalScope.launch {
                logout()
            }
            activity?.let {
                Intent(requireContext(), LoginActivity::class.java).also {
                    startActivity(it)
                }
                it.finish()

            }
            Toast.makeText(requireContext(), "Berhasil Logout!", Toast.LENGTH_SHORT).show()
        }
    }

    fun setJoin() {
        binding.btJoin.setOnClickListener {
            Toast.makeText(requireContext(), "Ah.... We're not Hiring. Please come again later", Toast.LENGTH_LONG).show()
        }
    }
    suspend fun logout() {
        val pref = SessionManager.get(requireContext().dataStore)
        pref.logout()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
