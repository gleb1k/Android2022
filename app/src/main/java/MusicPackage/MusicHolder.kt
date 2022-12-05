package MusicPackage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android2022.databinding.ItemMusicBinding

class MusicHolder(
    private val binding: ItemMusicBinding,
    private val actionShowInfo: (Int) -> Unit,
    private val actionPlay: (Music) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(music: Music) {
        with(binding) {
            tvAuthor.text = music.author
            tvName.text = music.name
            ivCover.setImageResource(music.cover)

            ivCover.setOnClickListener {

            }

            root.setOnClickListener{
                //todo ?
                actionShowInfo(MusicRepository.musicList.indexOfFirst { music == it })
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            actionShowInfo: (Int) -> Unit,
            actionPlay: (Music) -> Unit
        ) = MusicHolder(
            binding = ItemMusicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            actionShowInfo,
            actionPlay
        )
    }

}