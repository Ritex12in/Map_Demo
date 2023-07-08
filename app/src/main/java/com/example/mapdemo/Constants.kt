package com.example.mapdemo

import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

object Constants {
    fun getPlaceList():ArrayList<PlaceModel>
    {
        var placesList = ArrayList<PlaceModel>()
            val place1 = PlaceModel(
                40.690389,
                -74.046139,
                "Statue of Liberty"
            )
        placesList.add(place1)

        val place2 = PlaceModel(
            48.85778599650331,
            2.292758808374579,
            "Eiffel Tower"
        )
        placesList.add(place2)

        val place3 = PlaceModel(
            51.500909,
            -0.123799,
            "Big Ben"
        )
        placesList.add(place3)

        val place4 = PlaceModel(
            43.722628,
            10.396492,
            "Leaning Tower of Pisa"
        )
        placesList.add(place4)

        val place5 = PlaceModel(
            41.888622,
            12.490496,
            "Colosseum"
        )
        placesList.add(place5)

        val place6 = PlaceModel(
            37.828937,
            -122.485816,
            "Golden Gate Bridge"
        )
        placesList.add(place6)

        return placesList
    }

    fun getAstLatLong():ArrayList<LatLng>
    {
        return arrayListOf<LatLng>(
            LatLng(-35.016, 143.321),
            LatLng(-34.747, 145.592),
            LatLng(-34.364, 147.891),
            LatLng(-33.501, 150.217),
            LatLng(-32.306, 149.248),
            LatLng(-32.491, 147.309)
        )
    }
}