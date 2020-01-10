package com.karrel.filecapturesample

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Canvas
import android.os.Environment
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class CaptureUtil {
    fun capture(view: View) {

        val bitmap:Bitmap = getBitmapFromView(view)
        val path = Environment.getExternalStorageDirectory().absolutePath

        saveBitmapToFileCache(bitmap, path)
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height,
            Bitmap.Config.ARGB_8888
        )
        val c = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(c)
        return bitmap
    }


    private fun saveBitmapToFileCache(bitmap: Bitmap, filePath: String): Bitmap {
        println("CaptureUtil > saveBitmapToFileCache > filePath : $filePath")

        val fileCacheItem = File(filePath)

        var out: OutputStream? = null


        try {
            fileCacheItem.createNewFile()
            out = FileOutputStream(fileCacheItem)

            bitmap.compress(CompressFormat.JPEG, 100, out)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return bitmap
    }
}
