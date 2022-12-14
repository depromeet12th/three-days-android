package com.depromeet.threedays.create.emoji

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core.util.Emoji
import com.depromeet.threedays.create.databinding.ItemEmojiBinding

class EmojiListAdapter(private val onEmojiClick: (emojiString: String) -> Unit):
    ListAdapter<EmojiItem, EmojiListAdapter.EmojiViewHolder>(CALLBACK){

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

    class EmojiViewHolder(private val binding: ItemEmojiBinding, private val onEmojiClick: (emojiString: String) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: EmojiItem) {
            binding.tvEmoji.text = data.emojiString
            binding.tvEmoji.setOnSingleClickListener {
                onEmojiClick.invoke(data.emojiString)
            }
        }
    }

    companion object CALLBACK : DiffUtil.ItemCallback<EmojiItem>() {
        override fun areItemsTheSame(oldItem: EmojiItem, newItem: EmojiItem): Boolean {
            return oldItem.emojiString == newItem.emojiString
        }

        override fun areContentsTheSame(oldItem: EmojiItem, newItem: EmojiItem): Boolean {
            return oldItem == newItem
        }
    }
}

data class EmojiItem (
    val emojiString: String
)

fun List<Int>.toEmojiItemList() : List<EmojiItem> {
    return this.map { EmojiItem(emojiString = Emoji().getEmojiString(it)) }
}
