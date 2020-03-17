package com.aman802.pokedb.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import com.aman802.pokedb.R
import com.aman802.pokedb.adapters.PokemonListAdapter
import com.aman802.pokedb.models.PokemonModel
import com.aman802.pokedb.network.VolleyService
import com.android.volley.Request
import com.android.volley.VolleyError
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val tag: String = MainActivity::class.java.simpleName
    private val pokemonCount = 151
    private lateinit var searchEditText: EditText
    private lateinit var pokemonListView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.activity_main_progress_bar)
        searchEditText = findViewById(R.id.activity_main_search_edit_text)
        pokemonListView = findViewById(R.id.activity_main_pokemon_list_view)

        progressBar.visibility = View.VISIBLE

        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s.toString())
            }

        })

        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        val pokemonArray = Array(pokemonCount) { PokemonModel(null) }
        for (i in 0 until pokemonCount) {
            val id = i+1
            val url = "https://pokeapi.co/api/v2/pokemon/$id/"
            VolleyService.makeVolleyRequest(this@MainActivity, url, Request.Method.GET, null, tag,
                object : VolleyService.VolleyInterface {
                    override fun onSuccess(response: JSONObject) {
                        pokemonArray[i] = PokemonModel(response)
                        if (i == pokemonCount-1) {
                            adapter = PokemonListAdapter(this@MainActivity, pokemonArray)
                            pokemonListView.adapter = adapter
                            progressBar.visibility = View.GONE
                        }
                    }

                    override fun onError(error: VolleyError) {
                        VolleyService.handleVolleyError(error, true, this@MainActivity)
                    }
                })
        }
    }
}
