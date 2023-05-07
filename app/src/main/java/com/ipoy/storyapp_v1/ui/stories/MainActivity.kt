package com.ipoy.storyapp_v1.ui.stories

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.databinding.ActivityMainBinding
import com.ipoy.storyapp_v1.databinding.ItemStoryUserBinding
import com.ipoy.storyapp_v1.ui.auth.LoginActivity
import com.ipoy.storyapp_v1.ui.addstory.FormAddStoryActivity
import com.ipoy.storyapp_v1.ui.addstory.FormAddStoryActivity.Companion.TOKEN
import com.ipoy.storyapp_v1.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var _itemBinding: ItemStoryUserBinding? = null
    private val itemBinding get() = _itemBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var name: String
    private lateinit var token: String

    private val viewModel: StoriesViewModel by viewModels {
        ViewModelFactory(this, token)
    }
    private val storiesAdapter: StoriesAdapter by lazy { StoriesAdapter(StoriesAdapter.OnClickListener{ item ->
        Intent(this@MainActivity, DetailActivity::class.java).also {
            it.putExtra(DetailActivity.EXTRA_NAME, item.name)
            it.putExtra(DetailActivity.EXTRA_AVATAR, item.photoUrl)
            it.putExtra(DetailActivity.EXTRA_DESCRIPTION, item.description)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    Pair(itemBinding!!.imgProfile, "profile"),
                    Pair(itemBinding!!.profileName, "name"),
                )
            startActivity(it, optionsCompat.toBundle())
        }
    }, StoriesAdapter.ItemComparator) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        _itemBinding = ItemStoryUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(LoginActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        name = preferences.getString(LoginActivity.NAME, "Anonymous").toString()
        token = preferences.getString(LoginActivity.TOKEN, "").toString()

        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        getData()

        binding.addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, FormAddStoryActivity::class.java)
            intent.putExtra(FormAddStoryActivity.TOKEN, token)
            startActivity(intent)
            finish()
        }
        binding.greetMainActivity.text = getString(R.string.greet_main_activity, name)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                preferences.edit().apply {
                    clear()
                    apply()
                }
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
                true
            }
            else -> true
        }
    }

    private fun getData(){
        binding.mainRecyclerView.adapter = storiesAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storiesAdapter.retry()
            }
        )
        viewModel.listStoryItem.observe(this){
            storiesAdapter.submitData(lifecycle,it)
        }
    }

}
