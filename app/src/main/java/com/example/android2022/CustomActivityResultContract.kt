package com.example.android2022

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class CustomActivityResultContract : ActivityResultContract<Intent, Uri?>() {

    override fun createIntent(context: Context, input: Intent): Intent {

        //return Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        var temp = input.getParcelableExtra<Parcelable>("data")

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            .putExtra(MediaStore.EXTRA_OUTPUT, input.getParcelableExtra<Parcelable>("data"))

        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }

        val chooserIntent = Intent.createChooser(galleryIntent, "Chooser")

        chooserIntent?.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        return chooserIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {

        val data = intent.takeIf { resultCode == Activity.RESULT_OK }
            ?.getParcelableExtra<Parcelable>("data") as? Uri
//        val bitmap = data.
        val uri = intent?.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri
        if (data == null)
            return intent?.data
        else
            return data
//        val temp = intent?.data
//        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(temp))
//        return intent.takeIf { resultCode == Activity.RESULT_OK }?.getParcelableExtra(Intent.EXTRA_STREAM)
    }
}