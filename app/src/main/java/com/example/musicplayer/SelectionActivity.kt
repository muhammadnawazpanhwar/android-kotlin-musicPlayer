package com.example.musicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.databinding.ActivitySelectionBinding
import java.util.*
import kotlin.collections.ArrayList

class SelectionActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectionBinding
    private lateinit var adapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)

        setTheme(MainActivity.currentTheme[MainActivity.themeIndex])

        setContentView(binding.root)
        binding.selectionRV.setItemViewCacheSize(10)
        binding.selectionRV.setHasFixedSize(true)
        binding.selectionRV.layoutManager = LinearLayoutManager(this)

        adapter = MusicAdapter(this, MainActivity.musicListMA)
        binding.selectionRV.adapter = adapter

        binding.backBtnSA.setOnClickListener {
            finish()
        }

        binding.searchViewSA.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                MainActivity.musicListSearch = ArrayList()
                if(newText != null) {
                    val userInput = newText.lowercase(Locale.ROOT)
                    for( song in MainActivity.musicListMA){
                        if(song.title.lowercase().contains(userInput))
                            MainActivity.musicListSearch.add(song)
                        MainActivity.search = true
                        adapter.updateMusicList(searchList = MainActivity.musicListSearch)
                    }
                }
                return true
            }
        })
    }
    override fun onResume() {
        super.onResume()
        //for black theme checking
        if(MainActivity.themeIndex == 4)
        {
            binding.searchViewSA.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
        }
    }
}