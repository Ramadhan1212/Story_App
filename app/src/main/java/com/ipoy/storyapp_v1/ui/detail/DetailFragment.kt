package com.ipoy.storyapp_v1.ui.detail

import android.os.Bundle
import android.transition.ChangeBounds
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: com.ipoy.storyapp_v1.ui.detail.DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detail()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 1200
        }
    }





    private fun detail() {
        binding.apply {
            imgStory.transitionName = args.photo
            tvName.transitionName   = args.name
            tvDesc.transitionName   = args.description
            tvDate.transitionName   = args.date

            val photo   = args.photo
            val name    = args.name
            val desc    = args.description
            val date    = args.date

            Glide.with(binding.root)
                .load(photo)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_image_24)
                )
                .centerCrop()
                .into(imgStory)

            tvName.text = name
            tvDesc.text = desc
            tvDate.text = date
        }
    }

}