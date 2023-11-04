package com.inyonghengki.findinglocation.ui.tempat

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.inyonghengki.findinglocation.databinding.ActivityQrCodetempBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.getInitial
import com.inyongtisto.myhelper.extension.setToolbar

class QrCodeTempatActivity : MyActivity() {

    val user = Pref.getUser()

    private var _binding: ActivityQrCodetempBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQrCodetempBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "QR Code Saya")

        setData()
        mainButton()

        val userData = user?.tempat?.id  // Replace with the actual user's data
        val qrCodeBitmap = generateQRCode(userData.toString())
        if (qrCodeBitmap != null) {
            // Set the QR code bitmap to an ImageView or use it as needed
            binding.imgQrcode.setImageBitmap(qrCodeBitmap)
        }
    }

    override fun onResume() {
        setData()
        super.onResume()
    }

    private fun mainButton(){

    }
    private fun setData(){

        binding.apply {
            tvnmProduk.text = user?.tempat?.nameTempat
            tvKategori.text = user?.tempat?.kategori
            tvInisial.text = user?.tempat?.nameTempat.getInitial()
        }

    }

    private fun generateQRCode(data: String): Bitmap? {
        try {
            val writer = QRCodeWriter()
            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
            val width: Int = bitMatrix.width
            val height: Int = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bmp
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}