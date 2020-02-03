package com.aman802.pokedb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.aman802.pokedb.R
import com.aman802.pokedb.models.PokemonModel
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class PokemonListAdapter(context: Context, private val data: ArrayList<PokemonModel>) : BaseAdapter() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_pokemon_list_view, parent, false)

        val imageView = rowView.findViewById(R.id.item_pokemon_list_image_view) as ImageView
        val nameTextView = rowView.findViewById<TextView>(R.id.item_pokemon_list_name_text_view)
        val numberTextView = rowView.findViewById<TextView>(R.id.item_pokemon_list_number_text_view)
        val typesLinearLayout = rowView.findViewById<LinearLayout>(R.id.item_pokemon_list_types_linear_layout)

        val pokemonModel = getItem(position) as PokemonModel

        val picasso = Picasso.get()
        picasso.load(pokemonModel.getImageURL()).into(imageView)
        nameTextView.text = getCamelCaseName(pokemonModel.getName())
        numberTextView.text = getThreeDigitNumber(pokemonModel.getID().toString())

        val types = pokemonModel.getTypes()
        for (i in 0 until types.size) {
            val tagView = inflater.inflate(R.layout.item_tag, null, false)
            val tagName = tagView.findViewById<TextView>(R.id.item_tag_name)
            tagName.text = getCamelCaseName(types[i])
            typesLinearLayout.addView(tagView)
        }

        return rowView
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    private fun getCamelCaseName(name: String): String {
        val stringBuilder = StringBuilder()
        for (i in name.indices) {
            if (i == 0) {
                stringBuilder.append(name[i].toUpperCase())
            }
            else {
                stringBuilder.append(name[i])
            }
        }

        return stringBuilder.toString()
    }

    private fun getThreeDigitNumber(number: String): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("(#")
        if (number.length == 1) {
            stringBuilder.append("00")
        }
        else if (number.length == 2) {
            stringBuilder.append("0")
        }
        stringBuilder.append(number)
        stringBuilder.append(")")

        return stringBuilder.toString()
    }
}