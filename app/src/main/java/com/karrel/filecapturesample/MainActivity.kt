package com.karrel.filecapturesample

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        private const val PERMISSION_FILE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val REQUEST_CODE_PERMISSION = 1000
    }

    private val fileCapture = CaptureUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupButtonsEvent()
    }

    override fun onStart() {
        super.onStart()

        proccessCheckPermission()
    }

    private fun proccessCheckPermission() {

        val permissionState = ContextCompat.checkSelfPermission(this, PERMISSION_FILE)
        val isCheckedPermission = PackageManager.PERMISSION_GRANTED == permissionState

        if (!isCheckedPermission) {
            ActivityCompat.requestPermissions(this, arrayOf(PERMISSION_FILE), REQUEST_CODE_PERMISSION)
        }
    }

    private fun setupButtonsEvent() {
        capture_button.setOnClickListener {
            capture()
        }
    }

    private fun capture() {
        toast("capture")
        try {
            fileCapture.capture(root_view)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode == REQUEST_CODE_PERMISSION) {

            if(!grantResults.all { it == PackageManager.PERMISSION_GRANTED }){
                toast("권한이 승인되지 않아 앱을 사용할 수 없습니다.")
                finish()
            }
        }
    }
}
