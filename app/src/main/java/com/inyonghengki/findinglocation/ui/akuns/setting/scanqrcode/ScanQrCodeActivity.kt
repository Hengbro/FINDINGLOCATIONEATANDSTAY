package com.inyonghengki.findinglocation.ui.akuns.setting.scanqrcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.inyonghengki.findinglocation.R
import com.inyonghengki.findinglocation.ui.detail.DetailOneTempatActivity
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView

class ScanQrCodeActivity : AppCompatActivity() {
    private lateinit var barcodeView: CompoundBarcodeView

    private val barcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            // Mengirim hasil scan ke halaman lain
            val intent = Intent(this@ScanQrCodeActivity, DetailOneTempatActivity::class.java)
            intent.putExtra("qrCodeResult", result.text)
            startActivity(intent)
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanqr)

        barcodeView = findViewById(R.id.barcodeView)

        // Meminta izin untuk menggunakan kamera jika belum diberikan
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST
            )
        }
        barcodeView.decodeSingle(barcodeCallback)

    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                barcodeView.resume()
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 101
    }
}
