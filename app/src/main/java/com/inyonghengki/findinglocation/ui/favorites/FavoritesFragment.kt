package com.inyonghengki.findinglocation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inyonghengki.findinglocation.core.data.source.model.Favorite
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.Slider
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.FragmentFavoritesBinding
import com.inyonghengki.findinglocation.ui.base.MyFragment
import com.inyonghengki.findinglocation.ui.favorites.adapter.FavoriteAdapter
import com.inyonghengki.findinglocation.ui.home.adapter.RekomendasiAdapter
import com.inyonghengki.findinglocation.ui.slider.AddSliderAdminActivity
import com.inyonghengki.findinglocation.ui.tempat.DetailTempatActivity
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyonghengki.findinglocation.util.Pref
import com.inyonghengki.findinglocation.util.defaultError
import com.inyonghengki.findinglocation.util.getTempatId
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.remove
import com.inyongtisto.myhelper.extension.showConfirmDialog
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : MyFragment() {

    private var _binding: FragmentFavoritesBinding? = null

    private val viewModelFavorite: FavoriteViewModel by viewModel()
    private val viewModelTempat: TempatViewModel by viewModel()
    private val adapterFavorite = FavoriteAdapter(
        onClick = {
            detailTempat(it)
                  },
        onDelete = { item, pos ->
            confirmDelete(item, pos)
        }
    )



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainButton()
        setData()
        setUpAdapter()

        return root
    }

    private fun setUpAdapter(){
        binding.apply {
            rvFavorite.adapter = adapterFavorite
        }
    }

    private fun mainButton(){

    }



    private fun confirmDelete(item: Favorite, pos: Int) {
        showConfirmDialog(
            "Delete Favorit ",
            "Apakah anda yakin ingin menghapus data ini?",
            "Delete"
        ) {
            onDelete(item, pos)
        }
    }

    private fun onDelete(item: Favorite, pos: Int) {
        viewModelFavorite.delete(item.id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    adapterFavorite.removeAt(pos)
                    progress.dismiss()
                    toastSuccess("Slider berhasil di hapus")
                }

                State.ERROR -> {
                    showErrorDialog(it.message.defaultError())
                    progress.dismiss()
                }

                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun setData(){
        val userID = Pref.getUser()?.id
        viewModelFavorite.get(userID).observe(requireActivity()) {
            when (it.state) {
                State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    adapterFavorite.addItems(data)
                }
                State.ERROR -> {
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun detailTempat(tempat: Favorite) {

        viewModelTempat.getOne(tempat.placeId).observe(requireActivity()){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}