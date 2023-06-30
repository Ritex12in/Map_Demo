package com.example.mapdemo

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.StreetViewPanoramaView
import com.google.android.gms.maps.model.LatLng

class StreetViewActivity : AppCompatActivity(){
    private lateinit var streetViewPanoramaView: StreetViewPanoramaView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val options = StreetViewPanoramaOptions()
        val placeList = Constants.getPlaceList()
        val position = intent.getIntExtra("position",0)
        val lat: Double = placeList[position].lat
        val lng:Double = placeList[position].lng
        val pos = LatLng(lat, lng)
        savedInstanceState ?: options.position(pos)
        streetViewPanoramaView = StreetViewPanoramaView(this, options)
        addContentView(
            streetViewPanoramaView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        streetViewPanoramaView.onCreate(savedInstanceState?.getBundle(STREETVIEW_BUNDLE_KEY))
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var streetViewBundle = outState.getBundle(STREETVIEW_BUNDLE_KEY)
        if (streetViewBundle == null) {
            streetViewBundle = Bundle()
            outState.putBundle(
                STREETVIEW_BUNDLE_KEY,
                streetViewBundle
            )
        }
        streetViewPanoramaView.onSaveInstanceState(streetViewBundle)
    }


    companion object {
        private const val STREETVIEW_BUNDLE_KEY = "StreetViewBundleKey"
    }
}