package com.aman802.pokedb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aman802.pokedb.R
import com.aman802.pokedb.customViews.tagView
import com.aman802.pokedb.models.PokemonModel
import com.squareup.picasso.Picasso
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class PokemonListAdapter(context: Context, private var data: List<PokemonModel>) : BaseAdapter(), Filterable {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var filteredData = data
    private val mFilter = PokemonFilter()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_pokemon_list_view, parent, false)

        val imageView = rowView.findViewById(R.id.item_pokemon_list_image_view) as ImageView
        val nameTextView = rowView.findViewById<TextView>(R.id.item_pokemon_list_name_text_view)
        val numberTextView = rowView.findViewById<TextView>(R.id.item_pokemon_list_number_text_view)
        val type1 = tagView(rowView.findViewById(R.id.item_pokemon_list_type_1))
        val type2 = tagView(rowView.findViewById(R.id.item_pokemon_list_type_2))

        val pokemonModel = getItem(position) as PokemonModel

        val picasso = Picasso.get()
        picasso.load(pokemonModel.getImageURL()).into(imageView)
        nameTextView.text = getCamelCaseName(pokemonModel.getName())
        numberTextView.text = getThreeDigitNumber(pokemonModel.getID().toString())

        val types = pokemonModel.getTypes()
        if (types.size == 1) {
            type1.setText(getCamelCaseName(types[0]))
            type2.setVisibility(false)
        }
        else {
            type1.setText(getCamelCaseName(types[0]))
            type2.setText(getCamelCaseName(types[1]))
            type2.setVisibility(true)
        }

        return rowView
    }

    override fun getItem(position: Int): Any {
        return filteredData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return filteredData.size
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

    override fun getFilter(): Filter {
        return mFilter
    }

    private inner class PokemonFilter: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (constraint == null || constraint.isEmpty()) {
                filterResults.values = data
                filterResults.count = data.size
            }
            else {
                val filterString = constraint.toString().toLowerCase(Locale.US)
                val newList = ArrayList<PokemonModel>()
                for (pokemon in data) {
                    if (pokemon.getName().toLowerCase(Locale.US).contains(filterString)) {
                        newList.add(pokemon)
                    }
                }

                filterResults.values = newList
                filterResults.count = newList.size
            }

            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredData = results?.values as List<PokemonModel>
            notifyDataSetChanged()
        }

    }
}