package br.com.mobileti.imageeditor

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_editor.*
import java.io.File
import java.io.FileOutputStream


class EditorActivity : AppCompatActivity(), View.OnDragListener, View.OnLongClickListener {

    private val TAG = EditorActivity::class.java.simpleName

    private var textArrayList: ArrayList<TextView> = arrayListOf()
    private var selectedTextView: TextView? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        title = "Image Editor"
        setSupportActionBar(toolbar)

        initListeners()
        imageUri = intent.getParcelableExtra(ImageEditor.URI_ARG)

        imageUri.let {
            memeImageView.setImageURI(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_done -> {
                Intent().apply {
                    putExtra(ImageEditor.URI_ARG, getEditedImagePath())
                    setResult(ImageEditor.RESULT_IMAGE_EDITED, this)
                }
            }
        }
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDrag(view: View?, event: DragEvent?): Boolean {

        Log.d(TAG, "onDrag: view->$view\n DragEvent$event")
        when (event?.action) {
            DragEvent.ACTION_DRAG_ENDED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_ENDED ")
                return true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_EXITED")
                return true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_ENTERED")
                return true
            }
            DragEvent.ACTION_DRAG_STARTED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_STARTED")
                return true
            }
            DragEvent.ACTION_DROP -> {
                Log.d(TAG, "onDrag: ACTION_DROP")
                val tvState = event.localState as View
                Log.d(TAG, "onDrag:viewX" + event.x + "viewY" + event.y)
                Log.d(TAG, "onDrag: Owner->" + tvState.parent)
                val tvParent = tvState.parent as ViewGroup
                tvParent.removeView(tvState)
                val container = view as RelativeLayout
                container.addView(tvState)
                tvParent.removeView(tvState)
                tvState.x = event.x - tvState.width / 2
                tvState.y = event.y - tvState.height / 2
                view.addView(tvState)
                view.setVisibility(View.VISIBLE)
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                //Log.d(TAG, "onDrag: ACTION_DRAG_LOCATION")
                return true
            }
            else -> return false
        }
    }

    override fun onLongClick(view: View?): Boolean {
        val dragShadowBuilder = View.DragShadowBuilder(view)
        view?.let {
            ViewCompat.startDragAndDrop(it, null, dragShadowBuilder, it, 0)
        }
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.text_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.editItemMenu -> {
                selectedTextView?.let {
                    showEditTextDialog(it)
                }
            }
            R.id.resizeItemMenu -> {
                selectedTextView?.let {
                    showResizeTextDialog(it)
                }
            }
            R.id.removeItemMenu -> {
                selectedTextView?.let {
                    removeText(it)
                }
            }
        }
        return true
    }

    private fun getEditedImagePath(): String {
        val bitmap = viewToBitmap(imageContainer)
        val file = saveBitmap(bitmap)
        return file.absolutePath
    }

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(bitmap: Bitmap): File {
        val file = File.createTempFile("imged", ".png", cacheDir)
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()

        return file
    }

    private fun initListeners() {
        imageContainer.setOnDragListener(this)

        addTextButton.setOnClickListener {
            showAddTextDialog()
        }
    }

    private fun removeText(view: View) {
        imageContainer.removeView(view)
        textArrayList.remove(view)
    }

    private fun showAddTextDialog() {
        val input = EditText(this)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        AlertDialog.Builder(this)
            .setView(input)
            .setTitle(getString(R.string.add_text_dialog_title))
            .setPositiveButton(getString(R.string.add_text_ok_button)) { p0, p1 ->
                if (input.text.isNotEmpty()) {
                    createNewTextView(input.text.toString()).let { view ->
                        view.tag = view.id.toString()
                        imageContainer.addView(view, -1)
                        view.setOnLongClickListener(this)
                        view.setOnClickListener {
                            selectedTextView = view
                            registerForContextMenu(view)
                            view.showContextMenu()
                        }
                    }
                }
            }
            .setOnCancelListener {

            }
            .create()
            .show()
    }

    private fun showResizeTextDialog(textView: TextView) {

        val view = layoutInflater.inflate(R.layout.dialog_transform, null)
        val resizeSeekBar = view.findViewById<SeekBar>(R.id.resizeSeekbar)
        val scaleSeekBar = view.findViewById<SeekBar>(R.id.scaleSeekbar)

        resizeSeekBar.apply {
            progress = (textView.textSize / resources.displayMetrics.scaledDensity).toInt()
            incrementProgressBy(5)
            max = 100

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP , p1.toFloat())
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
        }

        scaleSeekBar.apply {
            progress = textView.rotation.toInt()
            incrementProgressBy(5)
            max = 100

            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    textView.rotation = p1.toFloat()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
        }

        AlertDialog.Builder(this)
            .setView(view)
            .setPositiveButton(getString(R.string.add_text_ok_button)) { p0, p1 ->

            }
            .create()
            .show()
    }

    private fun showEditTextDialog(textView: TextView) {
        val input = EditText(this)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        input.setText(textView.text.toString())
        AlertDialog.Builder(this)
            .setView(input)
            .setTitle(getString(R.string.edit_text_dialog_title))
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(getString(R.string.add_text_ok_button)) { p0, p1 ->
                textView.text = input.text
            }
            .setOnCancelListener { }
            .create()
            .show()
    }

    private fun createNewTextView(t: String): TextView {
        val newText = TextView(this)
        newText.apply {
            text = t
            textSize = resources.getDimension(R.dimen.new_text_size)
            setTextColor(ContextCompat.getColor(context, R.color.new_text_color))
            gravity = Gravity.CENTER

            layoutParams = if (textArrayList.isEmpty()) {
                topTextLayoutParam()
            } else {
                if (textArrayList.size == 1) {
                    bottomTextLayoutParam()
                } else {
                    centerTextLayoutParam()
                }
            }
        }

        newText.setOnLongClickListener(this)
        textArrayList.add(newText)
        return newText
    }

    private fun topTextLayoutParam(): RelativeLayout.LayoutParams {
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        return params
    }

    private fun centerTextLayoutParam(): RelativeLayout.LayoutParams {
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        return params
    }

    private fun bottomTextLayoutParam(): RelativeLayout.LayoutParams {
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        return params
    }
}
