package com.depromeet.threedays.create.emoji

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.create.databinding.ItemEmojiBinding
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.util.EmojiUtil

class EmojiListAdapter(private val onEmojiClick: (emoji: Emoji) -> Unit):
    ListAdapter<Emoji, EmojiListAdapter.EmojiViewHolder>(CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        return EmojiViewHolder(
            binding = ItemEmojiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onEmojiClick = onEmojiClick
        )
    }

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EmojiViewHolder(private val binding: ItemEmojiBinding, private val onEmojiClick: (emoji: Emoji) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Emoji) {
            binding.tvEmoji.text = data.value
            binding.tvEmoji.setOnSingleClickListener {
                onEmojiClick.invoke(data)
            }
        }
    }

    companion object CALLBACK : DiffUtil.ItemCallback<Emoji>() {
        override fun areItemsTheSame(oldItem: Emoji, newItem: Emoji): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(oldItem: Emoji, newItem: Emoji): Boolean {
            return oldItem == newItem
        }
    }
}

fun List<Int>.toEmojiItemList() : List<Emoji> {
    return this.map { Emoji(value = EmojiUtil.getEmojiString(it)) }
}
