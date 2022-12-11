package com.depromeet.threedays.create.emoji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.threedays.core.util.Emoji
import com.depromeet.threedays.create.R
import com.depromeet.threedays.create.databinding.FragmentEmojiBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EmojiBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private var _binding: FragmentEmojiBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        EmojiListAdapter(
            onEmojiClick = { emojiString ->
                onEmojiClick(emojiString)

                this.dismiss()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentEmojiBottomSheetDialogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun getTheme(): Int {
        return com.depromeet.threedays.core_design_system.R.style.RoundCornerBottomSheetDialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.rvEmojiList.adapter = adapter
        adapter.submitList(Emoji().getEmojiList(Emoji.Category.FACE).toEmojiItemList())
        binding.rvEmojiList.layoutManager = GridLayoutManager(requireContext(), 6)
        
        binding.groupEmoji.setOnCheckedChangeListener{_, index ->
            when(index) {
                R.id.rb_face -> adapter.submitList(Emoji().getEmojiList(Emoji.Category.FACE).toEmojiItemList())
                R.id.rb_food -> adapter.submitList(Emoji().getEmojiList(Emoji.Category.FOOD).toEmojiItemList())
                R.id.rb_animal -> adapter.submitList(Emoji().getEmojiList(Emoji.Category.ANIMAL).toEmojiItemList())
                R.id.rb_activity -> adapter.submitList(Emoji().getEmojiList(Emoji.Category.ACTIVITY).toEmojiItemList())
                R.id.rb_object -> adapter.submitList(Emoji().getEmojiList(Emoji.Category.OBJECT).toEmojiItemList())
            }
        }
    }

    companion object {
        const val TAG = "EmojiBottomSheetDialogFragment"

        lateinit var onEmojiClick: (emojiString: String) -> Unit

        fun newInstance(
            onEmojiClick: (emojiString: String) -> Unit
        ): EmojiBottomSheetDialogFragment {
            this.onEmojiClick = onEmojiClick
            return EmojiBottomSheetDialogFragment()
        }
    }
}
