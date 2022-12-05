package MusicPackage

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android2022.MainActivity
import com.example.android2022.MusicService
import com.example.android2022.R
import com.example.android2022.databinding.FragmentMusicInfoBinding

class MusicInfoFragment : Fragment(R.layout.fragment_music_info) {

    private var binding : FragmentMusicInfoBinding? = null
    private var music: Music? = null;

    private var binder: MusicService.MusicBinder? = null


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as? MusicService.MusicBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.bindService(
            Intent(activity, MusicService::class.java),
            connection,
            Service.BIND_AUTO_CREATE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicInfoBinding.bind(view)

        val musicId = arguments?.getString(MUSIC_INFO_FRAGMENT_TAG)!!.toInt()

        music = MusicRepository.musicList[musicId]

        binding?.run {
            ivCover.setImageResource(music!!.cover)
            tvAuthor.text = music!!.author
            tvName.text = music!!.name
            tvGenre.text = music!!.genre

            ivPlay.setOnClickListener {
                binder?.playMusic(music!!)
            }
            ivSkipBack.setOnClickListener {

            }
            ivSkipNext.setOnClickListener {

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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