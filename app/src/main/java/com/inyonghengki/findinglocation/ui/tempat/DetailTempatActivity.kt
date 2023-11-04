package com.inyonghengki.findinglocation.ui.tempat

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.inyonghengki.findinglocation.core.data.source.model.Favorite
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityDetailTempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.detail.DetailViewModel
import com.inyonghengki.findinglocation.ui.favorites.FavoriteViewModel
import com.inyonghengki.findinglocation.ui.favorites.FavoritesFragment
import com.inyonghengki.findinglocation.ui.home.HomeViewModel
import com.inyonghengki.findinglocation.ui.menufasilitas.MenuFasilitasViewModel
import com.inyonghengki.findinglocation.ui.menuproduct.MenuProductViewModel
import com.inyonghengki.findinglocation.ui.searchplace.SearchActivity
import com.inyonghengki.findinglocation.ui.tempat.adapter.FasilitasAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.ImageTempatSliderAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.ProdukTempatAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.ProdukTempatAllAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.RekomendasiAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.RelatedAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.ReviewPlaceAdapter
import com.inyonghengki.findinglocation.ui.tempat.adapter.ReviewPlaceAllAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setDefaultColor
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("SetTextI18n")
class DetailTempatActivity : MyActivity() {

    private val FasilitasviewModel : MenuFasilitasViewModel by viewModel()
    private val ProductviewModel : MenuProductViewModel by viewModel()
    private val HomeviewModel : HomeViewModel by viewModel()
    private val FavoritesViewModel : FavoriteViewModel by viewModel()
    private val viewModelTempat: TempatViewModel by viewModel()
    private val viewModelReview: DetailViewModel by viewModel()

    private var _binding: ActivityDetailTempatBinding? = null
    private val binding get() = _binding!!
    private val tempat by extra<Tempat>()
    private val favorit by extra<Favorite>()
    private val adapter = ImageTempatSliderAdapter()
    private val adapterProduct = ProdukTempatAdapter()
    private val adapterProductAll = ProdukTempatAllAdapter()
    private val adapterFasilitas = FasilitasAdapter()
    private val adapterReview = ReviewPlaceAdapter()
    private val adapterReviewAll = ReviewPlaceAllAdapter()

    private val adapterRekomendasi = RekomendasiAdapter{
        detailTempat(it)
    }

    private val adapterRelated = RelatedAdapter{
        detailTempat(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTempatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpData()
        mainButton()
        setAdapter()
        setDataFasilitas()
        setDataProduct()
        setDataRekomendasi()
        setDataRelated()
        setupAdepter()
        CekFavorite()
        getReview()

    }

    private fun setupAdepter(){
        binding.apply {
            rvTerkait.adapter = adapterRelated
            rvTempatRekomend.adapter = adapterRekomendasi
            rvFasilitas.adapter = adapterFasilitas
            rvMenuproduct.adapter = adapterProduct
            rvFasilitasAll.adapter = adapterFasilitas
            rvProductAll.adapter = adapterProductAll
            rvPdulasan.adapter = adapterReview
            rvReviewAll.adapter = adapterReview
        }
    }

    private fun mainButton(){
        binding.apply {

            swipRefresh.setDefaultColor()
            swipRefresh.setOnClickListener{
                setDataFasilitas()
                setDataProduct()
                setDataRekomendasi()
                setUpData()
                setAdapter()
                setDataRelated()
                getReview()
            }

            lySearch.setOnClickListener {
                intentActivity(SearchActivity::class.java)
            }

            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            imgFavoriteout.setOnClickListener {
                if ( favorit != null){
                    updateFavorite()
                }else {
                    TambahFavorite()
                }
                imgFavoritein.toVisible()
                imgFavoriteout.toGone()

            }

            imgFavoritein.setOnClickListener {
                intentActivity(FavoritesFragment::class.java)
            }

            viewAllpreview.setOnClickListener {
                lyReviewall.toVisible()
                getReviewAll()
            }

            imgClosefa.setOnClickListener {
                lyReviewall.toGone()
            }

            tvViewallproduct.setOnClickListener {
                lyProductall.toVisible()
                setDataProductAll()
            }

            tvViewallfasilitas.setOnClickListener {
                lyFasilitasall.toVisible()
                setDataFasilitasAll()
            }

            lyCall.setOnClickListener {
                callNumber()
            }

            lyEmail.setOnClickListener {
                sendEmail()
            }

            imgClosefa.setOnClickListener {
                lyFasilitasall.toGone()
            }

            imgClosepro.setOnClickListener {
                lyProductall.toGone()
            }

            imgCloseview.setOnClickListener {
                lyReviewall.toGone()
            }

            tvDeskAll.setOnClickListener {
                lyDeskripsiAll.toVisible()
            }

            imgCloseDesk.setOnClickListener {
                lyDeskripsiAll.toGone()
            }

            /*btnRute.setOnClickListener {
                val kata = tvAlamat.text.toString()
                val intent = Intent(this@DetailTempatActivity, MapsRute::class.java)
                intent.putExtra("kata",kata)
                startActivity(intent)
            }*/
        }
    }

    private fun setAdapter(){
        binding.apply {
            val image = tempat?.imageTempat?:""
            val splitImage = image.split("|")
            adapter.addItems(splitImage)
            sliderImage.adapter = adapter
        }
    }

    private fun callNumber() {
        val phoneNumber = binding.tvNotlp.text.toString()
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")

        if (dialIntent.resolveActivity(packageManager) != null) {
            startActivity(dialIntent)
        } else {
            Toast.makeText(this, "Tidak ada aplikasi telepon yang tersedia.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun sendEmail(){
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the email")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Isi pesan email")

        if (emailIntent.resolveActivity(packageManager) != null){
            startActivity(emailIntent)
        }

    }

    private fun updateFavorite() {

        val reqData = Favorite(
            id = favorit?.id,
            placeId = tempat?.id,
            userId = Pref.getUser()?.id?:0,
            isActive = true,
        )

        FavoritesViewModel.update(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil menambah data ke favorit")
                }
                State.ERROR -> {
                    progress.dismiss()
                    showErrorDialog(it.message.defaultError())
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun TambahFavorite() {

        val reqData = Favorite(
            placeId = tempat?.id,
            userId = Pref.getUser()?.id?:0,
        )

        FavoritesViewModel.add(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil menambahkan ke favorite")
                }
                State.ERROR -> {
                    progress.dismiss()
                    showErrorDialog(it.message.defaultError())
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun CekFavorite() {

        val reqData = Favorite(
            placeId = tempat?.id,
            userId = Pref.getUser()?.id?:0,
        )

        FavoritesViewModel.cek(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.imgFavoritein.toVisible()
                    binding.imgFavoriteout.toGone()
                    showToast("Salah satu tempat favoritmu")
                }
                State.ERROR -> {
                    binding.imgFavoriteout.toVisible()
                    showErrorDialog(it.message.defaultError())
                }
                State.LOADING -> {
                }
            }
        }
    }

    private fun getReview(){
        val tempatId = tempat?.id
        viewModelReview.getRating(tempatId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdulasan .toGone()
                    val data = it.data ?: emptyList()
                    adapterReview.addItems(data)
                }
                State.ERROR -> {
                    binding.pdulasan .toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.pdulasan .toVisible()
                }
            }
        }
    }

    private fun getReviewAll(){
        val tempatId = tempat?.id
        viewModelReview.getRatingAll(tempatId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    adapterReviewAll.addItems(data)
                }
                State.ERROR -> {
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {
                    binding.pdulasan .toVisible()
                }
            }
        }
    }

    private fun setUpData(){

        binding.apply {
            tempat?.let {
                tvNama.text = it.nameTempat
                tvKategori.text = it.kategori
                tvJambuka.text = it.openH +" - " +it.closeH
                tvNotlp.text = it.user?.phone
                tvEmail.text = it.user?.email
                tvKategori.text = it.category?.name ?: "Lainnya"
                tvAlamat.text = it.address?.alamat ?: "kota Medan"
                tvDeskripsi.text = it.deskription
                tvDeskAll.text = it.deskription
            }
        }
    }

    private fun setDataFasilitas(){
        val reqData = tempat?.id
        FasilitasviewModel.getFaDetail(reqData).observe(this) {
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

    private fun setDataProduct(){
        val reqData = tempat?.id
        ProductviewModel.getProductDetail(reqData).observe(this) {
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

    private fun setDataFasilitasAll(){
        val reqData = tempat?.id
        FasilitasviewModel.getFaDetailAll(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {

                    val data = it.data ?: emptyList()
                    adapterFasilitas.addItems(data)

                }
                State.ERROR -> {
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun setDataProductAll(){
        val reqData = tempat?.id
        ProductviewModel.getProductDetailAll(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    adapterProductAll.addItems(data)

                }
                State.ERROR -> {
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun setDataRelated(){
        val reqData = tempat?.kategori
        ProductviewModel.getRelated(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdTempatterkait.toGone()
                    binding.swipRefresh.isRefreshing = false

                    val data = it.data ?: emptyList()
                    adapterRelated.addItems(data)

                }
                State.ERROR -> {
                    binding.pdTempatterkait.toGone()
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {
                    binding.pdTempatterkait.toVisible()
                }
            }
        }
    }

    private fun setDataRekomendasi(){
        HomeviewModel.getHome().observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdTempatRekomend.toGone()
                    binding.swipRefresh.isRefreshing = false

                    val data = it.data ?: emptyList()
                    adapterRekomendasi.addItems(data)

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

    private fun detailTempat(tempat: Tempat) {

        viewModelTempat.getOne(tempat.id).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    intentActivity(DetailTempatActivity::class.java, it.data)
                }
                State.ERROR -> {
                    progress.dismiss()
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }

    }

}