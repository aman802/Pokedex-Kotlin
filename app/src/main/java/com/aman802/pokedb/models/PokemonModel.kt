package com.aman802.pokedb.models

import org.json.JSONArray
import org.json.JSONObject

class PokemonModel(response: JSONObject?) {
    private var id: Int = -1
    private var name: String = ""
    private var imageURL: String = ""
    private var types: ArrayList<String> = ArrayList()

    init {
        if (response != null) {
            id = response.getInt("id")
            name = response.getString("name")
            imageURL = response.getJSONObject("sprites").getString("front_default")
            val typesJSONArray: JSONArray = response.getJSONArray("types")
            for (i: Int in 0 until typesJSONArray.length()) {
                val typesJSONObject: JSONObject = typesJSONArray.getJSONObject(i).getJSONObject("type")
                types.add(typesJSONObject.getString("name"))
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

    fun getTypes(): ArrayList<String> {
        return types
    }
}