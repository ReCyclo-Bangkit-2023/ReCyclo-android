package com.zeroone.recyclo.ui.map


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zeroone.recyclo.R
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.api.response.DataItemproduct
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.ActivityMapsBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.detail.DetailActivity
import java.io.IOException
import java.util.Locale


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var vm : MapsViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val pref = SessionPreference.getInstance(dataStore)
        vm = ViewModelProvider(this,ViewModelFactory(pref)).get(MapsViewModel::class.java)

        vm.getToken().observe(this){
            vm.getGoods(it)
        }

        vm.goods.observe(this){
            addManyMarker(it)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        setMapStyle()



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    data class goodPlace(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )

    private val boundsBuilder = LatLngBounds.Builder()

    private fun addManyMarker(goods: List<DataItemproduct>) {
       
        goods.forEach { good ->
            mMap.setOnMarkerClickListener { marker ->

                val markerName = marker.title

                val dialog = BottomSheetDialog(this)


                val view = layoutInflater.inflate(R.layout.custom_tooltip, null)


                val heroTooltip = view.findViewById<ImageView>(R.id.hero_tooltip)
                val titleTooltip = view.findViewById<TextView>(R.id.title_tooltip)
                val btnClose = view.findViewById<View>(R.id.close)
                val btnDetail = view.findViewById<Button>(R.id.detail_tooltip)

                titleTooltip.text = good.title
                Glide.with(this).load(good.image1).into(heroTooltip);


                btnClose.setOnClickListener {

                    dialog.dismiss()
                }

                btnDetail.setOnClickListener{
                    val intent = Intent(this@MapsActivity,DetailActivity::class.java)
                    intent.putExtra("product",good)
                    startActivity(intent)
                }
                dialog.setCancelable(false)

                dialog.setContentView(view)

                dialog.show()


                false
            }

            val latLng = LatLng(good.lat.toDouble(), good.lon.toDouble())
            val addressName = getAddressName(good.lat.toDouble(), good.lon.toDouble())
            if (good.kind.lowercase().equals("plastik")) {

                mMap.addMarker(MarkerOptions().position(latLng).title(good.title).icon(vectorToBitmap(R.drawable.waste_bottle, Color.parseColor("#2E9399"))))
            } else if (good.kind.lowercase().equals("kardus")) {
                mMap.addMarker(MarkerOptions().position(latLng).title(good.title).icon(vectorToBitmap(R.drawable.kardus_ico, Color.parseColor("#FBAA4C"))))

            } else if (good.kind.lowercase().equals("kaleng")) {
                mMap.addMarker(MarkerOptions().position(latLng).title(good.title).icon(vectorToBitmap(R.drawable.can_ico, Color.parseColor("#FB4C4C"))))

            }
            else if (good.kind.lowercase().equals("kertas")) {
                mMap.addMarker(MarkerOptions().position(latLng).title(good.title).icon(vectorToBitmap(R.drawable.kertas_ico, Color.parseColor("#FFFFFF"))))

            }
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    private fun getAddressName(lat: Double, lon: Double): String? {
        var addressName: String? = null
        val geocoder = Geocoder(this@MapsActivity, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(lat, lon, 1)
            if (list != null && list.size != 0) {
                addressName = list[0].getAddressLine(0)
                Log.d(TAG, "getAddressName: $addressName")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressName
    }

    companion object {
        private const val TAG = "MapsActivity"
    }

}