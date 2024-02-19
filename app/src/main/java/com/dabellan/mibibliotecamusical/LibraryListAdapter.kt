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
import com.dabellan.mibibliotecamusical.Entities.Playlist
import com.dabellan.mibibliotecamusical.databinding.ItemCancionBinding
import com.dabellan.mibibliotecamusical.databinding.ItemLibraplaylistBinding
import com.dabellan.mibibliotecamusical.databinding.ItemPlaylistBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class LibraryListAdapter(private var listener: OnClickListener):
    ListAdapter<Playlist, RecyclerView.ViewHolder>(PlaylistDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemLibraplaylistBinding.bind(view)


        fun setListener(playlist: Playlist) {
            with(binding) {
                root.setOnClickListener { listener.onClickPlaylist(playlist) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_libraplaylist, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playlist = getItem(position)

        with(holder as LibraryListAdapter.ViewHolder) {
            setListener(playlist)

            with(binding) {
                tvPlayName.text = playlist.titulo

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