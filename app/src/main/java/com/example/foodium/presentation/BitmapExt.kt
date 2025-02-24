package com.example.foodium.presentation

import android.graphics.Bitmap
import android.media.Image
import java.nio.ByteBuffer



fun Bitmap.centerCrop(desiredWidth: Int, desiredHeight: Int): Bitmap {
    val xStart = (width - desiredWidth) / 2
    val yStart = (height - desiredHeight) / 2

    if(xStart < 0 || yStart < 0 || desiredWidth > width || desiredHeight > height) {
        throw IllegalArgumentException("Invalid arguments for center cropping")
    }

    return Bitmap.createBitmap(this, xStart, yStart, desiredWidth, desiredHeight)
}

fun toBitmap(image: Image): Bitmap {
    val planes: Array<Image.Plane> = image.planes
    val buffer: ByteBuffer = planes[0].buffer
    val pixelStride: Int = planes[0].pixelStride
    val rowStride: Int = planes[0].rowStride
    val rowPadding: Int = rowStride - pixelStride * image.width
    val bitmap = Bitmap.createBitmap(
        image.width + rowPadding / pixelStride,
        image.height, Bitmap.Config.ARGB_8888
    )
    bitmap.copyPixelsFromBuffer(buffer)
    return bitmap
}