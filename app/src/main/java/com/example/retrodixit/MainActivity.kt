package com.example.retrodixit

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrodixit.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.view_cards.*
import kotlinx.android.synthetic.main.view_selected.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: DixitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cards = resources.assets.list("cards")
        // check cards present
        if (cards == null) {
            Toast.makeText(this, R.string.no_card_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        var totalCard = cards.size
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, MainViewModelFactory(FirestoreSource(), totalCard)).get(DixitViewModel::class.java)
        binding.vm = viewModel

        cards_list.adapter = CardAdapter()
        (cards_list.adapter as CardAdapter).setOnItemListener{ item -> viewModel.selectCardFromList(item) }
        cards_list.layoutManager = GridLayoutManager(this, 3)

        users_list.adapter = StatusUserAdapter()
        (users_list.adapter as StatusUserAdapter).setOnItemListener{ item -> viewModel.showUserCard(item) }
        users_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onBackPressed() {
        if (!viewModel.onBackPressed()) {
            super.onBackPressed()
        }
    }
}

object CommonsBinding {
    @BindingAdapter("app:inputVisible")
    @JvmStatic
    fun EditText.setInputVisible(isVisible: Boolean?) {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isVisible != null && isVisible) {
            manager.showSoftInput(this, 0)
        } else {
            manager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    @BindingAdapter("app:imageSource")
    @JvmStatic
    fun ImageView.setImage(cardId: Int?) {
        if (cardId == null) {
            setImageDrawable(null)
        } else {
            loadImage(context, cardId, this)
        }
    }

    @BindingAdapter("app:usersStatus")
    @JvmStatic
    fun RecyclerView.setUserStatus(documents: List<UserDocument>?) {
        (adapter as StatusUserAdapter).replace(documents)
    }

    @BindingAdapter("app:cardList")
    @JvmStatic
    fun RecyclerView.setCardsItem(cards: List<Int>?) {
        (adapter as CardAdapter).replace(cards)
    }


    private fun loadImage(context: Context, cardId: Int, view: ImageView) {
        try {
            context.resources.assets.open("cards/card_" + String.format("%05d", cardId) + ".jpg")
                .use { view.setImageBitmap(BitmapFactory.decodeStream(it)) }
        } catch (e: IOException) {
            view.setImageDrawable(null)
        }
    }
}

