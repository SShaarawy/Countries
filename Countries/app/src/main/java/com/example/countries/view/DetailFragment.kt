package com.example.countries.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.countries.R
import com.example.countries.databinding.FragmentDetailBinding
import com.example.countries.model.Details
import com.example.countries.roomdb.CountryDao
import com.example.countries.roomdb.CountryDatabase
import com.example.countries.service.RetrofitInstance
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private var name: String = ""
    private var code: String = ""
    private var wikiDataId: String = ""

    private lateinit var db: CountryDatabase
    private lateinit var countryDao: CountryDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bnView = requireActivity().findViewById<BottomNavigationView>(R.id.bnMain)
        val tvCountries = requireActivity().findViewById<TextView>(R.id.tvHome)

        bnView.visibility = View.GONE
        tvCountries.visibility = View.GONE

        val data = arguments
        name = data?.getString("name").toString()
        val sign = data?.getInt("sign")


        db = Room.databaseBuilder(requireContext(), CountryDatabase::class.java, "Country")
            .allowMainThreadQueries()
            .build()

        countryDao = db.countryDao()


        if (countryDao.getStateFromName(name)) {
            binding.ivStar.setImageResource(R.drawable.ic_star_pressed)
        } else {
            binding.ivStar.setImageResource(R.drawable.ic_star_not_pressed)
        }

        code = countryDao.getCodeFromName(name)

        loadData()

        binding.btnDetails.setOnClickListener {
            btnDetailsClicked()
        }

        binding.ivStar.setOnClickListener {
            if (!countryDao.getStateFromName(name)) {
                binding.ivStar.setImageResource(R.drawable.ic_star_pressed)
                countryDao.updateState(name, true)

            } else {
                binding.ivStar.setImageResource(R.drawable.ic_star_not_pressed)
                countryDao.updateState(name, false)
            }
        }

        binding.ivBack.setOnClickListener {
            bnView.visibility = View.VISIBLE
            tvCountries.visibility = View.VISIBLE

            requireActivity().supportFragmentManager.beginTransaction().apply {
                if(sign == 0){
                    replace(R.id.flMain, HomeFragment())
                } else {
                    replace(R.id.flMain, SavedFragment())
                }
                commit()
            }
        }
    }

    private fun loadData() {

        val call = RetrofitInstance.api.getDetails(code)
        call.enqueue(object : Callback<Details> {
            override fun onResponse(call: Call<Details>, response: Response<Details>) {
                if (response.isSuccessful) {
                    response.body()!!.data.let {
                        println("code: $it")

                        wikiDataId = it.wikiDataId

                        binding.tvName.text = it.name
                        binding.tvCode.text = " ${it.code}"
                        binding.ivFlag.loadUrl(it.flagImageUri)
                    }
                }
            }

            override fun onFailure(call: Call<Details>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    private fun btnDetailsClicked() {

        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikidata.org/wiki/${wikiDataId}"))
        startActivity(intent)

    }
}