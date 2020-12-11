package com.example.artister.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class ImageConverter {
    fun convertToBase64(bm: Bitmap): String {
        val resizedBm = Bitmap.createScaledBitmap(bm, 412, 324, true) //5kb

        val byteArrayOS = ByteArrayOutputStream()
        resizedBm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS)

        val b = byteArrayOS.toByteArray()

        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun convertToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(
            base64Str.substring(base64Str.indexOf(",") + 1),
            Base64.DEFAULT
        )
        val bm = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        return Bitmap.createScaledBitmap(bm, 1080, 1920, true)
    }
}