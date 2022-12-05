package MusicPackage

import com.example.android2022.R

object MusicRepository {
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
}