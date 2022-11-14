package com.example.android2022

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import java.io.ByteArrayOutputStream

class CustomActivityResultContract(private val context: Context) :
    ActivityResultContract<Intent, Bitmap?>() {

    override fun createIntent(context: Context, input: Intent): Intent {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }

        val chooserIntent = Intent.createChooser(galleryIntent, "Chooser")

        chooserIntent?.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        return chooserIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? {
        if (resultCode == Activity.RESULT_CANCELED)
            return null

        //камера
        val bitmap = intent.takeIf { resultCode == Activity.RESULT_OK }
            ?.getParcelableExtra<Parcelable>("data")
        //галерея
        val uri = intent.takeIf { resultCode == Activity.RESULT_OK }?.data
        //говнокод
        if (bitmap != null) {
            return bitmap as Bitmap
        } else
            if (uri != null)
                return BitmapFactory.decodeStream(
                    context
                        .contentResolver.openInputStream(uri)
                )
            else
                return null
    }
}