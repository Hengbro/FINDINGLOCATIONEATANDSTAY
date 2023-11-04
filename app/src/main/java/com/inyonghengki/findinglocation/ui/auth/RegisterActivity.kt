package com.inyonghengki.findinglocation.ui.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.core.data.source.remote.request.RegistrasiRequest
import com.inyonghengki.findinglocation.databinding.ActivityRegisterBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyongtisto.myhelper.extension.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegisterActivity : MyActivity() {

    private  val viewModel : AuthViewModel by viewModel()
    private val calendar = Calendar.getInstance()

    private  var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainButton()
    }
    private fun mainButton(){
        binding.btnRegister.setOnClickListener{
            registrasi()
        }

        binding.edtTglLahir.setOnClickListener {
            showDatePickerDialog()
        }
        binding.btnGoogle.setOnClickListener{
            dataDummy()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun dataDummy(){
        binding.edtNama.setText("SUKSES")
        binding.edtEmail.setText("sukses@gmail.com")
        binding.edtPhone.setText("08216075893")
        binding.edtPassword.setText("biSA1208")
        binding.edtPwdkonfir.setText("biSA1208")
    }

   private fun registrasi(){

        if (binding.edtNama.isEmpty())  return
        if (binding.edtEmail.isEmpty()) return
        if (binding.edtPhone.isEmpty()) return
        if (binding.edtKota.isEmpty()) return
       if (binding.edtTglLahir.isEmpty()) return
        if (binding.edtPassword.isEmpty()) return
        if (binding.edtPwdkonfir.isEmpty()) return

       val body = RegistrasiRequest(
           binding.edtNama.text.toString(),
           binding.edtEmail.text.toString(),
           binding.edtPhone.text.toString(),
           binding.edtKota.text.toString(),
           binding.edtTglLahir.text.toString(),
           binding.edtPassword.text.toString()
       )
       viewModel.register(body).observe(this) {
           when (it.state) {
               State.SUCCESS -> {
                   progress.dismiss()
                   showToast("Selamat anda berhasil mendaftar " + it?.data?.name)
                   pushActivity(LoginActivity::class.java)
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

    private fun showDatePickerDialog() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate: String = sdf.format(calendar.getTime())
                binding.edtTglLahir.setText(formattedDate)
            }
        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}