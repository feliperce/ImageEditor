package com.mobileti.imageeditor

import android.app.Activity
import android.content.Intent
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

        val imageEditor = ImageEditor(this)

        editImageFab.setOnClickListener {
            val intent = Intent()
// Show only images, no videos or anything else
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
// Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_PICKER)
        }
    }

//    val intent = Intent(this, EditorActivity::class.java)
//    startActivity(intent)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            val uri = data.data

            val imageEditor = ImageEditor(this)
                .setImageUri(uri).create()

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}
