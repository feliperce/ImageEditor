package com.mobileti.imageeditor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import br.com.mobileti.imageeditor.ImageEditor
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_IMAGE_PICKER = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editImageFab.setOnClickListener {
            Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(this, "Select Picture"), REQUEST_IMAGE_PICKER)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            val uri = data.data

            ImageEditor(this)
                .setImageUri(uri)
                .create()

        } else if (requestCode == ImageEditor.REQUEST_IMAGE_EDIT && resultCode == ImageEditor.RESULT_IMAGE_EDITED) {
            val imagePath = data?.getStringExtra(ImageEditor.URI_ARG)
            imageView.setImageURI(Uri.parse(imagePath))
        }
    }
}
