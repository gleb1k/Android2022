package com.example.android2022

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android2022.databinding.FragmentMusicInfoBinding

class MusicInfoFragment : Fragment(R.layout.fragment_music_info) {

    private var binding : FragmentMusicInfoBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicInfoBinding.bind(view)

    }

    companion object {

        const val MUSIC_INFO_FRAGMENT_TAG = "MUSIC_INFO_FRAGMENT_TAG"

        fun newInstance(data : String) =
            MusicInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(MUSIC_INFO_FRAGMENT_TAG,data)
                }
            }
    }
}