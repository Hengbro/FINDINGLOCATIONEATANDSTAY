package com.inyonghengki.findinglocation.core.data.source.remote.network

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
import com.inyonghengki.findinglocation.core.data.source.model.Slider
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.request.LoginRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.RegistrasiRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.SliderRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.UpdateProfileRequest
import com.inyonghengki.findinglocation.core.data.source.remote.response.BaseListResponse
import com.inyonghengki.findinglocation.core.data.source.remote.response.BaseSingleResponse
import com.inyonghengki.findinglocation.core.data.source.remote.response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path


interface ApiService {

    @GET("home")
    suspend fun getHome():Response<BaseListResponse<Tempat>>

    @GET("newplace")
    suspend fun getNew():Response<BaseListResponse<Tempat>>

    @GET("find/{cari}")
    suspend fun getFind(
        @Path("cari") cari :String? = null,
    ):Response<BaseListResponse<Tempat>>

    @GET("findRelated/{cari}")
    suspend fun getRelated(
        @Path("cari") cari :String? = null,
    ):Response<BaseListResponse<Tempat>>

    @GET("homeslider")
    suspend fun getHomeSlider():Response<BaseListResponse<Slider>>

    @GET("show-place-age/{userId}")
    suspend fun getShowPlaceAge(
        @Path("userId") id: Int? = null,
    ):Response<BaseListResponse<Pengunjung>>

    @GET("homekategori")
    suspend fun getHomeCategory():Response<BaseListResponse<Category>>

    @POST("login")
    suspend fun login(
        @Body login: LoginRequest,
    ): Response<LoginResponse>

    // "https://127.0.0.1:8000/api/register"
    @POST("register")
    suspend fun register(
        @Body data: RegistrasiRequest,
    ): Response<LoginResponse>

    @PUT("update-profil/{id}")
    suspend fun updateProfil(
        @Path("id")int: Int? = null,
        @Body data: UpdateProfileRequest,
    ):Response<LoginResponse>

    @Multipart
    @POST("upload-imguser/{id}")
    suspend fun uploadImgUser(
        @Path("id")int: Int? = null,
        @Part fileImage: MultipartBody.Part? = null
    ):Response<LoginResponse>

    @GET("tempat/{id}")
    suspend fun getTempat(
        @Path("id")id: Int? = null,
    ):Response<BaseListResponse<Tempat>>

    @POST("tempat")
    suspend fun store(
        @Body data: Tempat,
    ): Response<BaseSingleResponse<Tempat>>

    @PUT("tempat/{id}")
    suspend fun updateTempat(
        @Path("id")id: Int? = null,
        @Body data: Tempat,
    ):Response<BaseSingleResponse<Tempat>>

    @DELETE("tempat/{id}")
    suspend fun deleteTempat(
        @Path("id") idTempat: Int? = null,
    ):Response<BaseSingleResponse<Tempat>>

    @GET("view-confirmation-place")
    suspend fun viewConfirmation(): Response<BaseListResponse<Tempat>>

    @PUT("confirmation-place/{id}")
    suspend fun updateConfirmation(
        @Path("id")id: Int? = null,
        @Body data: Tempat,
    ):Response<BaseSingleResponse<Tempat>>

    @DELETE("delete-place/{id}")
    suspend fun deletePlace(
        @Path("id") idTempat: Int? = null,
    ):Response<BaseSingleResponse<Tempat>>

    @GET("tempat-detail/{id}")
    suspend fun getOne(
        @Path("id") idTempat: Int? = null,
    ):Response<BaseSingleResponse<Tempat>>

    @GET("view-detail-place/{id}")
    suspend fun getView(
        @Path("id") idTempat: Int? = null,
    ):Response<BaseSingleResponse<Tempat>>

    @Multipart
    @POST("upload-imageTe")
    suspend fun uploadImageTemp(
        @Part fileImage: MultipartBody.Part? = null
    ):Response<BaseSingleResponse<String>>

    @Multipart
    @POST("upload-imagePe")
    suspend fun uploadImagePemilik(
        @Part fileImage: MultipartBody.Part? = null
    ):Response<BaseSingleResponse<String>>

    @GET("tempat-user/{id}")
    suspend fun cekTempat(
        @Path("id")int: Int? = null,
    ):Response<LoginResponse>

    @GET("lokasi-tempat/{id}")
    suspend fun getLokasiTempat(
        @Path("id")idTempat: Int? = null,
    ):Response<BaseListResponse<LokasiTempat>>

    @POST("lokasi-tempat")
    suspend fun addLokasiTempat(
        @Body data: LokasiTempat,
    ):Response<BaseSingleResponse<LokasiTempat>>

    @PUT("lokasi-tempat/{id}")
    suspend fun updateLokasiTempat(
        @Path("id")id: Int? = null,
        @Body data: LokasiTempat,
    ):Response<BaseSingleResponse<LokasiTempat>>

    @DELETE("lokasi-tempat/{id}")
    suspend fun deleteLokasitempat(
        @Path("id") idLokasiTemp: Int? = null,
    ):Response<BaseSingleResponse<LokasiTempat>>

    @GET("product-place/{id}")
    suspend fun getMenuProduct(
        @Path("id")idTempat: Int? = null,
    ):Response<BaseListResponse<MenuProduct>>

    @GET("product-placedetail/{id}")
    suspend fun getProductDetail(
        @Path("id")idTempat: Int? = null,
    ):Response<BaseListResponse<MenuProduct>>

    @GET("product-place/{id}")
    suspend fun getProductDetailAll(
        @Path("id")idTempat: Int? = null,
    ):Response<BaseListResponse<MenuProduct>>

    @POST("product-place")
    suspend fun addMenuProduct(
        @Body data: MenuProduct,
    ):Response<BaseSingleResponse<MenuProduct>>

    @PUT("product-place/{id}")
    suspend fun updateMenuProduct(
        @Path("id")id: Int? = null,
        @Body data: MenuProduct,
    ):Response<BaseSingleResponse<MenuProduct>>

    @DELETE("product-place/{id}")
    suspend fun deleteMenuProduct(
        @Path("id") idMenuProduct: Int? = null,
    ):Response<BaseSingleResponse<MenuProduct>>

    @Multipart
    @POST("upload-image")
    suspend fun uploadImage(
        @Part fileImage: MultipartBody.Part? = null
    ):Response<BaseSingleResponse<String>>

    @GET("get-place-by-fasilitas/{id}")
    suspend fun getPlaceByFas(
        @Path("id")id: Int? = null,
    ):Response<BaseListResponse<MenuFasilitas>>

    @GET("fasilitas-place")
    suspend fun getMenuFasilitasAll():Response<BaseListResponse<MenuFasilitas>>

    @GET("fasilitas-place/{id}")
    suspend fun getMenuFasilitas(
        @Path("id")idTemp: Int? = null,
    ):Response<BaseListResponse<MenuFasilitas>>

    @GET("fasilitas-place/{id}")
    suspend fun getMenuFasilitasDetailAll(
        @Path("id")idTempat: Int? = null,
    ):Response<BaseListResponse<MenuFasilitas>>

    @GET("fasilitas-placedetail/{id}")
    suspend fun getFasilitasDetail(
        @Path("id")idTempat: Int? = null,
    ):Response<BaseListResponse<MenuFasilitas>>

    @POST("fasilitas-place")
    suspend fun addMenuFasilitas(
        @Body data: MenuFasilitas,
    ):Response<BaseSingleResponse<MenuFasilitas>>

    @PUT("fasilitas-place/{id}")
    suspend fun updateMenuFasilitas(
        @Path("id")id: Int? = null,
        @Body data: MenuFasilitas,
    ):Response<BaseSingleResponse<MenuFasilitas>>

    @DELETE("fasilitas-place/{id}")
    suspend fun deleteMenuFasilitas(
        @Path("id") id: Int? = null,
    ):Response<BaseSingleResponse<MenuFasilitas>>

    @Multipart
    @POST("upload-imageFa")
    suspend fun uploadImageFa(
        @Part fileImage: MultipartBody.Part? = null
    ):Response<BaseSingleResponse<String>>

    @GET("category-show")
    suspend fun getCategory(): Response<BaseListResponse<Category>>

    @POST("category")
    suspend fun createCategory(
        @Body data: Category
    ): Response<BaseSingleResponse<Category>>

    @PUT("category/{id}")
    suspend fun updateCategory(
        @Path("id") id: Int? = null,
        @Body data: Category
    ): Response<BaseSingleResponse<Category>>

    @DELETE("category/{id}")
    suspend fun deleteCategory(
        @Path("id") id: Int? = null
    ): Response<BaseSingleResponse<Category>>

    @GET("category-product")
    suspend fun getCategoryProduct(): Response<BaseListResponse<CategoryProduct>>

    @POST("category-product")
    suspend fun createCategoryProduct(
        @Body data: CategoryProduct
    ): Response<BaseSingleResponse<CategoryProduct>>

    @PUT("category-product/{id}")
    suspend fun updateCategoryProduct(
        @Path("id") id: Int? = null,
        @Body data: CategoryProduct
    ): Response<BaseSingleResponse<CategoryProduct>>

    @DELETE("category-product/{id}")
    suspend fun deleteCategoryProduct(
        @Path("id") id: Int? = null
    ): Response<BaseSingleResponse<CategoryProduct>>

    @GET("category-fasilitas")
    suspend fun getCategoryFasilitas(): Response<BaseListResponse<CategoryFasilitas>>

    @POST("category-fasilitas")
    suspend fun createCategoryFasilitas(
        @Body data: CategoryFasilitas
    ): Response<BaseSingleResponse<CategoryFasilitas>>

    @PUT("category-fasilitas/{id}")
    suspend fun updateCategoryFasilitas(
        @Path("id") id: Int? = null,
        @Body data: CategoryFasilitas
    ): Response<BaseSingleResponse<CategoryFasilitas>>

    @DELETE("category-fasilitas/{id}")
    suspend fun deleteCategoryFasilitas(
        @Path("id") id: Int? = null
    ): Response<BaseSingleResponse<CategoryFasilitas>>

    @GET("slider")
    suspend fun getSlider(): Response<BaseListResponse<Slider>>

    @POST("slider")
    suspend fun createSlider(
        @Body data: SliderRequest
    ): Response<BaseSingleResponse<Slider>>

    @PUT("slider/{id}")
    suspend fun updateSlider(
        @Path("id") id: Int? = null,
        @Body data: SliderRequest
    ): Response<BaseSingleResponse<Slider>>

    @DELETE("slider/{id}")
    suspend fun deleteSlider(
        @Path("id") id: Int? = null
    ): Response<BaseSingleResponse<Slider>>



    @Multipart
    @POST("upload/{path}")
    suspend fun uploadImages(
        @Path("path")id: String? = null,
        @Part fileImage: MultipartBody.Part? = null
    ):Response<BaseSingleResponse<String>>



    @GET("rating")
    suspend fun getUlasan(): Response<BaseListResponse<Rating>>

    @GET("rating-place/{id}")
    suspend fun getRating(
        @Path("id") id :Int? = null,
    ):Response<BaseListResponse<Rating>>

    @GET("rating-placeAll/{id}")
    suspend fun getRatingAll(
        @Path("id") id :Int? = null,
    ):Response<BaseListResponse<Rating>>

    @GET("review-ulasan-pribadi/{userId}")
    suspend fun getReviewSolo(
        @Path("userId") userId :Int? = null,
    ):Response<BaseListResponse<Rating>>

    @GET("review-ulasan-potensi/{tempatId}")
    suspend fun getReviewPotensi(
        @Path("tempatId") tempatId :Int? = null,
    ):Response<BaseListResponse<Rating>>

    @GET("review-ulasan-nopotensi/{tempatId}")
    suspend fun getReviewNoPotensi(
        @Path("tempatId") tempatId :Int? = null,
    ):Response<BaseListResponse<Rating>>

    @POST("chek-review")
    suspend fun cekUlasan(
        @Body data: Rating
    ): Response<BaseSingleResponse<Rating>>

    @POST("rating")
    suspend fun createUlasan(
        @Body data: Rating
    ): Response<BaseSingleResponse<Rating>>

    @PUT("rating/{id}")
    suspend fun updateUlasan(
        @Path("id") id: Int? = null,
        @Body data: Rating
    ): Response<BaseSingleResponse<Rating>>

    @PUT("review")
    suspend fun updateJumlah(
        @Body data: Rating
    ): Response<BaseSingleResponse<Rating>>

    @PUT("totalReview")
    suspend fun updateTotal(
        @Body data: Rating
    ): Response<BaseSingleResponse<Tempat>>

    @GET("cart-Favorite/{id}")
    suspend fun getFavorite(
        @Path("id") id :Int? = null,
    ):Response<BaseListResponse<Favorite>>

    @POST("cek-favorit")
    suspend fun cekFavorite(
        @Body data: Favorite
    ): Response<BaseSingleResponse<Favorite>>

    @POST("cart-Favorite")
    suspend fun createFavorite(
        @Body data: Favorite
    ): Response<BaseSingleResponse<Favorite>>

    @PUT("cart-Favorite/{id}")
    suspend fun updateFavorite(
        @Path("id") id: Int? = null,
        @Body data: Favorite
    ): Response<BaseSingleResponse<Favorite>>

    @DELETE("cart-Favorite/{id}")
    suspend fun deleteFavorite(
        @Path("id") id: Int? = null
    ): Response<BaseSingleResponse<Favorite>>


    //keranjang product
    @GET("view-by-userId/{userId}")
    suspend fun getCartByIdUser(
        @Path("userId") userId :Int? = null,
    ):Response<BaseListResponse<KeranjangProduct>>

    @GET("view-order-by-place/{tempatId}")
    suspend fun getCartByIdPlace(
        @Path("tempatId") tempatId :Int? = null,
    ):Response<BaseListResponse<KeranjangProduct>>

    @GET("view-by-order/{userId}")//tampil produk yang di beli berdasarkan isOrder true
    suspend fun getByOrder(
        @Path("userId") userId :Int? = null,
    ):Response<BaseListResponse<KeranjangProduct>>

    @POST("keranjang-product")
    suspend fun createCart(
        @Body data: KeranjangProduct
    ): Response<BaseSingleResponse<KeranjangProduct>>

    @PUT("order/{userId}")//memesan dengan ubah isActive jadi false
    suspend fun createOrder(
        @Path("userId") userId: Int? = null
    ): Response<BaseSingleResponse<KeranjangProduct>>

    @GET("view-history-transaksi/{cartPlace}")//tampil produk yang di beli berdasarkan isOrder true
    suspend fun getByCartId(
        @Path("cartPlace") cartPlaceId :Int? = null,
    ):Response<BaseListResponse<KeranjangProduct>>

    @PUT("addId-CartPlace")//selesai
    suspend fun updateCartPlace(
        @Body data: KeranjangProduct
    ): Response<BaseSingleResponse<KeranjangProduct>>

    @PUT("finish-Konfir/{userId}")//selesai order dengan ubah isOrder jadi false
    suspend fun konfirPesanan(
        @Path("userId") userId: Int? = null
    ): Response<BaseSingleResponse<KeranjangProduct>>

    @DELETE("keranjang-product/{id}")
    suspend fun deleteProduk(
        @Path("id") id: Int? = null
    ): Response<BaseSingleResponse<KeranjangProduct>>

    //keranjang tempat
    @GET("view-bayar/{userId}")//tampil ketika user cek total bayar di feedBack
    suspend fun getBuy(
        @Path("userId") userId :Int? = null,
    ):Response<BaseListResponse<KeranjangTempat>>

    @GET("view-konfir-bayar/{tempatId}")//tampil di menu pesanan tempat
    suspend fun getBuyPlace(
        @Path("tempatId") tempatId :Int? = null,
    ):Response<BaseListResponse<KeranjangTempat>>

    @GET("view-history-user/{userId}")//tampil ketika user cek total bayar di feedBack
    suspend fun getHistoryUser(
        @Path("userId") userId :Int? = null,
    ):Response<BaseListResponse<KeranjangTempat>>

    @GET("view-histroy-place/{tempatId}}")//tampil di menu pesanan tempat
    suspend fun getHistoryPlace(
        @Path("tempatId") tempatId :Int? = null,
    ):Response<BaseListResponse<KeranjangTempat>>

    @POST("keranjang-place")//add kedalam keranjang tempat
    suspend fun createReallyBuy(
        @Body data: KeranjangTempat
    ): Response<BaseSingleResponse<KeranjangTempat>>

    @PUT("konfir-status/{id}")//ubah status menjadi sudah bayar
    suspend fun confirBuy(
        @Path("id") id: Int? = null
    ): Response<BaseSingleResponse<KeranjangTempat>>

    @POST("pengunjung-place")//add kedalam keranjang tempat
    suspend fun createAgePlace(
        @Body data: Pengunjung
    ): Response<BaseSingleResponse<Pengunjung>>

    //history search user
    @GET("show-search-by-userId/{userId}")//tampil ketika jika terdapat history user
    suspend fun getHistorySearch(
        @Path("userId") userId :Int? = null,
    ):Response<BaseListResponse<HistorySearch>>

    @POST("history-search")//add kedalam riwayat pencarian
    suspend fun createHisSerach(
        @Body data: HistorySearch
    ): Response<BaseSingleResponse<HistorySearch>>

    @DELETE("history-search/{id}")
    suspend fun deleteHisSearach(
        @Path("id") id: Int? = null
    ): Response<BaseSingleResponse<HistorySearch>>
}