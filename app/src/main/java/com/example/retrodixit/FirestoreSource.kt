package com.example.retrodixit

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges

class FirestoreSource {
    val db = FirebaseFirestore.getInstance()
    val currentUser = UserDocument()
    val allUsers = HashMap<String, UserDocument>()
    val userLD = MutableLiveData<HashMap<String, UserDocument>>()
    val usersList = MutableLiveData<List<UserDocument>>()
    val dbName = "dixit_users"

    fun addUser(name: String) {
        if (name.isEmpty()) {
            if (currentUser.name.isNotEmpty()) {
                db.collection(dbName).document(currentUser.name).delete()
            }
            currentUser.name = name
        } else {
            currentUser.name = name
            db.collection(dbName).document(currentUser.name).set(currentUser)
        }
    }

    fun addCard(selected: Int) {
        if (currentUser.name.isNotEmpty()) {
            currentUser.index = selected
            db.collection(dbName).document(currentUser.name).set(currentUser)
        }
    }

    fun changeCardVisibility(visible: Boolean) {
        if (currentUser.name.isNotEmpty()) {
            currentUser.visible = visible
            db.collection(dbName).document(currentUser.name).set(currentUser)
        }
    }

    var registration: ListenerRegistration? = null

    fun listenToFirestore() {
        registration = db.collection(dbName)
            .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                if (e == null && querySnapshot != null) {
                    querySnapshot.documentChanges.forEach { change ->
                        val user = change.document.toObject(UserDocument::class.java)
                        when (change.type) {
                            DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED -> {
                                allUsers.put(user.name, user)
                            }
                            DocumentChange.Type.REMOVED -> {
                                allUsers.remove(user.name)
                            }
                        }
                        allUsers.put(user.name, user)
                        userLD.value = allUsers
                    }
                    if (currentUser.name.isNotEmpty()) {
                        usersList.value = ArrayList(allUsers.filterNot {it.key == currentUser.name}.values)
                    } else {
                        usersList.value = ArrayList(allUsers.values)
                    }
                }
            }
    }

    fun stopListening() {
        registration?.remove()
    }
}

class UserDocument {
    var name = ""
    var index = -1
    var visible = false
}