package com.dabellan.mibibliotecamusical

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dabellan.mibibliotecamusical.Entities.Cancion
import com.dabellan.mibibliotecamusical.databinding.ItemCancionBinding

class FindListAdapter(private var listener: OnClickListener):
    ListAdapter<Cancion, RecyclerView.ViewHolder>(CancionDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemCancionBinding.bind(view)


        fun setListener(cancion: Cancion) {
            with(binding) {
                //root.setOnClickListener { listener.onClickCancion(cancion) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_cancion, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cancion = getItem(position)

        with(holder as ViewHolder) {
            setListener(cancion)

            with(binding) {
                tvName.text = cancion.titulo

                /*Glide.with(context)
                    .load(cancion.ruta)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imagePhoto)*/


            }
        }
    }


    class CancionDiffCallback: DiffUtil.ItemCallback<Cancion>() {
        override fun areItemsTheSame(oldItem: Cancion, newItem: Cancion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cancion, newItem: Cancion): Boolean {
            return oldItem == newItem
        }
    }

}