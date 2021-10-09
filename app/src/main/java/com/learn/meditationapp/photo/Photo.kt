package com.learn.meditationapp.photo

import android.graphics.drawable.Drawable

class Photo (var time : String, var image : Drawable)

object PhotoFromGallery {
    var photo : Photo? = null
}