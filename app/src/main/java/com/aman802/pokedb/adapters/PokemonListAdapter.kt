package com.aman802.pokedb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aman802.pokedb.Helper
import com.aman802.pokedb.R
import com.aman802.pokedb.customViews.tagView
import com.aman802.pokedb.models.PokemonModel
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class PokemonListAdapter(private var context: Context, private var data: Array<PokemonModel>) : BaseAdapter(), Filterable {

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

        nameTextView.text = Helper.getCamelCaseName(pokemonModel.getName())
        numberTextView.text = Helper.getThreeDigitNumber(pokemonModel.getID().toString())

        type1.setText(Helper.getCamelCaseName(pokemonModel.getType1()))
        type1.setColor(Helper.getColorForType(context, pokemonModel.getType1()))

        val type2String = pokemonModel.getType2()
        if (type2String != null) {
            type2.setVisibility(true)
            type2.setText(Helper.getCamelCaseName(type2String))
            type2.setColor(Helper.getColorForType(context, type2String))
        }
        else {
            type2.setVisibility(false)
        }

        val picasso = Picasso.get()
        picasso.load(pokemonModel.getImageURL()).into(imageView)

        return rowView
    }

    override fun getItem(position: Int): PokemonModel? {
        return filteredData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return filteredData.size
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
                val newList = ArrayList<PokemonModel?>()
                for (pokemon in data) {
                    if (pokemon.getName().toLowerCase(Locale.US).contains(filterString)) {
                        newList.add(pokemon)
                    }
                }

                val newArray = arrayOfNulls<PokemonModel>(newList.size)
                for (i in 0 until newList.size) {
                    newArray[i] = newList[i]
                }

                filterResults.values = newArray
                filterResults.count = newList.size
            }

            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredData = results?.values as Array<PokemonModel>
            notifyDataSetChanged()
        }

    }
}