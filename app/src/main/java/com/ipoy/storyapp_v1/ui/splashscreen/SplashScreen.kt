package com.ipoy.storyapp_v1.ui.splashscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.connection.SessionManager
import com.ipoy.storyapp_v1.ui.login.LoginActivity
import com.ipoy.storyapp_v1.ui.story.HomeActivity

class SplashScreen : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ssactivity)

        var token = "empty"
        var name = "Guest"

        getToken().observe(this){
            if (it != null){
                token = it
            }
        }

        getName().observe(this){
            if (it != null){
                name = it
            }
        }

        val delay = getString(R.string.delay).toLong()


        supportActionBar?.hide()



        Handler().postDelayed({
            if (token != "empty"){
                Toast.makeText(this, "Welcome back, $name", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
//

        }, delay)
    }
    fun getToken(): LiveData<String> {
        val pref = SessionManager.get(dataStore)
        val tkn = pref.getToken().asLiveData()
        return tkn
    }

    fun getName(): LiveData<String> {
        val pref = SessionManager.get(dataStore)
        val name = pref.getName().asLiveData()
        return name
    }
}