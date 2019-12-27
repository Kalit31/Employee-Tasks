package com.example.todolist_ramkumartextiles.owners.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todolist_ramkumartextiles.owners.data.repo.OwnersRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsViewModel(private val ownersRepository: OwnersRepository): ViewModel(){
    fun addMarkers(googleMap:GoogleMap?){
        ownersRepository.loadUsers(object: OwnersRepository.UserCallback{
            override fun onCallback(users: ArrayList<String>) {
                users.removeAt(0)
                for(user in users){
                    var markerList=ArrayList<Marker>()
                    ownersRepository.addMarker(user,object:OwnersRepository.addMarkersCallback{
                        override fun onCallback(latLng: LatLng) {
                            val marker = googleMap?.addMarker(MarkerOptions().position(latLng).title(user))
                            if (marker != null) {
                                markerList.add(marker)
                            }
                        }

                    })
                }
            }
        })
    }
}