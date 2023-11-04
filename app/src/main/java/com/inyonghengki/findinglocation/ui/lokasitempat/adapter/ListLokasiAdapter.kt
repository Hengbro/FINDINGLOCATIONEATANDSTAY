package com.inyonghengki.findinglocation.ui.lokasitempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.LokasiTempat
import com.inyonghengki.findinglocation.databinding.ItemLokasiTempatBinding
import com.inyonghengki.findinglocation.ui.lokasitempat.UpdateLokasiActivity
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.toJson


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class ListLokasiAdapter (
    val onClick: (item: LokasiTempat) -> Unit,
    val onDelete:(item: LokasiTempat, pos: Int) -> Unit)
    : RecyclerView.Adapter<ListLokasiAdapter.ViewHolder>(){

    private var data = ArrayList<LokasiTempat>()

    inner class ViewHolder(private val itemBinding: ItemLokasiTempatBinding):
        RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : LokasiTempat, position: Int){
            val user = Pref.getUser()
            itemBinding.apply {
                tvKota.text = item.kota
                var kecamatan = ""
                var kabupaten = ""

                if (item.kota != null) kabupaten = "Kota/Kab. ${item.kota}"
                if (item.kecamatan != null) kecamatan = "Kec. ${item.kecamatan}"

                tvAlamat.text  =
                    "${item.alamat}, $kabupaten ${item.kota}, $kecamatan, ${item.provinsi}, ${item.kodepos}"

                tvLabel.text  = item.label
                tvKota.text   = item.kota
                tvPhone.text  = user?.phone
                tvEmail.text  = user?.email
                tvNamatempat.text = user?.tempat?.nameTempat

                val context = root.context

                btnMenu.setOnClickListener{
                    onDelete.invoke(item, adapterPosition)

                }

                btnUbahalamattoko.setOnClickListener{
                    context.intentActivity(UpdateLokasiActivity::class.java, item.toJson())
                }

                lymain.setOnClickListener {
                    onClick.invoke(item)
                }

            }
        }

    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<LokasiTempat>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLokasiTempatBinding.inflate(
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

