package com.clientsample.adammaskulka.clientsample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {


    private val FLASH_STATE = "FLASH_STATE"
    private val AUTOFOCUS_STATE = "AUTOFOCUS_STATE"
    private val MY_CAMERA_REQUEST_CODE = 6515

    private var mScannerView: ZXingScannerView? = null
    private var mFlash: Boolean = false
    private var mAutofocus: Boolean = false
    private var resultText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_scanner)

        mScannerView = ZXingScannerView(this)

        content_frame.addView(mScannerView)

        switchFlash.setOnClickListener({
            mFlash = !mFlash
            mScannerView?.setFlash(mFlash)
        })

        switchAutoFocus.setOnClickListener({
            mAutofocus = !mAutofocus
            mScannerView?.setAutoFocus(mAutofocus)
        })
    }

    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                        MY_CAMERA_REQUEST_CODE)
                return
            }
        }

        mScannerView?.startCamera()
        mScannerView?.setResultHandler(this)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openCamera()

        }
    }

    private fun openCamera() {
        mScannerView?.setResultHandler(this)
        mScannerView?.setAspectTolerance(0.2f)
        mScannerView?.setFlash(mFlash)
        mScannerView?.setAutoFocus(mAutofocus)
        mScannerView?.startCamera()

        switchAutoFocus.setChecked(mAutofocus)
        switchFlash.setChecked(mFlash)
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(FLASH_STATE, mFlash)
        outState?.putBoolean(AUTOFOCUS_STATE, mAutofocus)
    }

    override fun handleResult(result: Result?) {
        mScannerView?.resumeCameraPreview(this)

        if (result == null) {
            return
        }

        mScannerView?.stopCamera()

        resultText = result.text;
        val resultIntent: Intent = Intent().putExtra("BRCode", resultText)
        setResult(1, resultIntent)
        finish()
    }
}
