![MIT](https://img.shields.io/github/license/feliperce/ImageEditor.svg)
[![version](https://jitpack.io/v/feliperce/ImageEditor.svg)](https://jitpack.io/#feliperce/ImageEditor)


# ImageEditor

ImageEditor is a Android Kotlin library for editing images.

Sample on Youtube:
[![Watch the video](https://i.imgur.com/EaO64vJ.png)](https://youtu.be/HKcjDqLvBJM)

# Getting Started
**Step 1.** Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the dependency
```groovy
dependencies {
    implementation 'com.github.feliperce:ImageEditor:0.5.1'
}
```

# Usage

Call the image editor passing an image URI

```kotlin
ImageEditor(this) // Context
  .setImageUri(uri) // Image URI
  .create() // Call the Image Editor
```
Where you called the ImageEditor, place onActivityResult, the ImageEditor will cache the edited image and return your path

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
