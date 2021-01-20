package com.example.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.WeatherResponse.Pokemon
import com.example.pokedex.WeatherResponse.PokemonResponse
import com.example.pokedex.binding
import com.example.pokedex.listPokemenAll
import retrofit2.Callback
import timber.log.Timber


class PokemonAdapter(
        private val context: Context,
        private val dataset: List<Pokemon>,
        private val onClick: onClickListener
) : RecyclerView.Adapter<PokemonAdapter.ItemViewHolder>(){

//    class ItemViewHolder(private val view: View):RecyclerView.ViewHolder(view){
//
//        val name: TextView = view.findViewById(R.id.lName)
//        //val url: TextView = view.findViewById(R.id.lUrl)
//        val artwork: ImageView = view.findViewById(R.id.artwork)
//        var card: CardView = view.findViewById(R.id.pokemon_card)
//
//    }

    inner class ItemViewHolder(private val view: View):RecyclerView.ViewHolder(view){

        val name: TextView = view.findViewById(R.id.lName)
        //val url: TextView = view.findViewById(R.id.lUrl)
        val artwork: ImageView = view.findViewById(R.id.artwork)
        var card: CardView = view.findViewById(R.id.pokemon_card)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
//                .inflate(R.layout.list, parent, false)
                .inflate(R.layout.fragment_list_detail, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items = dataset[position]
        holder.name.text = items.getName()
        //holder.url.text = items.getUrl()


        Timber.i("on do RES ssssssssssssss %s", items)
        Timber.i("on do RES ssssssssssssss NAME %s", items.getName())
        Timber.i("on do RES ssssssssssssss URL %s", items.getUrl())
        Timber.i("on do RES ssssssssssssss NUMBER %s", items.getNumber())

        Glide.with(context)
//            .load("https://honeysanime.com/wp-content/uploads/2020/01/High-School-DxD-Wallpaper.jpg")
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + items.getNumber() + ".png")
                .into(holder.artwork)

//        holder.card.setOnClickListener{
//            Timber.i("on click card")
//        }

        val view = binding.root

        holder.card.setOnClickListener {
            //   Navigation.findNavController(view).navigate(R.id.action_listFragment_to_listPokemenAll )
        }


        holder.itemView.setOnClickListener{
            onClick.onClick(position)
        }
    }


    override fun getItemCount() = dataset.size




}

