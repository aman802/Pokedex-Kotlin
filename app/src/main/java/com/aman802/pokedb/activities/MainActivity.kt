package com.aman802.pokedb.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.aman802.pokedb.Constants
import com.aman802.pokedb.Helper
import com.aman802.pokedb.R
import com.aman802.pokedb.SharedPref
import com.aman802.pokedb.adapters.PokemonListAdapter
import com.aman802.pokedb.models.PokemonModel
import com.aman802.pokedb.network.VolleyService
import com.android.volley.Request
import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PokemonListAdapter.onDataChanged {

    private val tag: String = MainActivity::class.java.simpleName
    private var currentPageNumber = 1
    private lateinit var progressBar: ProgressBar
    private lateinit var headerPokeBallImageView: ImageView
    private lateinit var searchEditText: EditText
    private lateinit var clearSearchImageView: ImageView
    private lateinit var pokemonListView: ListView
    private lateinit var noPokemonLinearLayout: LinearLayout
    private lateinit var adapter: PokemonListAdapter
    private val pokemonArrayList = ArrayList<PokemonModel>()
    private var preLast = 0
    private var nextUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Helper.setupKeyboardHidingUI(findViewById(android.R.id.content), this)

        progressBar = findViewById(R.id.activity_main_progress_bar)
        headerPokeBallImageView = findViewById(R.id.activity_main_header_pokeball_image_view)
        searchEditText = findViewById(R.id.activity_main_search_edit_text)
        clearSearchImageView = findViewById(R.id.activity_main_clear_search_image_view)
        pokemonListView = findViewById(R.id.activity_main_pokemon_list_view)
        noPokemonLinearLayout = findViewById(R.id.activity_main_no_pokemon_linear_layout)

        adapter = PokemonListAdapter(this@MainActivity, pokemonArrayList)
        pokemonListView.adapter = adapter

        headerPokeBallImageView.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
            val popupMenu = PopupMenu(this, headerPokeBallImageView)
            popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                Toast.makeText(this, "Feature in development", Toast.LENGTH_SHORT).show()
                return@setOnMenuItemClickListener true
            }

            popupMenu.show()
            headerPokeBallImageView.startAnimation(animation)
        }

        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s.toString())
                if (s.isNullOrEmpty()) {
                    clearSearchImageView.visibility = View.GONE
                }
                else {
                    clearSearchImageView.visibility = View.VISIBLE
                }
            }

        })

        clearSearchImageView.setOnClickListener {
            searchEditText.setText("")
            clearSearchImageView.visibility = View.GONE
        }

        pokemonListView.setOnScrollListener(object : AbsListView.OnScrollListener{
            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val lastItem = firstVisibleItem + visibleItemCount
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        Log.d("End", "Reached end of list. Fetching more pokemon...")
                        preLast = lastItem
                    }

                    if (nextUrl != "" && nextUrl != "null") {
                        currentPageNumber++
                        // This is added to show the loader
                        if (pokemonArrayList[pokemonArrayList.size - 1].getID() != -1) {
                            pokemonArrayList.add(PokemonModel(null))
                            adapter.notifyDataSetChanged()
                        }
                        localAPIFetch()
                    }
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}

        })

        localAPIFetch()
    }

    private fun localAPIFetch() {
        if (currentPageNumber == 1) {
            progressBar.visibility = View.VISIBLE
            pokemonListView.visibility = View.GONE

        }
        searchEditText.isEnabled = false
        val url = Constants.apiPath + "/api/v1/pokemon/?page=" + currentPageNumber
        VolleyService.makeJSONObjectRequest(this@MainActivity, url, Request.Method.GET, null, tag,
            object : VolleyService.JSONObjectInterface {
                override fun onJSONObjectSuccess(response: JSONObject) {
                    nextUrl = response.getString("next")
                    parseResponse(response.getJSONArray("results"))
                    if (currentPageNumber == 1) {
                        progressBar.visibility = View.GONE
                        pokemonListView.visibility = View.VISIBLE
                    }
                    searchEditText.isEnabled = true
                }

                override fun onError(error: VolleyError) {
                    if (currentPageNumber == 1) {
                        progressBar.visibility = View.GONE
                    }
                    VolleyService.handleVolleyError(error, true, this@MainActivity)
                    Log.d("Error", error.message.toString())
                }

            })
    }

    private fun parseResponse(results: JSONArray) {
        if (currentPageNumber > 1) {
            pokemonArrayList.removeAt(pokemonArrayList.size - 1)
        }
        val favoritesList = SharedPref.getFavoritesList(this)
        for (i in 0 until results.length()) {
            val currentPokemonModel = PokemonModel(results[i] as JSONObject?)
            if (!favoritesList.isNullOrEmpty() && favoritesList.contains(currentPokemonModel.getID())) {
                currentPokemonModel.setFavorite(true)
            }
            pokemonArrayList.add(currentPokemonModel)
        }
        adapter.notifyDataSetChanged()
    }

    override fun noPokemonFiltered() {
        noPokemonLinearLayout.visibility = View.VISIBLE
        pokemonListView.visibility = View.GONE
    }

    override fun pokemonFiltered() {
        noPokemonLinearLayout.visibility = View.GONE
        pokemonListView.visibility = View.VISIBLE
    }
}
