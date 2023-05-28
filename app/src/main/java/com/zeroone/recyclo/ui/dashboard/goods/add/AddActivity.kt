package com.zeroone.recyclo.ui.dashboard.goods.add

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.zeroone.recyclo.R
import com.zeroone.recyclo.databinding.ActivityAddGoodsBinding
import java.io.File

class AddActivity : AppCompatActivity() {

    private lateinit var img1 : File
    private lateinit var binding : ActivityAddGoodsBinding

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = com.zeroone.recyclo.utils.Image.uriToFile(uri, this@AddActivity)
                binding.imageView11.setImageURI(uri)
                img1 = myFile
            }
        }
    }

    private fun startGallery(id : Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser,id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.img1.setOnClickListener {
            startGallery(1)
        }

    }
}