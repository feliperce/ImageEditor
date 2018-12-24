package br.com.mobileti.imageeditor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.lang.RuntimeException

class ImageEditor(val context: Context) {

    var uri: Uri? = null
    var imageResourceId: Int? = null

    fun setImageUri(uri: Uri?): ImageEditor {
        this.uri = uri
        return this
    }

    fun create() {
        if (uri != null) {
            val intent = Intent(context, EditorActivity::class.java)
            intent.putExtra(URI_ARG, uri)
            (context as Activity).startActivityForResult(intent, REQUEST_IMAGE_EDIT)
        } else {
            throw RuntimeException("Empty URI")
        }
    }

    companion object {
        const val REQUEST_IMAGE_EDIT = 9
        const val RESULT_IMAGE_EDITED = 10
        const val URI_ARG = "uri"
    }
}