package br.com.mobileti.imageeditor

import android.content.Context
import android.content.Intent
import android.net.Uri

public class ImageEditor(val context: Context) {

    var uri: Uri? = null
    var imageResourceId: Int? = null

    fun setImageUri(uri: Uri?): ImageEditor {
        this.uri = uri
        return this
    }

    fun create() {
        if (uri != null) {
            val intent = Intent(context, EditorActivity::class.java)
            intent.putExtra(EditorActivity.URI_ARG, uri)
            context.startActivity(intent)
        } else {

        }
    }

    companion object {
        const val REQUEST_IMAGE_EDIT = 9
        const val RESULT_IMAGE_EDITED = 10
    }
}