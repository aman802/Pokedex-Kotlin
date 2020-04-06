package com.aman802.pokedb.activities

import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.aman802.pokedb.Helper
import com.aman802.pokedb.R
import com.aman802.pokedb.SharedPref
import com.aman802.pokedb.customViews.LockableScrollView
import com.aman802.pokedb.customViews.tagView
import com.aman802.pokedb.models.PokemonModel
import com.squareup.picasso.Picasso

class DedicatedActivity : AppCompatActivity() {

    private val uriBaseString = "https://veekun.com/dex/media/pokemon/sugimori/"
    private var set: Boolean = false
    private lateinit var pokemonModel: PokemonModel
    private lateinit var superConstraintLayout: ConstraintLayout
    private lateinit var backImageView: ImageView
    private lateinit var headerNameTextView: TextView
    private lateinit var favoriteImageView: ImageView
    private lateinit var pokemonImageView: ImageView
    private lateinit var upperRelativeLayout: RelativeLayout
    private lateinit var pokeballImageView: ImageView
    private lateinit var lowerRelativeLayout: RelativeLayout
    private lateinit var contentScrollView: LockableScrollView
    private lateinit var contentLinearLayout: LinearLayout
    private lateinit var nameTextView: TextView
    private lateinit var type1: tagView
    private lateinit var type2: tagView
    private lateinit var weaknessGroup2: ConstraintLayout
    private lateinit var weaknessGroup3: ConstraintLayout
    private lateinit var weakness1: tagView
    private lateinit var weakness2: tagView
    private lateinit var weakness3: tagView
    private lateinit var weakness4: tagView
    private lateinit var weakness5: tagView
    private lateinit var weakness6: tagView
    private lateinit var descriptionTextView: TextView
    private lateinit var evolutionsRelativeLayout: RelativeLayout
    private lateinit var evolution1LinearLayout: LinearLayout
    private lateinit var evolution1ImageView: ImageView
    private lateinit var evolution1TextView: TextView
    private lateinit var evolution2ArrowImageView: ImageView
    private lateinit var evolution2LinearLayout: LinearLayout
    private lateinit var evolution2ImageView: ImageView
    private lateinit var evolution2TextView: TextView
    private lateinit var evolution3ArrowImageView: ImageView
    private lateinit var evolution3LinearLayout: LinearLayout
    private lateinit var evolution3ImageView: ImageView
    private lateinit var evolution3TextView: TextView
    private lateinit var noEvolutionsTextView: TextView
    private lateinit var hpTextView: TextView
    private lateinit var defenseTextView: TextView
    private lateinit var attackTextView: TextView
    private lateinit var speedTextView: TextView
    private lateinit var hpProgressBar: ProgressBar
    private lateinit var defenseProgressBar: ProgressBar
    private lateinit var attackProgressBar: ProgressBar
    private lateinit var speedProgressBar: ProgressBar
    private lateinit var seeMoreLinearLayout: LinearLayout
    private val picasso = Picasso.get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dedicated)

        if (intent.extras != null) {
            pokemonModel = intent.getSerializableExtra("Pokemon") as PokemonModel
        }

        val pokemonID = pokemonModel.getID()

        superConstraintLayout = findViewById(R.id.activity_dedicated_super_constraint_layout)
        backImageView = findViewById(R.id.activity_dedicated_back_image_view)
        headerNameTextView = findViewById(R.id.activity_dedicated_header_name_text_view)
        favoriteImageView = findViewById(R.id.activity_dedicated_favorite_image_view)
        pokemonImageView = findViewById(R.id.activity_dedicated_pokemon_image_view)
        upperRelativeLayout = findViewById(R.id.activity_dedicated_upper_relative_layout)
        pokeballImageView = findViewById(R.id.activity_dedicated_pokeball_background)
        lowerRelativeLayout = findViewById(R.id.activity_dedicated_lower_relative_layout)
        contentScrollView = findViewById(R.id.activity_dedicated_content_scroll_view)
        contentLinearLayout = findViewById(R.id.activity_dedicated_content_linear_layout)
        nameTextView = findViewById(R.id.activity_dedicated_name_text_view)
        type1 = tagView(findViewById(R.id.activity_dedicated_type1))
        type2 = tagView(findViewById(R.id.activity_dedicated_type2))
        weaknessGroup2 = findViewById(R.id.activity_dedicated_weakness_group_2)
        weaknessGroup3 = findViewById(R.id.activity_dedicated_weakness_group_3)
        weakness1 = tagView(findViewById(R.id.activity_dedicated_weakness1))
        weakness2 = tagView(findViewById(R.id.activity_dedicated_weakness2))
        weakness3 = tagView(findViewById(R.id.activity_dedicated_weakness3))
        weakness4 = tagView(findViewById(R.id.activity_dedicated_weakness4))
        weakness5 = tagView(findViewById(R.id.activity_dedicated_weakness5))
        weakness6 = tagView(findViewById(R.id.activity_dedicated_weakness6))
        descriptionTextView = findViewById(R.id.activity_dedicated_description_text_view)
        evolutionsRelativeLayout = findViewById(R.id.activity_dedicated_evolutions_relative_layout)
        evolution1LinearLayout = findViewById(R.id.activity_dedicated_evolution_1_linear_layout)
        evolution1ImageView = findViewById(R.id.activity_dedicated_evolution_1_image_view)
        evolution1TextView = findViewById(R.id.activity_dedicated_evolution_1_text_view)
        evolution2ArrowImageView = findViewById(R.id.activity_dedicated_evolution_2_arrow_image_view)
        evolution2LinearLayout = findViewById(R.id.activity_dedicated_evolution_2_linear_layout)
        evolution2ImageView = findViewById(R.id.activity_dedicated_evolution_2_image_view)
        evolution2TextView = findViewById(R.id.activity_dedicated_evolution_2_text_view)
        evolution3ArrowImageView = findViewById(R.id.activity_dedicated_evolution_3_arrow_image_view)
        evolution3LinearLayout = findViewById(R.id.activity_dedicated_evolution_3_linear_layout)
        evolution3ImageView = findViewById(R.id.activity_dedicated_evolution_3_image_view)
        evolution3TextView = findViewById(R.id.activity_dedicated_evolution_3_text_view)
        noEvolutionsTextView = findViewById(R.id.activity_dedicated_no_evolutions_text_view)
        hpTextView = findViewById(R.id.activity_dedicated_hp_text_view)
        defenseTextView = findViewById(R.id.activity_dedicated_defense_text_view)
        attackTextView = findViewById(R.id.activity_dedicated_attack_text_view)
        speedTextView = findViewById(R.id.activity_dedicated_speed_text_view)
        hpProgressBar = findViewById(R.id.activity_dedicated_hp_progress_bar)
        defenseProgressBar = findViewById(R.id.activity_dedicated_defense_progress_bar)
        attackProgressBar = findViewById(R.id.activity_dedicated_attack_progress_bar)
        speedProgressBar = findViewById(R.id.activity_dedicated_speed_progress_bar)
        seeMoreLinearLayout = findViewById(R.id.activity_dedicated_see_more_linear_layout)

        backImageView.setOnClickListener {
            onBackPressed()
            finish()
        }

        favoriteImageView.setOnClickListener {
            // if not favorite now, wants to be favorite
            if (!pokemonModel.isFavorite()) {
                favoriteImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_filled))
                SharedPref.addFavorite(this, pokemonID)
                pokemonModel.setFavorite(true)
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
            else {
                favoriteImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_border))
                SharedPref.removeFavorite(this, pokemonID)
                pokemonModel.setFavorite(false)
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
        contentScrollView.setScrollable(false)

        seeMoreLinearLayout.setOnClickListener {
            animateLayout()
        }

        val backgroundColor = Helper.getBackgroundColorForType(this, pokemonModel.getType1())

        superConstraintLayout.setBackgroundColor(backgroundColor)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = backgroundColor
        }

        val favoritesList = SharedPref.getFavoritesList(this)
        if (!favoritesList.isNullOrEmpty() && favoritesList.contains(pokemonID)) {
            favoriteImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_filled))
        }
        else {
            favoriteImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_border))
        }

        val uriString = uriBaseString + pokemonID + ".png"
        picasso.load(Uri.parse(uriString)).into(pokemonImageView)

        val pokemonName = pokemonModel.getName() + " " + Helper.getThreeDigitNumber(pokemonModel.getID().toString())

        nameTextView.text = Helper.getCamelCaseName(pokemonName)
        headerNameTextView.text = Helper.getCamelCaseName(pokemonName)

        type1.setText(Helper.getCamelCaseName(pokemonModel.getType1()))
        type1.setColor(Helper.getColorForType(this ,pokemonModel.getType1()))



        val type2String = pokemonModel.getType2()
        if (!type2String.isNullOrEmpty()) {
            type2.setVisibility(true)
            type2.setText(Helper.getCamelCaseName(type2String))
            type2.setColor(Helper.getColorForType(this, type2String))
        }
        else {
            type2.setVisibility(false)
        }

//        val weaknessList = Helper.getWeaknessesForType()

        descriptionTextView.text = pokemonModel.getDesription()

        val evolutionID1 = pokemonModel.getEvolution1()
        val evolutionID2 = pokemonModel.getEvolution2()

        // Case 1: When Pokemon doesn't have any evolutions
        if (evolutionID1 == -1 && evolutionID2 == -1) {
            evolutionsRelativeLayout.visibility = View.GONE
            noEvolutionsTextView.visibility = View.VISIBLE
            if (pokemonID in 133..136) {
                noEvolutionsTextView.text = "Eevee Evolution chart is difficult to prepare"
            }
        }
        else {
            evolutionsRelativeLayout.visibility = View.VISIBLE
            noEvolutionsTextView.visibility = View.GONE

            // Case 2: When Pokemon has one evolution
            if (evolutionID2 == -1) {

                evolution3ArrowImageView.visibility = View.GONE
                evolution3LinearLayout.visibility = View.GONE

                val evolution1Name = pokemonModel.getEvolution1Name()
                val uri2 = uriBaseString + evolutionID1 + ".png"

                // Case 2.1: When the current pokemon has an evolution
                if (pokemonID < evolutionID1) {
                    picasso.load(uriString).into(evolution1ImageView)
                    picasso.load(uri2).into(evolution2ImageView)
                    evolution1TextView.text = Helper.getCamelCaseName(pokemonModel.getName()) + '\n' + Helper.getThreeDigitNumber(pokemonID.toString())
                    evolution2TextView.text = Helper.getCamelCaseName(evolution1Name) + '\n' + Helper.getThreeDigitNumber(evolutionID1.toString())
                }

                // Case 2.2: When the current pokemon is an evolution of some other pokemon
                else {
                    picasso.load(uri2).into(evolution1ImageView)
                    picasso.load(uriString).into(evolution2ImageView)
                    evolution1TextView.text = Helper.getCamelCaseName(evolution1Name) + '\n' + Helper.getThreeDigitNumber(evolutionID1.toString())
                    evolution2TextView.text = Helper.getCamelCaseName(pokemonModel.getName()) + '\n' + Helper.getThreeDigitNumber(pokemonID.toString())
                }
            }

            // Case 3: When Pokemon has 2 evolutions
            else {

                evolution3ArrowImageView.visibility = View.VISIBLE
                evolution3LinearLayout.visibility = View.VISIBLE

                val evolution1Name = pokemonModel.getEvolution1Name()
                val evolution2Name = pokemonModel.getEvolution2Name()
                val uri2 = uriBaseString + evolutionID1 + ".png"
                val uri3 = uriBaseString + evolutionID2 + ".png"

                // Case 3.1: When the current pokemon has 2 evolutions. It is guaranteed that evolutionID1 is always less than evolutionID2
                if (pokemonID < evolutionID1) {
                    picasso.load(uriString).into(evolution1ImageView)
                    picasso.load(uri2).into(evolution2ImageView)
                    picasso.load(uri3).into(evolution3ImageView)
                    evolution1TextView.text = Helper.getCamelCaseName(pokemonModel.getName()) + '\n' + Helper.getThreeDigitNumber(pokemonID.toString())
                    evolution2TextView.text = Helper.getCamelCaseName(evolution1Name) + '\n' + Helper.getThreeDigitNumber(evolutionID1.toString())
                    evolution3TextView.text = Helper.getCamelCaseName(evolution2Name) + '\n' + Helper.getThreeDigitNumber(evolutionID2.toString())
                }

                // Case 3.2: When the current pokemon has 1 evolution and evolved from some other pokemon (middle evolution)
                else if (pokemonID < evolutionID2) {
                    picasso.load(uri2).into(evolution1ImageView)
                    picasso.load(uriString).into(evolution2ImageView)
                    picasso.load(uri3).into(evolution3ImageView)
                    evolution1TextView.text = Helper.getCamelCaseName(evolution1Name) + '\n' + Helper.getThreeDigitNumber(evolutionID1.toString())
                    evolution2TextView.text = Helper.getCamelCaseName(pokemonModel.getName()) + '\n' + Helper.getThreeDigitNumber(pokemonID.toString())
                    evolution3TextView.text = Helper.getCamelCaseName(evolution2Name) + '\n' + Helper.getThreeDigitNumber(evolutionID2.toString())
                }

                // Case 3.3: When the current pokemon is the maximum evolution of the series
                else {
                    picasso.load(uri2).into(evolution1ImageView)
                    picasso.load(uri3).into(evolution2ImageView)
                    picasso.load(uriString).into(evolution3ImageView)
                    evolution1TextView.text = Helper.getCamelCaseName(evolution1Name) + '\n' + Helper.getThreeDigitNumber(evolutionID1.toString())
                    evolution2TextView.text = Helper.getCamelCaseName(evolution2Name) + '\n' + Helper.getThreeDigitNumber(evolutionID2.toString())
                    evolution3TextView.text = Helper.getCamelCaseName(pokemonModel.getName()) + '\n' + Helper.getThreeDigitNumber(pokemonID.toString())
                }
            }
        }

        val hp = pokemonModel.getHp()
        val attack = pokemonModel.getAttack()
        val defense = pokemonModel.getDefense()
        val speed = pokemonModel.getSpeed()

        hpTextView.text = hp.toString()
        attackTextView.text = attack.toString()
        defenseTextView.text = defense.toString()
        speedTextView.text = speed.toString()

        setProgressBarData(hpProgressBar, Helper.getPercentForStat(hp, "hp"))
        setProgressBarData(attackProgressBar, Helper.getPercentForStat(attack, "attack"))
        setProgressBarData(defenseProgressBar, Helper.getPercentForStat(defense, "defense"))
        setProgressBarData(speedProgressBar, Helper.getPercentForStat(speed, "speed"))


    }

    private fun animateLayout() {
        val constraint1 = ConstraintSet()
        constraint1.clone(superConstraintLayout)
        val constraint2 = ConstraintSet()
        constraint2.clone(this, R.layout.activity_dedicated_expanded)

        TransitionManager.beginDelayedTransition(superConstraintLayout)
        val constraint = if (set) constraint1 else constraint2
        upperRelativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        upperRelativeLayout.elevation = 10.0f
        pokemonImageView.elevation = 10.1f
        lowerRelativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        constraint.applyTo(superConstraintLayout)

        pokeballImageView.alpha = 0.0f

        val animationDown = TranslateAnimation(0.0f, 0.0f, 0.0f, 200.0f)
        animationDown.duration = 500
        animationDown.fillAfter = true
        seeMoreLinearLayout.startAnimation(animationDown)

        nameTextView.visibility = View.GONE
        headerNameTextView.visibility = View.VISIBLE

        val layoutParams: RelativeLayout.LayoutParams = contentLinearLayout.layoutParams as RelativeLayout.LayoutParams
        val marginDP = Helper.convertToDP(this, 16)
        layoutParams.setMargins(marginDP, 0, marginDP, 0)
        contentLinearLayout.layoutParams = layoutParams
        contentLinearLayout.setPadding(0, marginDP, 0, marginDP)

        // This enables scroll in expanded view
        contentScrollView.setScrollable(true)
    }

    private fun setProgressBarData(progressBar: ProgressBar, percent: Int) {
        when {
            percent <= 25 -> {
                progressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lowestStat))
                progressBar.progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lowestStatTransparent))
            }

            percent <= 50 -> {
                progressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lowStat))
                progressBar.progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lowStatTransparent))
            }

            percent <= 75 -> {
                progressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.midStat))
                progressBar.progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.midStatTransparent))
            }

            percent <= 100 -> {
                progressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.highStat))
                progressBar.progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.highStatTransparent))
            }
        }
        progressBar.progress = percent
    }
}
