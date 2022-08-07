package com.example.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.countries.R
import com.example.countries.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val homeFragment = HomeFragment()
        val savedFragment = SavedFragment()

        setCurrentFragment(homeFragment)

        binding.bnMain.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.mHome -> setCurrentFragment(homeFragment)
                R.id.mSaved -> setCurrentFragment(savedFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flMain,fragment)
            commit()
        }
    }
}