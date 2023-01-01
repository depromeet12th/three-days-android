package com.depromeet.threedays.mate.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.mate.databinding.ItemOnBoardingMateBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {
    // TODO: 임시 데이터 (일러스터 나오면 변경)
    private var list = mutableListOf(
        com.depromeet.threedays.mate.R.drawable.on_boarding_mate_1,
        com.depromeet.threedays.mate.R.drawable.on_boarding_mate_2,
        com.depromeet.threedays.mate.R.drawable.on_boarding_mate_3
    )

    inner class OnBoardingViewHolder(val binding: ItemOnBoardingMateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(res: Int) {
            binding.ivMate.setImageResource(res)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val binding =
            ItemOnBoardingMateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
