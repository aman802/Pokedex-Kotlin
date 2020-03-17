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

        val picasso = Picasso.get()
        picasso.load(pokemonModel.getImageURL()).into(imageView)
        nameTextView.text = Helper.getCamelCaseName(pokemonModel.getName())
        numberTextView.text = Helper.getThreeDigitNumber(pokemonModel.getID().toString())

        val types = pokemonModel.getTypes()
        if (types.size == 1) {
            type1.setText(Helper.getCamelCaseName(types[0]))
            type1.setColor(Helper.getColorForType(context, types[0]))
            type2.setVisibility(false)
        }
        else {
            type1.setText(Helper.getCamelCaseName(types[1]))
            type1.setColor(Helper.getColorForType(context, types[1]))
            type2.setText(Helper.getCamelCaseName(types[0]))
            type2.setColor(Helper.getColorForType(context, types[0]))
            type2.setVisibility(true)
        }

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