package com.aman802.pokedb.models

import org.json.JSONObject

class PokemonModel(response: JSONObject?) {
    private var id: Int = -1
    private var name: String = ""
    private var imageURL: String = ""
    private var type1: String = ""
    private var type2: String = ""

    init {
        if (response != null) {
            id = response.getInt("id")
            name = response.getString("name")
            imageURL = response.getString("image_url")
            type1 = response.getString("type1")
            type2 = response.getString("type2")
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
        return if (!type2.equals("null", true)) {
            type2
        }
        else {
            null
        }
    }
}