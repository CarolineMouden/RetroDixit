package com.example.retrodixit

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * The factory to create the LoginViewModel with parameters.
 */
class MainViewModelFactory(private val repository: FirestoreSource, private val totalCards:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DixitViewModel(repository, totalCards) as T
    }
}


class DixitViewModel constructor(val firestore: FirestoreSource, val totalCards:Int) : ViewModel(), LifecycleObserver {
    val SCREEN_START = 0
    val SCREEN_ALL_CARDS = 1
    val SCREEN_WAITING_ROOM = 2
    val SCREEN_SHOW_CARD = 3

    val allCards = (1..totalCards).toList().shuffled()

    val name = MutableLiveData<String>()
    var selectedCard = MutableLiveData<Int>()
    var isSelectCardVisible = MutableLiveData<Boolean>()
    var isShowCardVisible = MutableLiveData<Boolean>()
    var isHideCardVisible = MutableLiveData<Boolean>()

    val currentScreen = MutableLiveData<Int>()

    // others (firestore)
    val userCardStatus = firestore.usersList

    // preview
    val previewUserName = MutableLiveData<String>()
    val previewCard = MutableLiveData<Int>()

    init {
        currentScreen.value = SCREEN_START
        isSelectCardVisible.value = false
        isShowCardVisible.value = false
        isHideCardVisible.value = false
        selectedCard.value = -1
    }

    fun selectName() {
        if (!name.value.isNullOrEmpty()) {
            currentScreen.value = SCREEN_ALL_CARDS
            firestore.addUser(name.value!!)
            firestore.addCard(-1)
            firestore.changeCardVisibility(false)
        }
    }

    fun selectCardFromList(index: Int) {
        if (index >= 0 && index <= totalCards) {
            selectedCard.value = index
            isSelectCardVisible.value = true
            isShowCardVisible.value = false
            isHideCardVisible.value = false
            currentScreen.value = SCREEN_WAITING_ROOM
            firestore.listenToFirestore()
        }
    }

    fun showUserCard(item: UserDocument) {
        if (item.index >= 0 && item.index <= totalCards) {
            previewUserName.value = item.name
            previewCard.value = item.index
            currentScreen.value = SCREEN_SHOW_CARD
        }
    }

    fun selectCard() {
        isSelectCardVisible.value = false
        isShowCardVisible.value = true
        isHideCardVisible.value = false
        firestore.addCard(selectedCard.value!!)
    }

    fun showCard() {
        isSelectCardVisible.value = false
        isShowCardVisible.value = false
        isHideCardVisible.value = true
        firestore.changeCardVisibility(true)
    }

    fun hideCard() {
        isSelectCardVisible.value = false
        isShowCardVisible.value = true
        isHideCardVisible.value = false
        firestore.changeCardVisibility(false)
    }

    fun onBackPressed(): Boolean {
        if (currentScreen.value == SCREEN_ALL_CARDS) {
            currentScreen.value = SCREEN_START
            resetSelection()
            firestore.addUser("")
            return true
        } else if (currentScreen.value == SCREEN_WAITING_ROOM) {
            currentScreen.value = SCREEN_ALL_CARDS
            firestore.stopListening()
            resetSelection()
            return true
        } else if (currentScreen.value == SCREEN_SHOW_CARD) {
            currentScreen.value = SCREEN_WAITING_ROOM
            return true
        }
        return false
    }

    fun resetSelection() {
        selectedCard.value = -1
        isSelectCardVisible.value = false
        isShowCardVisible.value = false
        isHideCardVisible.value = false
        firestore.addCard(-1)
        firestore.changeCardVisibility(false)
    }
}