package com.inyonghengki.findinglocation.ui.review.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.Rating
import com.inyonghengki.findinglocation.databinding.ItemDataReviewBinding
import com.inyonghengki.findinglocation.databinding.ItemDataReviewPribadyBinding
import com.inyonghengki.findinglocation.databinding.ItemFasilitasBinding
import com.inyonghengki.findinglocation.util.toUrlFasilitas
import com.inyongtisto.myhelper.extension.setImagePicasso


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class ReviewPotensiAdapter (

    val onClick:(item: Rating)-> Unit):
    RecyclerView.Adapter<ReviewPotensiAdapter.ViewHolder>(){

    private var data = ArrayList<Rating>()

    inner class ViewHolder(private val itemBinding: ItemDataReviewBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Rating, position: Int){

            itemBinding.apply {

                tvnmPenguna.text = item.user?.name
                tvSaran.text = item.review
                tvReting.text = "5.0 | Kunjungan: " +item.qtyReview

                lyUlasanall.setOnClickListener{
                    onClick.invoke(item)
                }

            }
        }
    }

    fun addItems(items : List<Rating>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDataReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

