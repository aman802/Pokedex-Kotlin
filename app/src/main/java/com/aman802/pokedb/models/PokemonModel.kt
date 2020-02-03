package com.aman802.pokedb.models

import org.json.JSONArray
import org.json.JSONObject

public class PokemonModel(response: JSONObject) {
    private var id: Int = response.getInt("id")
    private var name: String = response.getString("name")
    private var imageURL: String = response.getJSONObject("sprites").getString("front_default")
    private var types: ArrayList<String> = ArrayList()

    init {
        val typesJSONArray: JSONArray = response.getJSONArray("types")
        for (i: Int in 0 until typesJSONArray.length()) {
            val typesJSONObject: JSONObject = typesJSONArray.getJSONObject(i).getJSONObject("type")
            types.add(typesJSONObject.getString("name"))
        }
    }

    public fun getID(): Int {
        return id
    }

    public fun setID(id: Int) {
        this.id = id
    }

    public fun getName(): String {
        return name
    }

    public fun setName(name: String) {
        this.name = name
    }

    public fun getImageURL(): String {
        return imageURL
    }

    public fun setImageURL(url: String) {
        this.imageURL = url
    }

    public fun getTypes(): ArrayList<String> {
        return types
    }

    public fun setTypes(types: ArrayList<String>) {
        this.types = types
    }
}