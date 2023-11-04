package com.inyonghengki.findinglocation.ui.tempat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.inyonghengki.findinglocation.core.data.source.model.Slider
import com.inyonghengki.findinglocation.databinding.ItemImgaeTempatSliderBinding
import com.inyonghengki.findinglocation.databinding.ItemSliderBinding
import com.inyonghengki.findinglocation.util.toUrlPemilik
import com.inyonghengki.findinglocation.util.toUrlSlider
import com.inyonghengki.findinglocation.util.toUrlTempat
import com.inyongtisto.myhelper.extension.setImagePicasso

class ImagePemilikTempatSliderAdapter : PagerAdapter() {
    private val data: ArrayList<String> = ArrayList()

    override fun getCount() = data.size

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    fun addItems(items: List<String>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            ItemImgaeTempatSliderBinding.inflate(LayoutInflater.from(container.context), container, false)
        val item = data[position]

        binding.apply {
            imageTempat.setImagePicasso(item.toUrlPemilik())
        }

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
