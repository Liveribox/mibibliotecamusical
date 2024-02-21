package com.dabellan.mibibliotecamusical.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dabellan.mibibliotecamusical.Entities.Album
import com.dabellan.mibibliotecamusical.OnClickListener
import com.dabellan.mibibliotecamusical.R
import com.dabellan.mibibliotecamusical.databinding.ItemAlbumBinding


class AlbumListAdapter(private var listener: OnClickListener):
    ListAdapter<Album, RecyclerView.ViewHolder>(AlbumDiffCallback()) {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemAlbumBinding.bind(view)

        fun setListener(album: Album) {
            with(binding) {
                //root.setOnClickListener { listener.onClickCancion(playlist) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = getItem(position)

        with(holder as ViewHolder) {
            setListener(album)

            with(binding) {
                tvAlbumName.text = album.titulo
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

    class AlbumDiffCallback: DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }


}