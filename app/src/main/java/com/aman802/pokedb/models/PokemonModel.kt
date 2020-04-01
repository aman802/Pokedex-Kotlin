package com.aman802.pokedb.models

import android.util.Log
import org.json.JSONObject
import java.io.Serializable

class PokemonModel(response: JSONObject?) : Serializable {
    private var id: Int = -1
    private var name: String = ""
    private var imageURL: String = ""
    private var type1: String = ""
    private var type2: String = ""
    private var description: String = ""
    private var evolutionID1: Int = -1
    private var evolution1Name: String = ""
    private var evolutionID2: Int = -1
    private var evolution2Name: String = ""
    private var isFavorite: Boolean = false

    init {
        if (response != null) {
            id = response.getInt("id")
            name = response.getString("name")
            imageURL = response.getString("image_url")
            type1 = response.getString("type1")
            if (response.has("type2") && !response.isNull("type2"))
                type2 = response.getString("type2")
            description = response.getString("description")
            if (response.has("evolutionID1") && !response.isNull("evolutionID1")) {
                evolutionID1 = response.getInt("evolutionID1")
                evolution1Name = response.getString("evolution1Name")
            }
            if (response.has("evolutionID1") && !response.isNull("evolutionID2")) {
                evolutionID2 = response.getInt("evolutionID2")
                evolution2Name = response.getString("evolution2Name")
            }
        }
    }

    fun getID(): Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getImageURL(): String {
        return imageURL
    }

    fun getType1(): String {
        return type1
    }

    fun getType2(): String? {
        return type2
    }

    fun getDesription(): String {
        return description
    }

    fun getEvolution1(): Int {
        return evolutionID1
    }

    fun getEvolution2(): Int {
        return evolutionID2
    }

    fun getEvolution1Name(): String {
        return evolution1Name
    }

    fun getEvolution2Name(): String {
        return evolution2Name
    }

    fun isFavorite(): Boolean {
        return isFavorite
    }

    fun setFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
    }

    fun printPokemon() {
        Log.d("Pokemon", "Name: $name")
        Log.d("Pokemon", "ID: $id")
        Log.d("Pokemon", "Type1: $type1")
        Log.d("Pokemon", "Type2: $type2")
        Log.d("Pokemon", "Description: $description")
        Log.d("Pokemon", "Evolution1: $evolutionID1")
        Log.d("Pokemon", "Evolution1Name: $evolution1Name")
        Log.d("Pokemon", "Evolution2: $evolutionID2")
        Log.d("Pokemon", "Evolution2Name: $evolution2Name")
    }
}