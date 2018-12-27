# RateDialog

ImageEditor is a Android Kotlin library for editing images.

![ImageEditor](https://thumbs.gfycat.com/ThoroughDeliciousAmericanlobster.webp)

# Getting Started

# Usage

Call the image editor passing an image URI

```kotlin
ImageEditor(this) // Context
  .setImageUri(uri) // Image URI
  .create() // Call the Image Editor
```
Where you called the Image Editor, place onActivityResult, the Image Editor will cache the edited image and return your path

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    // use this request and result code
    if (requestCode == ImageEditor.REQUEST_IMAGE_EDIT && resultCode == ImageEditor.RESULT_IMAGE_EDITED) {
        val imagePath = data?.getStringExtra(ImageEditor.URI_ARG) // Edited image path
        imageView.setImageURI(Uri.parse(imagePath))
    }
}
```
# Language and Support

* Currently supported only English and Brazilian portuguese, please help me with new translations by opening new PR.
* Support Android API 15+
* Kotlin and Java support
