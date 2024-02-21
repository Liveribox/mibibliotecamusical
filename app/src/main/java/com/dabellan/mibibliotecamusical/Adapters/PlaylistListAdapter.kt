package com.dabellan.mibibliotecamusical.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dabellan.mibibliotecamusical.Entities.Playlist
import com.dabellan.mibibliotecamusical.OnClickListener
import com.dabellan.mibibliotecamusical.R
import com.dabellan.mibibliotecamusical.databinding.ItemPlaylistBinding

class PlaylistListAdapter(private var listener: OnClickListener):
    ListAdapter<Playlist, RecyclerView.ViewHolder>(PlaylistDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemPlaylistBinding.bind(view)

        fun setListener(playlist: Playlist) {
            with(binding) {
                //root.setOnClickListener { listener.onClickCancion(playlist) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playlist = getItem(position)

        with(holder as ViewHolder) {
            setListener(playlist)

            with(binding) {
                tvPlaylistName.text = playlist.titulo
                /*
                Glide.with(context)
                    .load(store.photoUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imagePhoto)

                 */
            }
        }
    }


    class PlaylistDiffCallback: DiffUtil.ItemCallback<Playlist>() {
        override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem == newItem
        }
    }

}