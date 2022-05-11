package com.example.musicplayer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivitySettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(MainActivity.currentThemeNav[MainActivity.themeIndex])

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Settings"

        when(MainActivity.themeIndex){
            0-> binding.tealTheme.setBackgroundColor(R.color.teal_200)
            1-> binding.blackTheme.setBackgroundColor(R.color.black)
            2-> binding.purpleTheme.setBackgroundColor(R.color.purple_700)
            3-> binding.greenTheme.setBackgroundColor(R.color.green)
            4-> binding.blueTheme.setBackgroundColor(R.color.blue)
        }

        binding.tealTheme.setOnClickListener { saveTheme(0)}
        binding.blackTheme.setOnClickListener { saveTheme(1)}
        binding.purpleTheme.setOnClickListener { saveTheme(2)}
        binding.greenTheme.setOnClickListener { saveTheme(3)}
        binding.blueTheme.setOnClickListener { saveTheme(4)}
        binding.versionNameTV.text = setVersionDetails()
        binding.sortBtn.setOnClickListener {
            val menuList = arrayOf("Time", "Name", "Size")
            var currentSort = MainActivity.sortOrder
            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle("Sort By")
               .setPositiveButton("OK") { _, _ ->
                   val editor = getSharedPreferences("SORTING", MODE_PRIVATE).edit()
                   editor.putInt("sortOrder", currentSort)
                   editor.apply()
                }
                .setSingleChoiceItems(menuList, currentSort){_,which->
                    currentSort = which

                }
            val customDialog = builder.create()
            customDialog.show()
            setDialogBtnBackground(this, customDialog)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun saveTheme(index: Int) {
        if (MainActivity.themeIndex != index) {
            val editor = getSharedPreferences("THEMES", MODE_PRIVATE).edit()
            editor.putInt("themeIndex", index)
            editor.apply()

            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle("Apply Theme")
                .setMessage("Do you want to apply this Theme?")
                .setPositiveButton("Yes") { _, _ ->

                    exitApplication()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val customDialog = builder.create()
            customDialog.show()
            setDialogBtnBackground(this, customDialog)
        }
    }

    private fun setVersionDetails(): String{
        return "Version Name: ${BuildConfig.VERSION_NAME}"
    }
}