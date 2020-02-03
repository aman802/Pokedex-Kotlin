package com.aman802.pokedb.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private val pokemonCount = 10
    private lateinit var pokemonListView: ListView
    private lateinit var progressBar: ProgressBar
    private var pokemonList: ArrayList<PokemonModel> = ArrayList(pokemonCount)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.activity_main_progress_bar)
        pokemonListView = findViewById(R.id.activity_main_pokemon_list_view)
        progressBar.visibility = View.VISIBLE
        fetchPokemonList(pokemonCount)
    }

    private fun fetchPokemonList(size: Int) {
        for (i in 1 until size+1) {
            val url = "https://pokeapi.co/api/v2/pokemon/$i/"
            VolleyService.makeVolleyRequest(this@MainActivity, url, Request.Method.GET, null, tag,
                object : VolleyService.VolleyInterface {
                    override fun onSuccess(response: JSONObject) {
                        pokemonList.add(PokemonModel(response))
                        if (pokemonList.size == size) {
                            val adapter = PokemonListAdapter(this@MainActivity, pokemonList)
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
