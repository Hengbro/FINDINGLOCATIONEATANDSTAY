package com.inyonghengki.findinglocation.core.data.source.remote

import com.inyonghengki.findinglocation.core.data.source.model.Category
import com.inyonghengki.findinglocation.core.data.source.model.CategoryFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.CategoryProduct
import com.inyonghengki.findinglocation.core.data.source.model.Favorite
import com.inyonghengki.findinglocation.core.data.source.model.HistorySearch
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangProduct
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangTempat
import com.inyonghengki.findinglocation.core.data.source.model.LokasiTempat
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.MenuProduct
import com.inyonghengki.findinglocation.core.data.source.model.Pengunjung
import com.inyonghengki.findinglocation.core.data.source.model.Rating
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.ApiService
import com.inyonghengki.findinglocation.core.data.source.remote.request.LoginRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.RegistrasiRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.SliderRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.UpdateProfileRequest
import com.inyonghengki.findinglocation.util.getTempatId
import okhttp3.MultipartBody

class RemoteDataSource(val api: ApiService) {

    //untuk login
    suspend fun  login(data: LoginRequest) = api.login(data)
    suspend fun  register(data: RegistrasiRequest) = api.register(data)
    suspend fun  updateProfil(data: UpdateProfileRequest) = api.updateProfil(data.id, data)
    suspend fun  uploadImgUser(id: Int? = null, fileImage: MultipartBody.Part? = null) = api.uploadImgUser(id, fileImage)

    //tampilan home
    suspend fun  getHome() = api.getHome()
    suspend fun  getNew() = api.getNew()
    suspend fun  getHomeSlider() = api.getHomeSlider()
    suspend fun  getPlaceAge(id: Int? = null) = api.getShowPlaceAge(id)
    suspend fun  getPlaceByFas(id: Int? = null) = api.getPlaceByFas(id)
    suspend fun  getCate() = api.getHomeCategory()

    //perintah untuk detail tempat
    suspend fun  getTempat(id: Int? = null,) = api.getTempat(id)
    suspend fun  getOne(id: Int?) = api.getOne(id)
    suspend fun  getView(id: Int?) = api.getView(id)
    suspend fun  store(data: Tempat) = api.store(data)
    suspend fun  updateTempat(data: Tempat) = api.updateTempat(data.id, data)
    suspend fun  deleteTempat(id: Int?) = api.deleteTempat(id)
    suspend fun  viewConfirmation() = api.viewConfirmation()
    suspend fun  updateConfirmation(id: Int?, data: Tempat) = api.updateConfirmation(id, data)
    suspend fun  deletePlace(id: Int?) = api.deletePlace(id)
    suspend fun  uploadImageTempat(fileImage: MultipartBody.Part? = null) = api.uploadImageTemp(fileImage)
    suspend fun  uploadImagePemilik(fileImage: MultipartBody.Part? = null) = api.uploadImagePemilik(fileImage)
    suspend fun  cekTempat(id: Int? = null) = api.cekTempat(id)

    //perintah untuk lokasi dari pengguna dan tempat
    suspend fun  getLokasiTempat() = api.getLokasiTempat(getTempatId())
    suspend fun  addLokasiTempat(data: LokasiTempat) = api.addLokasiTempat(data)
    suspend fun  updateLokasiTempat(data: LokasiTempat) = api.updateLokasiTempat(data.id, data)
    suspend fun  deleteLokasiTempat(id: Int?) = api.deleteLokasitempat(id)

    //perintah untuk tabel product tempat
    suspend fun  getMenuProduct() = api.getMenuProduct(getTempatId())
    suspend fun  getProductDetail(id: Int? = null) = api.getProductDetail(id)
    suspend fun  getProductDetailAll(id: Int? = null) = api.getProductDetailAll(id)
    suspend fun  getFind(cari: String?) = api.getFind(cari)
    suspend fun  getRelated(cari: String?) = api.getRelated(cari)
    suspend fun  addMenuProduct(data: MenuProduct) = api.addMenuProduct(data)
    suspend fun  updateMenuProduct(data: MenuProduct) = api.updateMenuProduct(data.id, data)
    suspend fun  deleteMenuProduct(id: Int?) = api.deleteMenuProduct(id)
    suspend fun  uploadImage(fileImage: MultipartBody.Part? = null) = api.uploadImage(fileImage)

    //perintah tabel fasilitas dari tempat
    suspend fun  getMenuFasilitasAll() = api.getMenuFasilitasAll()
    suspend fun  getMenuFasilitas() = api.getMenuFasilitas(getTempatId())
    suspend fun  getFasilitasDetail(id: Int? = null) = api.getFasilitasDetail(id)
    suspend fun  getFasilitasDetailAll(id: Int? = null) = api.getMenuFasilitasDetailAll(id)
    suspend fun  addMenuFasilitas(data: MenuFasilitas) = api.addMenuFasilitas(data)
    suspend fun  updateMenuFasilitas(data: MenuFasilitas) = api.updateMenuFasilitas(data.id, data)
    suspend fun  deleteMenuFasilitas(id: Int?) = api.deleteMenuFasilitas(id)
    suspend fun  uploadImageFa(fileImage: MultipartBody.Part? = null) = api.uploadImageFa(fileImage)

    //perintah tabel category dari tempat
    suspend fun  getCategory() = api.getCategory()
    suspend fun  addCategory(data: Category) = api.createCategory(data)
    suspend fun  updateCategory(data: Category) = api.updateCategory(data.id, data)
    suspend fun  deleteCategory(id: Int?) = api.deleteCategory(id)

    suspend fun  getCategoryProduct() = api.getCategoryProduct()
    suspend fun  addCategoryProduct(data: CategoryProduct) = api.createCategoryProduct(data)
    suspend fun  updateCategoryProduct(data: CategoryProduct) = api.updateCategoryProduct(data.id, data)
    suspend fun  deleteCategoryProduct(id: Int?) = api.deleteCategoryProduct(id)

    suspend fun  getCategoryFasilitas() = api.getCategoryFasilitas()
    suspend fun  addCategoryFasilitas(data: CategoryFasilitas) = api.createCategoryFasilitas(data)
    suspend fun  updateCategoryProduct(data: CategoryFasilitas) = api.updateCategoryFasilitas(data.id, data)
    suspend fun  deleteCategoryFasilitas(id: Int?) = api.deleteCategoryFasilitas(id)

    //perintah untuk slider pada home
    suspend fun  getSlider() = api.getSlider()
    suspend fun  addSlider(data: SliderRequest) = api.createSlider(data)
    suspend fun  updateSlider(data: SliderRequest) = api.updateSlider(data.id, data)
    suspend fun  deleteSlider(id: Int?) = api.deleteSlider(id)

    //perintah untuk rating dan mereview
    suspend fun  getUlasan() = api.getUlasan()
    suspend fun  getRating(id: Int? = null) = api.getRating(id)
    suspend fun  getRatingAll(id: Int? = null) = api.getRatingAll(id)
    suspend fun  getReviewSolo(userId: Int? = null) = api.getReviewSolo(userId)
    suspend fun  getReviewPotensi(tempatId: Int? = null) = api.getReviewPotensi(tempatId)
    suspend fun  getReviewNoPotensi(tempatId: Int? = null) = api.getReviewNoPotensi(tempatId)
    suspend fun  cekUlasan(data: Rating) = api.cekUlasan(data)
    //melakukan rating pada tempat
    suspend fun  addUlasan(data: Rating) = api.createUlasan(data)
    suspend fun  updateJumlah(data: Rating) = api.updateJumlah(data)
    suspend fun  updateTotal(data: Rating) = api.updateTotal(data)
    suspend fun  updateUlasan(data: Rating) = api.updateUlasan(data.id, data)

    //perintah crud untuk melakukan favorite
    suspend fun  getFavorite(id: Int? = null) = api.getFavorite(id)
    suspend fun  cekFavorite(data: Favorite) = api.cekFavorite(data)
    suspend fun  addFavorite(data: Favorite) = api.createFavorite(data)
    suspend fun  updateFavorite(data: Favorite) = api.updateFavorite(data.id, data)
    suspend fun  deleteFavorite(id: Int?) = api.deleteFavorite(id)

    //perintah untuk keranjang product
    suspend fun  getCartByIdUser(userId: Int? = null) = api.getCartByIdUser(userId)
    suspend fun  getCartByIdPlace(tempatId: Int? = null) = api.getCartByIdPlace(tempatId)
    suspend fun  getByOrder(userId: Int? = null) = api.getByOrder(userId)
    suspend fun  getByCartPlaceId(cartPlaceId: Int? = null) = api.getByCartId(cartPlaceId)
    suspend fun  updateCartPlace(data: KeranjangProduct) = api.updateCartPlace(data)
    suspend fun  addKeranjangProduct(data: KeranjangProduct) = api.createCart(data)
    suspend fun  createOrder(userId :Int? = null) = api.createOrder(userId)
    suspend fun  konfirmasiOrder(userId :Int? = null) = api.konfirPesanan(userId)
    suspend fun  deleteProduk(id: Int?) = api.deleteProduk(id)

    //perintah untuk keranjang tempat
    suspend fun  getBuyByUser(userId: Int? = null) = api.getBuy(userId)
    suspend fun  getBuyByPlace(tempatId: Int? = null) = api.getBuyPlace(tempatId)
    suspend fun  getHistoryUser(userId: Int? = null) = api.getHistoryUser(userId)
    suspend fun  getHistoryPlace(tempatId: Int? = null) = api.getHistoryPlace(tempatId)
    suspend fun  addKeranjangTempat(data: KeranjangTempat) = api.createReallyBuy(data)
    suspend fun  confirmationBuy(id :Int? = null) = api.confirBuy(id)

    suspend fun  addPlaceAge(data: Pengunjung) = api.createAgePlace(data)

    // history search user
    suspend fun  getHisSearch(userId: Int? = null) = api.getHistorySearch(userId)
    suspend fun  addHisSearch(data: HistorySearch) = api.createHisSerach(data)
    suspend fun  delHisSearch(id :Int? = null) = api.deleteHisSearach(id)

    suspend fun  uploadImages(path : String, fileImage: MultipartBody.Part? = null) =
        api.uploadImages(path, fileImage)
}