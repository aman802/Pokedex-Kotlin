package com.aman802.pokedb.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.aman802.pokedb.Constants
import com.aman802.pokedb.R
import com.aman802.pokedb.SharedPref
import com.aman802.pokedb.adapters.PokemonListAdapter
import com.aman802.pokedb.customViews.tagView
import com.aman802.pokedb.models.PokemonModel
import com.aman802.pokedb.network.VolleyService
import com.android.volley.Request
import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val tag: String = MainActivity::class.java.simpleName
    private val pokemonCount = 807
    private lateinit var progressBar: ProgressBar
    private lateinit var searchEditText: EditText
    private lateinit var clearSearchImageView: ImageView
    private lateinit var popularSearchHeader: LinearLayout
    private lateinit var popularSearchImageView: ImageView
    private lateinit var popularSearchTagsLinearLayout: LinearLayout
    private lateinit var searchTag1: tagView
    private lateinit var searchTag2: tagView
    private lateinit var searchTag3: tagView
    private lateinit var searchTag4: tagView
    private lateinit var searchTag5: tagView
    private lateinit var searchTag6: tagView
    private lateinit var searchTag7: tagView
    private lateinit var searchTag8: tagView
    private lateinit var pokemonListView: ListView
    private lateinit var adapter: PokemonListAdapter
    private val pokemonArray = Array(pokemonCount) { PokemonModel(null) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.activity_main_progress_bar)
        searchEditText = findViewById(R.id.activity_main_search_edit_text)
        clearSearchImageView = findViewById(R.id.activity_main_clear_search_image_view)
        popularSearchTagsLinearLayout = findViewById(R.id.activity_main_popular_search_tags_linear_layout)
        popularSearchHeader = findViewById(R.id.activity_main_popular_search_header_linear_layout)
        popularSearchImageView = findViewById(R.id.activity_main_popular_search_image_view)
        searchTag1 = tagView(findViewById(R.id.search_tag_1))
        searchTag2 = tagView(findViewById(R.id.search_tag_2))
        searchTag3 = tagView(findViewById(R.id.search_tag_3))
        searchTag4 = tagView(findViewById(R.id.search_tag_4))
        searchTag5 = tagView(findViewById(R.id.search_tag_5))
        searchTag6 = tagView(findViewById(R.id.search_tag_6))
        searchTag7 = tagView(findViewById(R.id.search_tag_7))
        searchTag8 = tagView(findViewById(R.id.search_tag_8))
        pokemonListView = findViewById(R.id.activity_main_pokemon_list_view)

        adapter = PokemonListAdapter(this@MainActivity, pokemonArray)
        pokemonListView.adapter = adapter

        progressBar.visibility = View.VISIBLE
        pokemonListView.visibility = View.GONE
        searchEditText.isEnabled = false

        searchTag1.setText("Pikachu")
        searchTag2.setText("Charizard")
        searchTag3.setText("Mew")
        searchTag4.setText("Arceus")
        searchTag5.setText("Greninja")
        searchTag6.setText("Cyndaquil")
        searchTag7.setText("Bulbasaur")
        searchTag8.setText("Mewtwo")

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

        popularSearchHeader.setOnClickListener {
            when(popularSearchTagsLinearLayout.visibility) {
                View.VISIBLE ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popularSearchTagsLinearLayout.visibility = View.GONE
                        popularSearchImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_add, theme))
                    }
                    else {
                        popularSearchTagsLinearLayout.visibility = View.GONE
                        popularSearchImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_add))
                    }

                View.GONE ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popularSearchTagsLinearLayout.visibility = View.VISIBLE
                        popularSearchImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_cross, theme))
                    }
                    else {
                        popularSearchTagsLinearLayout.visibility = View.VISIBLE
                        popularSearchImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_cross))
                    }
            }
        }

        localAPIFetch()
    }

    private fun localAPIFetch() {
        val favoritesList = SharedPref.getFavoritesList(this)
        val url = Constants.apiPath + "/api/v1/pokemon/"
        VolleyService.makeJSONArrayRequest(this@MainActivity, url, Request.Method.GET, null, tag,
            object : VolleyService.JSONArrayInterface {
                override fun onJSONArraySuccess(response: JSONArray) {
                    for (i in 0 until response.length()) {
                        pokemonArray[i] = PokemonModel(response[i] as JSONObject?)
                        if (!favoritesList.isNullOrEmpty() && favoritesList.contains(pokemonArray[i].getID())) {
                            pokemonArray[i].setFavorite(true)
                        }
                    }
                    progressBar.visibility = View.GONE
                    pokemonListView.visibility = View.VISIBLE
                    searchEditText.isEnabled = true
                }

                override fun onError(error: VolleyError) {
                    progressBar.visibility = View.GONE
                    VolleyService.handleVolleyError(error, true, this@MainActivity)
                }

            })
    }
}
