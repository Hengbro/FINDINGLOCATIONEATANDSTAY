package com.inyonghengki.findinglocation.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangProduct
import com.inyonghengki.findinglocation.core.data.source.model.MenuProduct
import com.inyonghengki.findinglocation.core.data.source.model.Pengunjung
import com.inyonghengki.findinglocation.core.data.source.model.Rating
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityDetailOnePlaceBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.detail.adapter.ProdukTempatAddCartAdapter
import com.inyonghengki.findinglocation.ui.keranjangproduct.KeranjangProductActivity
import com.inyonghengki.findinglocation.ui.keranjangproduct.KeranjangProductViewModel
import com.inyonghengki.findinglocation.ui.menufasilitas.MenuFasilitasViewModel
import com.inyonghengki.findinglocation.ui.menuproduct.MenuProductViewModel
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyonghengki.findinglocation.ui.tempat.adapter.FasilitasAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.ImageTempatSliderAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.setDefaultColor
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showConfirmDialog
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("SetTextI18n")
class DetailOneTempatActivity : MyActivity() {

    private val FasilitasviewModel : MenuFasilitasViewModel by viewModel()
    private val ProductviewModel : MenuProductViewModel by viewModel()
    private val viewModelTempat: TempatViewModel by viewModel()
    private val viewModelDetail: DetailViewModel by viewModel()
    private val viewModelCartProduct: KeranjangProductViewModel by viewModel()

    private val product by extra<MenuProduct>()

    private var _binding: ActivityDetailOnePlaceBinding? = null
    private val binding get() = _binding!!

    private val adapter = ImageTempatSliderAdapter()
    private val adapterProduct = ProdukTempatAddCartAdapter{
        addCartProduct(it)
    }
    private val adapterFasilitas = FasilitasAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailOnePlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Tempat")

        mainButton()
        setDataFasilitas()
        setDataProduct()
        setupAdepter()
        detailTempat()
        Tambah()
        TambahPlaceAge()
        UpdateTotal()
    }

    override fun onResume() {
        cekUlasan()
        super.onResume()
    }

    private fun setupAdepter(){
        binding.apply {
            rvFasilitas.adapter = adapterFasilitas
            rvFasilitasall.adapter = adapterFasilitas
            rvMenuproduct.adapter = adapterProduct
            rvMenuproductall.adapter = adapterProduct
        }
    }

    private fun mainButton(){
        binding.apply {

            swipRefresh.setDefaultColor()
            swipRefresh.setOnClickListener{
                setDataFasilitas()
                setDataProduct()
                detailTempat()
            }

            btnRating.setOnClickListener {
                val qrCodeResult = intent.getStringExtra("qrCodeResult")
                val intent = Intent(this@DetailOneTempatActivity, AddUlasanActivity::class.java)
                val Id = qrCodeResult?.toInt()
                intent.putExtra("id", Id)
                startActivity(intent)
            }

            viewAllproduct.setOnClickListener {
                lyProdukall.toVisible()
                setDataProductAll()
            }

            viewAll.setOnClickListener {
                lyFasilitasall.toVisible()
                setDataFasilitasAll()
            }

            imgClosePro.setOnClickListener {
                lyProdukall.toGone()
            }

            imgClosefa.setOnClickListener {
                lyFasilitasall.toGone()
            }

            btnCartorder.setOnClickListener {
                val qrCodeResult = intent.getStringExtra("qrCodeResult")
                val intent = Intent(this@DetailOneTempatActivity, KeranjangProductActivity::class.java)
                val Id = qrCodeResult?.toInt()
                intent.putExtra("id", Id)
                startActivity(intent)
            }
        }
    }

    private fun detailTempat() {
        val qrCodeResult = intent.getStringExtra("qrCodeResult")

        qrCodeResult?.let { qrCode ->

            viewModelTempat.getOne(qrCode.toInt()).observe(this){ resource ->
                when (resource.state) {
                    State.SUCCESS -> {
                        progress.dismiss()
                        val tempat = resource.data
                        binding.apply {
                            tempat.let {
                                tvNama.text = it?.nameTempat
                                tvKategori.text = it?.kategori
                                tvJambuka.text = it?.openH +" - " +it?.closeH
                                tvNotlp.text = it?.user?.phone
                                tvEmail.text = it?.user?.email
                                tvKategori.text = it?.category?.name ?: "Lainnya"
                                tvAlamat.text = it?.address?.alamat ?: "kota Medan"
                                tvDeskripsi.text = it?.deskription

                                val image = tempat?.imageTempat?:""
                                val splitImage = image.split("|")
                                adapter.addItems(splitImage)
                                sliderImage.adapter = adapter

                            }
                        }
                    }
                    State.ERROR -> {
                        progress.dismiss()
                        toastError(resource?.message ?: "Error")
                    }
                    State.LOADING -> {
                        progress.show()
                    }
                }
            }
        }
    }

    private fun setDataFasilitas(){
        val qrCodeResult = intent.getStringExtra("qrCodeResult")
        qrCodeResult?.let { qrCode ->
            FasilitasviewModel.getFaDetail(qrCode.toInt()).observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        binding.pdmenufasilitas.toGone()
                        binding.swipRefresh.isRefreshing = false

                        val data = it.data ?: emptyList()
                        adapterFasilitas.addItems(data)

                    }
                    State.ERROR -> {
                        binding.pdmenufasilitas.toGone()
                        toastError(it?.message ?: "Error")
                    }
                    State.LOADING -> {
                        binding.pdmenufasilitas.toVisible()
                    }
                }
            }
        }
    }

    private fun setDataFasilitasAll(){
        val qrCodeResult = intent.getStringExtra("qrCodeResult")
        qrCodeResult?.let { qrCode ->
            FasilitasviewModel.getFaDetailAll(qrCode.toInt()).observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        binding.pdmenufasilitas.toGone()
                        binding.swipRefresh.isRefreshing = false

                        val data = it.data ?: emptyList()
                        adapterFasilitas.addItems(data)

                    }
                    State.ERROR -> {
                        binding.pdmenufasilitas.toGone()
                        toastError(it?.message ?: "Error")
                    }
                    State.LOADING -> {
                        binding.pdmenufasilitas.toVisible()
                    }
                }
            }
        }
    }

    private fun setDataProduct(){
        val qrCodeResult = intent.getStringExtra("qrCodeResult")
        qrCodeResult?.let {qrCode ->
            ProductviewModel.getProductDetail(qrCode.toInt()).observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        binding.pdmenuproduct.toGone()
                        binding.swipRefresh.isRefreshing = false

                        val data = it.data ?: emptyList()
                        adapterProduct.addItems(data)

                    }
                    State.ERROR -> {
                        binding.pdmenuproduct.toGone()
                        toastError(it?.message ?: "Error")
                    }
                    State.LOADING -> {
                        binding.pdmenuproduct.toVisible()
                    }
                }
            }
        }
    }

    private fun setDataProductAll(){
        val qrCodeResult = intent.getStringExtra("qrCodeResult")
        qrCodeResult?.let { qrcode ->
            ProductviewModel.getProductDetailAll(qrcode.toInt()).observe(this) {
                when (it.state) {
                    State.SUCCESS -> {

                        val data = it.data ?: emptyList()
                        adapterProduct.addItems(data)

                    }
                    State.ERROR -> {
                        toastError(it?.message ?: "Error")
                    }
                    State.LOADING -> {
                        binding.pdmenuproduct.toVisible()
                    }
                }
            }
        }
    }

    private fun Tambah() {

        val qrCodeResult = intent.getStringExtra("qrCodeResult")

        val reqData = Rating(
            userId = Pref.getUser()?.id?:0,
            tempatId = qrCodeResult?.toInt(),
        )

        viewModelDetail.updateJumlah(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {

                }
                State.ERROR -> {

                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun TambahPlaceAge() {

        val qrCodeResult = intent.getStringExtra("qrCodeResult")

        val reqData = Pengunjung(
            userId = Pref.getUser()?.id?:0,
            tempatId = qrCodeResult?.toInt(),
        )

        viewModelDetail.addPlaceAge(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {

                }
                State.ERROR -> {

                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun cekUlasan() {

        val qrCodeResult = intent.getStringExtra("qrCodeResult")

        val reqData = Rating(
            userId = Pref.getUser()?.id,
            tempatId = qrCodeResult?.toInt(),
        )
        viewModelDetail.cekUlasan(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.btnRating.toGone()
                    showToast("Ayo ubah ulasan tempat mu")
                }
                State.ERROR -> {
                    confirmReview()
                    binding.btnRating.toVisible()
                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun confirmReview() {
        showConfirmDialog(
            "Ayo Review Tempat",
            "Kamu belum melakukan review tempat, ayok berikan review anda",
            "Ok"
        ) {
            val qrCodeResult = intent.getStringExtra("qrCodeResult")
            val intent = Intent(this@DetailOneTempatActivity, AddUlasanActivity::class.java)
            val Id = qrCodeResult?.toInt()
            intent.putExtra("id", Id)
            startActivity(intent)
        }
    }

    private fun UpdateTotal() {

        val qrCodeResult = intent.getStringExtra("qrCodeResult")

        val reqData = Rating(
            tempatId = qrCodeResult?.toInt(),
        )
        viewModelDetail.updateTotal(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {

                }
                State.ERROR -> {

                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun addCartProduct(product: MenuProduct){
        val qrCodeResult = intent.getStringExtra("qrCodeResult")
        val user = Pref.getUser()?.id
        val reqData = KeranjangProduct(
            userId = user,
            tempatId = qrCodeResult?.toInt(),
            productId = product.id
        )

        viewModelCartProduct.addKeranjangProduct(reqData).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    showToast("Berhasil di tambah ke keranjang")
                }
                State.ERROR -> {
                    showErrorDialog(it.message.defaultError())
                }
                State.LOADING -> {

                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}