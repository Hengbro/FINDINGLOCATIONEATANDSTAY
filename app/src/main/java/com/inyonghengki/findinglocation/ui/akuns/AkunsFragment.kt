package com.inyonghengki.findinglocation.ui.akuns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.inyonghengki.findinglocation.databinding.FragmentAkunsBinding
import com.inyonghengki.findinglocation.ui.adminpanel.AdminPanel
import com.inyonghengki.findinglocation.ui.akuns.setting.scanqrcode.ScanQrCodeActivity
import com.inyonghengki.findinglocation.ui.navigation.NavigationActivity
import com.inyonghengki.findinglocation.ui.review.ListReviewMySelf
import com.inyonghengki.findinglocation.ui.tempat.AddTempatActivity
import com.inyonghengki.findinglocation.ui.tempat.InfoTempatActivity
import com.inyonghengki.findinglocation.ui.updateprofile.UpdateProfileActivity
import com.inyonghengki.findinglocation.util.Constants.USER_URL
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.getInitial
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.pushActivity
import com.inyongtisto.myhelper.extension.visible
import com.squareup.picasso.Picasso

class AkunsFragment : Fragment() {

    private var _binding: FragmentAkunsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAkunsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainButton()

        return root
    }

    override fun onResume() {
        setUser()
        super.onResume()
    }

    private fun mainButton() {
        binding.apply {

            btnLogout.setOnClickListener {
                val dialog = AlertDialog.Builder(requireActivity())
                dialog.apply {
                    setTitle("Konfirmasi keluar")
                    setMessage("Apakah anda yakin untuk keluar dari akun anda saat ini?")
                    setPositiveButton("KELUAR"){ dialogInterface, i ->
                        Pref.isLogin = false
                        pushActivity(NavigationActivity::class.java)
                        dialogInterface.dismiss()
                    }
                    setNegativeButton("BATAL"){ dialogInterface, c ->
                        dialogInterface.dismiss()
                    }
                    dialog.show()
                }
            }

            btnEditProfile.setOnClickListener {
                intentActivity(UpdateProfileActivity::class.java)
            }

            btnPanelAdmin.setOnClickListener {
                intentActivity(AdminPanel::class.java)
            }
            btnScan.setOnClickListener {
                intentActivity(ScanQrCodeActivity::class.java)
            }

            btnRiwayat.setOnClickListener {
                intentActivity(ListReviewMySelf::class.java)
            }
        }
    }

    private fun setUser(){
        val user = Pref.getUser()
        if (user != null){
            binding.apply {
                tvNama.text = user.name
                tvEmail.text = user.email
                tvPhone.text = user.phone
                tvInisial.text = user.name.getInitial()

                Picasso.get().load(USER_URL+user.image).into(binding.imgProfile)

                if (user.tempat != null){
                    tvNmtempat.text = user.tempat?.nameTempat
                    btnTempat.setOnClickListener {
                        intentActivity(InfoTempatActivity::class.java)
                    }
                } else {
                    btnTempat.setOnClickListener {
                        intentActivity(AddTempatActivity::class.java)
                    }
                }

                btnPanelAdmin.visible(Pref.getUser()?.isAdmin()?: false)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}