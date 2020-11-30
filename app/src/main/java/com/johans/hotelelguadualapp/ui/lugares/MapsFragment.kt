package com.johans.hotelelguadualapp.ui.lugares

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.johans.hotelelguadualapp.R

class MapsFragment : Fragment(), GoogleMap.OnPoiClickListener {
    private lateinit var mMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap

        setUpMap() // funcion mi posicion
        mMap.setOnPoiClickListener(this)


        mMap.uiSettings.isZoomControlsEnabled = true
        //mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val Hotel = LatLng(5.4734913, -75.5871343)
        mMap.addMarker(
            MarkerOptions().position(Hotel).title("Hotel el Guadual")
                .snippet("El Llano Marmato Caldas")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Hotel, 15f))

        val estadio = LatLng(5.4708937, -75.5888522)
        mMap.addMarker(
            MarkerOptions().position(estadio).title("Estadio Marmato")
                .snippet("El Llano Marmato Caldas")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(estadio, 15f))

        val Hospital = LatLng(5.4719967, -75.5861728)
        mMap.addMarker(
            MarkerOptions().position(Hospital).title("Hospital Marmato")
                .snippet("La Betulia Marmato Caldas")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Hospital, 15f))

        val Estadero = LatLng(5.4696844, -75.5887885)
        mMap.addMarker(
            MarkerOptions().position(Estadero).title("Estadero Gavilanes")
                .snippet("El Llano Marmato Caldas")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Estadero, 15f))

        val tienda = LatLng(5.4728877, -75.5877813)
        mMap.addMarker(
            MarkerOptions().position(tienda).title("Granero el Uva").snippet("Tienda de Abarrotes")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tienda, 15f))

        val alcaldia = LatLng(5.4753361, -75.598988)
        mMap.addMarker(
            MarkerOptions().position(alcaldia).title("Alcaldia Municipal")
                .snippet("Cabecera municipal")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alcaldia, 15f))

        val caldasGold = LatLng(5.477672, -75.5975793)
        mMap.addMarker(
            MarkerOptions().position(caldasGold).title("Caldas Gold S.A.S.")
                .snippet("Multinacional Minera")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(caldasGold, 15f))

        val parque = LatLng(5.4722246, -75.5900726)
        mMap.addMarker(
            MarkerOptions().position(parque).title("PArque Principal").snippet("El Llano Marmato")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parque, 15f))
    }

    private fun setUpMap() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onPoiClick(poi: PointOfInterest?) {
        Toast.makeText(
            context,
            "Nombre: ${poi?.name} \nLatitud: ${poi?.latLng?.latitude} \nLongitud: ${poi?.latLng?.longitude}",
            Toast.LENGTH_SHORT
        ).show()
    }
}