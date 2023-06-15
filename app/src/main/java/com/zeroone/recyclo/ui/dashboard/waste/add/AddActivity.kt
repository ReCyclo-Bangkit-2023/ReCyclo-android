package com.zeroone.recyclo.ui.dashboard.waste.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeroone.recyclo.R


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.snackbar.Snackbar
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityAddWasteBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.dashboard.DashboardActivity
import com.zeroone.recyclo.ui.dashboard.goods.GoodsViewModel
import com.zeroone.recyclo.ui.dashboard.waste.ViewModelFactory
import com.zeroone.recyclo.ui.dashboard.waste.WasteViewModel
import com.zeroone.recyclo.ui.map.MapsViewModel
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.Utils
import java.io.File

class AddActivity : AppCompatActivity() {

    private lateinit var img1 : File
    private lateinit var img2 : File
    private lateinit var img3 : File
    private lateinit var binding : ActivityAddWasteBinding
    private lateinit var vm : WasteViewModel
    private lateinit var loading : LoadingBar
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var latitude : Double = 0.0
    var longitude : Double = 0.0

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private val launcherIntentGallery1 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = com.zeroone.recyclo.utils.Image.uriToFile(uri, this@AddActivity)
                binding.img1View.setImageURI(uri)
                img1 = myFile
            }
        }
    }

    private val launcherIntentGallery2 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = com.zeroone.recyclo.utils.Image.uriToFile(uri, this@AddActivity)
                binding.img2View.setImageURI(uri)
                img2 = myFile
            }
        }
    }

    private val launcherIntentGallery3 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = com.zeroone.recyclo.utils.Image.uriToFile(uri, this@AddActivity)
                binding.img3View.setImageURI(uri)
                img3 = myFile
            }
        }
    }

    private fun startGallery(id : Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        if(id == 1) {
            launcherIntentGallery1.launch(chooser)
        } else if (id == 2) {
            launcherIntentGallery2.launch(chooser)
        } else {
            launcherIntentGallery3.launch(chooser)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWasteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.img1.setOnClickListener {
            startGallery(1)
        }
        binding.img2.setOnClickListener {
            startGallery(2)
        }
        binding.img3.setOnClickListener {
            startGallery(3)
        }



        binding.kirimGambar.setOnClickListener {
            vm.getToken().observe(this){
                if (img1 != null) {
                    vm.predictPrice(it,img1)
                }
            }
        }

        val typeOfProducts = resources.getStringArray(R.array.KindspinnerItems)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, typeOfProducts)

        binding.inpJenis.adapter = adapter

        binding.inpPrice.addTextChangedListener(object: TextWatcher{
            var setEditText =  binding.inpPrice.text.toString()
            var setTextTv = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != setEditText) {
                    binding.inpPrice.removeTextChangedListener(this)
                    val replace: String = s.toString().replace("[Rp. ]".toRegex(), "")
                    if (!replace.isEmpty()) {
                        setEditText = Utils.formatrupiah(replace.toDouble())
                        setTextTv = setEditText
                    } else {
                        setEditText = ""
                        setTextTv = "Hasil Input"
                    }
                    binding.inpPrice.setText(setEditText)
                    binding.inpPrice.setSelection(setEditText.length)
                    binding.inpPrice.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.saveBtn.setOnClickListener {
            vm.getToken().observe(this){
                vm.insert(binding.inpName.text.toString(),binding.inpPrice.text.toString(),binding.inpJenis.selectedItem.toString(),binding.inpStok.text.toString(),img1,img2,img3,binding.inpDesc.text.toString(),latitude.toString(),longitude.toString(),it)
            }
        }

        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this, ViewModelFactory(pref)).get(WasteViewModel::class.java)
        loading = LoadingBar(this)

        vm.isLoading.observe(this) {
            showLoading(it)
        }

        vm.price.observe(this){
            binding.inpPrice.setText(Utils.formatrupiah(it.toDouble()))
        }

        vm.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { it1 ->
                Snackbar.make(
                    binding.root,
                    it1,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        vm.status.observe(this){
            if(it){
                finish()
                startActivity(Intent(this@AddActivity,DashboardActivity::class.java))
            }
        }

        getMyLocation()

    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                        latitude = location?.latitude!!
                        longitude = location?.longitude!!
                }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startLoading()
        } else {
            loading.isDismiss()
        }
    }

}