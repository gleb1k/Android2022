package com.example.android2022

object MusicRepository {

    var currentId = -1
    var isPlaying = false

    val musicList: List<Music> = arrayListOf(
        Music(
            "REASON TO LIVE",
            "DVRST",
            "Фонк",
            R.drawable.reason_to_live,
            R.raw.dvrst_reason_to_live
        ),
        Music(
            "Major Crimes",
            "HEALTH & Window Weather",
            "Альтернативная музыка/инди",
            R.drawable.major_crimes,
            R.raw.health_major_crimes
        ),
        Music(
            "No Love",
            "АДЛИН",
            "Хип-хоп/рэп",
            R.drawable.no_love,
            R.raw.adlin_no_love
        ),

        Music(
            "LAND OF FIRE",
            "Kordhell",
            "Phonk",
            R.drawable.land_of_fire,
            R.raw.kordhell_land_of_fire
        ),
        Music(
            "INDUSTRY BABY",
            "Lil Nas X, Jack Harlow",
            "Хип-хоп/рэп",
            R.drawable.industy_baby,
            R.raw.lil_nas_industy_baby
        ),
        Music(
            "Autoplay",
            "LSP",
            "Поп-музыка",
            R.drawable.autoplay,
            R.raw.lsp_autoplay
        ),
    )

    fun newNext(): Int {
        if (currentId == musicList.size - 1) {
            currentId = 0
        } else {
            currentId +=1
        }
        return currentId
    }

    fun newPrev(): Int {
        if (currentId == 0) {
            currentId = musicList.size - 1
        } else {
            currentId -=1
        }
        return currentId
    }

    fun next(currentPosition: Int): Music {
        if (currentPosition == musicList.size - 1)
            return musicList[0]
        else
            return musicList[currentPosition + 1]
    }

    fun prev(currentPosition: Int): Music {
        if (currentPosition == 0)
            return musicList[musicList.size - 1]
        else
            return musicList[currentPosition - 1]
    }


}