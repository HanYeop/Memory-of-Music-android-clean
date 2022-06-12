package com.hanyeop.presentation.view.music_list.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hanyeop.domain.model.music.Music
import com.hanyeop.presentation.databinding.ItemMusicListBinding

class MusicListAdapter(private val listener: MusicListAdapterListener)
    : ListAdapter<Music, MusicListAdapter.ViewHolder>(diffUtil),Filterable {

    private var originalList = arrayListOf<Music>()

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values = if (constraint.isNullOrEmpty()){
                        originalList
                    }else{
                        originalList.filter { it.title.contains(constraint.toString()) }
                    }
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as? List<Music>)
            }
        }
    }

    fun setItem(items: List<Music>){
        originalList.clear()
        originalList.addAll(items)
        submitList(originalList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMusicListBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                root.setOnClickListener {
                    listener.onItemClicked(getItem(adapterPosition))
                }
                imageOther.setOnClickListener {
                    listener.onOtherButtonClicked(getItem(adapterPosition))
                }
            }
        }
        fun bind(music: Music){
            binding.music = music
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMusicListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Music>(){
            override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}