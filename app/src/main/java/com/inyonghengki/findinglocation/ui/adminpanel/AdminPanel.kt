package com.inyonghengki.findinglocation.ui.adminpanel

import android.os.Bundle
import com.inyonghengki.findinglocation.databinding.ActivityAdminpanelBinding
import com.inyonghengki.findinglocation.ui.adminpanel.tempat.ListPlaceForConfirmation
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.category.ListCategoryAdminActivity
import com.inyonghengki.findinglocation.ui.categoryfasilitas.ListCategoryFasilitasAdminActivity
import com.inyonghengki.findinglocation.ui.categoryproduct.ListCategoryProductAdminActivity
import com.inyonghengki.findinglocation.ui.slider.ListSliderAdminActivity
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar

class AdminPanel : MyActivity() {

    private var _binding: ActivityAdminpanelBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAdminpanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Admin Panel")

        setData()
        mainButton()
    }
    private fun mainButton(){
        binding.apply {

            rvCatetempat.setOnClickListener {
                intentActivity(ListCategoryAdminActivity::class.java)
            }

            rvSlider.setOnClickListener {
                intentActivity(ListSliderAdminActivity::class.java)
            }

            rvKonfirtempat.setOnClickListener {
                intentActivity(ListPlaceForConfirmation::class.java)
            }

            rvMenuproduct.setOnClickListener {
                intentActivity(ListCategoryProductAdminActivity::class.java)
            }

            rvMenufas.setOnClickListener {
                intentActivity(ListCategoryFasilitasAdminActivity::class.java)
            }

        }
    }
    private fun setData(){

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}