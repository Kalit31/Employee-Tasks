package com.example.todolist_ramkumartextiles.owners.data.repo

import android.util.Log
import android.widget.Toast
import com.example.todolist_ramkumartextiles.tasks.data.room.dataclasses.TaskInformation
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OwnersRepository {
    companion object {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    }

    fun loadUsers(userCallback:UserCallback){
        var usersList = ArrayList<String>()
        database.getReference("Usernames").addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError){}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                usersList.clear()
                usersList.add("Select Employee")
                for (ds in dataSnapshot.children) {
                    val user = ds.getValue(String::class.java)
                    usersList.add(user!!)
                }
                userCallback.onCallback(usersList)
            }
        })
    }

    fun uploadTask(employeeName:String,taskDesc:String,dateString:String){
        var databaseReference = database.getReference("Users")
        val taskId = databaseReference.push().key
        val taskInfo =
            TaskInformation(
                employeeName,
                taskDesc,
                dateString,
                false,
                taskId
            )
        databaseReference.child(employeeName).child("tasks").child(taskId!!).setValue(taskInfo)

        val notifications = FirebaseDatabase.getInstance().getReference("notificationsRequests").child(employeeName)
        notifications.push().setValue(taskDesc)
    }

    fun removeNotifications(){
        database.getReference("notificationsRequests").removeValue()
    }

    fun addMarker(user:String,addMarkersCallback: addMarkersCallback){
        val refLoc = FirebaseDatabase.getInstance().getReference("Users").child(user)
        refLoc.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var lat = 0.0
                var lng = 0.0

                for(ds in dataSnapshot.children)                            {
                    if(ds.key == "latitude")
                        lat = ds.getValue(String::class.java)!!.toDouble()
                    if(ds.key == "longitude")
                        lng = ds.getValue(String::class.java)!!.toDouble()
                }
                val latlng = LatLng(lat, lng)
                /*val marker = googleMap.addMarker(MarkerOptions().position(latlng).title(user))
                markerList.add(marker)*/
                addMarkersCallback.onCallback(latlng)
            }
        })
    }


    interface UserCallback{
        fun onCallback(users:ArrayList<String>)
    }

    interface addMarkersCallback{
        fun onCallback(latLng: LatLng)
    }

}


/*for(user in users){
                    val refLoc = FirebaseDatabase.getInstance().getReference("Users").child(user)
                    val locationList = HashMap<String,LatLng>()
                    refLoc.addValueEventListener(object: ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {}

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var lat = 0.0
                            var lng = 0.0
                            /*for(m in markerList){
                                m.remove()
                            }*/
                            for(ds in dataSnapshot.children)                            {
                                if(ds.key == "latitude")
                                    lat = ds.getValue(String::class.java)!!.toDouble()
                                if(ds.key == "longitude")
                                    lng = ds.getValue(String::class.java)!!.toDouble()
                            }
                            val latlng = LatLng(lat, lng)
                            locationList
                            /*val marker = googleMap.addMarker(MarkerOptions().position(latlng).title(user))
                            markerList.add(marker)*/
                        }
                    })
                }*/