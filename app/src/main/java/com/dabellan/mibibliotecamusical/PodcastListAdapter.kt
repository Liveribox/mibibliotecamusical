package com.dabellan.mibibliotecamusical

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dabellan.mibibliotecamusical.databinding.ItemPlaylistBinding
import com.dabellan.mibibliotecamusical.databinding.ItemPodcastBinding

class PodcastListAdapter(private var listener: OnClickListener):
    ListAdapter<Podcast, RecyclerView.ViewHolder>(PodcastListAdapter.PodcastDiffCallback()) {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemPodcastBinding.bind(view)

        fun setListener(podcast: Podcast) {
            with(binding) {
                //root.setOnClickListener { listener.onClickCancion(playlist) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastListAdapter.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_podcast, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val podcast = getItem(position)

        with(holder as PodcastListAdapter.ViewHolder) {
            setListener(podcast)

            with(binding) {
                tvPodcastName.text = podcast.titulo
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

    class PodcastDiffCallback: DiffUtil.ItemCallback<Podcast>() {
        override fun areItemsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem == newItem
        }
    }
}