package com.ipoy.storyapp_v1.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.ipoy.storyapp_v1.ui.login.LoginActivity
import com.ipoy.storyapp_v1.story.Stories
import com.ipoy.storyapp_v1.ui.upload.UploadActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.connection.SessionManager
import com.ipoy.storyapp_v1.databinding.FragmentHomeBinding
import com.ipoy.storyapp_v1.databinding.RvItemBinding
import com.ipoy.storyapp_v1.ui.story.LoadingStateAdapter

class HomeFragment : Fragment() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: SessionManager
    private val adapter: Adapter by lazy {
        Adapter()
    }

    private val vm: HomeViewModel by viewModels {
        val pref = SessionManager.get(requireContext().dataStore)
        ViewModelFactory(requireContext(), pref)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        pref = SessionManager.get(requireContext().dataStore)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setHasOptionsMenu(true)
        getStories()
        loadStories()


    }

    private fun loadStories(){
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        binding.rvStories.setHasFixedSize(true)
        adapter.setOnItemClickCallback(object : Adapter.OnItemClickCallback {
            override fun onItemClick(data: Stories, card: RvItemBinding) {

                val extras = FragmentNavigatorExtras(
                    Pair(card.imgStory, data.photo),
                    Pair(card.tvName, data.name),
                    Pair(card.tvDesc, data.description),
                    Pair(card.tvDate, data.date)
                )
                val toDestination =
                    com.ipoy.storyapp_v1.ui.home.HomeFragmentDirections.actionNavigationHomeToDetailFragment()
                toDestination.photo = data.photo
                toDestination.name = data.name
                toDestination.description = data.description
                toDestination.date = data.date

                view?.findNavController()?.navigate(
                    toDestination,
                    navigatorExtras = extras
                )

            }
        })
    }

    private fun getStories() {
        showLoading(true)
        postponeEnterTransition()
        vm.getToken().observe(viewLifecycleOwner) {

            var token: String
            token = it
            vm.showStories(token).observe(viewLifecycleOwner) {

                if (it != null) {
                    showLoading(false)
                    adapter.submitData(lifecycle, it)
                    (view?.parent as ViewGroup).doOnPreDraw {
                        startPostponedEnterTransition()
                    }
                } else {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Failed to load Story", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            binding.fabAdd.setOnClickListener {
                Intent(requireContext(), UploadActivity::class.java).also {
                    it.putExtra(UploadActivity.TOKEN, token)
                    startActivity(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbHome.visibility = View.VISIBLE
        } else {
            binding.pbHome.visibility = View.GONE
        }
    }
}