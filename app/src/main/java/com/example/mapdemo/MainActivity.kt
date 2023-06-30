package com.example.mapdemo

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mapdemo.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var binding:ActivityMainBinding? = null
    private var mGoogleMap:GoogleMap? = null
    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    private var mLat = -33.87365
    private var mLng = 151.20689

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        Places.initialize(applicationContext, getString(R.string.map_api_key))
        autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID,Place.Field.ADDRESS,Place.Field.LAT_LNG))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val latLong = place.latLng!!
                mLat = latLong.latitude
                mLng = latLong.longitude
                zoomOnMap(latLong)
            }

            override fun onError(status: Status) {
                Toast.makeText(this@MainActivity,"Some error occurred",Toast.LENGTH_SHORT).show()
                Log.i("PlaceError", "An error occurred: $status")
            }
        })

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val mapOptionButton:ImageButton = findViewById(R.id.mapOptionsMenu)
        val popupMenu = PopupMenu(this,mapOptionButton)
        popupMenu.menuInflater.inflate(R.menu.map_options,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }
        mapOptionButton.setOnClickListener {
            popupMenu.show()
        }

        binding?.fabStarMark?.setOnClickListener {
            markStarPlace()
        }

        binding?.fabStreetView?.setOnClickListener {
            val intent = Intent(this, FamousPlaceActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        addMarker(LatLng(12.345,23.232))
        addCustomMarker(R.drawable.location_pin_24dp, LatLng(12.543,23.432))
        addDraggableMarker(LatLng(12.987,23.789))

        mGoogleMap?.setOnMapClickListener {position->
            mGoogleMap?.clear()
            addMarker(position)
        }

        mGoogleMap?.setOnMapLongClickListener {
            addCustomMarker(R.drawable.location_pin_24dp,it)
        }

//        mGoogleMap?.setOnMarkerClickListener {
//            it.remove()
//            false
//        }


//        val androidOverlay = GroundOverlayOptions()
//            .image(BitmapDescriptorFactory.fromResource(R.drawable.boy_trash_throw))
//            .position(LatLng(4.566,5.345),100f)
//
//        mGoogleMap?.addGroundOverlay(androidOverlay)
    }

    private fun changeMap(id:Int)
    {
        when(id)
        {
            R.id.normal_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.hybrid_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.satellite_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.terrain_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
    }

    private fun zoomOnMap(mLatLng: LatLng)
    {
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(mLatLng,12f)
        mGoogleMap?.animateCamera(newLatLngZoom)
    }

    private fun addMarker(position: LatLng)
    {
        mGoogleMap?.addMarker(MarkerOptions().position(position)
            .title("Marker")
            .snippet("Snippet")
        )
    }

    private fun addCustomMarker(iconImage: Int, position: LatLng)
    {
        mGoogleMap?.addMarker(MarkerOptions().position(position)
            .draggable(true)
            .title("Custom Marker")
            .snippet("Snippet")
            .icon(BitmapDescriptorFactory.fromResource(iconImage))
        )
    }
    private fun addDraggableMarker(position: LatLng)
    {
        mGoogleMap?.addMarker(MarkerOptions().position(position)
            .draggable(true)
            .title("Draggable Marker")
            .snippet("Snippet")
        )
    }

    private var isStarMarked = false
    private var starMark:Marker? = null
    private fun markStarPlace()
    {
        val starPlace = LatLng(2.345,3.432)
        if (!isStarMarked)
        {
            isStarMarked = true
            starMark = mGoogleMap?.addMarker(MarkerOptions()
                .position(starPlace))
        }
        else
        {
            starMark?.remove()
            isStarMarked = false
        }
    }
}