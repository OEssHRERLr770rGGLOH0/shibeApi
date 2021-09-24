package com.tobianoapps.shibeapi.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.tobianoapps.shibeapi.R
import com.tobianoapps.shibeapi.databinding.ShibeItemBinding
import com.tobianoapps.shibeapi.list.api.ShibeModels.Shibe


/**
 * A recycler view adapter used to display Shibe image and captions in [ListFragment].
 */
class ListAdapter(
    private val callback: (
        shibe: Shibe,
        imageView: ShapeableImageView,
        textView: AppCompatTextView,
        layout: ConstraintLayout
    ) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ShibeViewHolder(
        val viewBinding: ShibeItemBinding
    ): RecyclerView.ViewHolder(viewBinding.root) {
        companion object {
            fun getLayout(): Int {
                return R.layout.shibe_item
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Shibe>() {
        override fun areItemsTheSame(oldItem: Shibe, newItem: Shibe): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Shibe, newItem: Shibe): Boolean {
            return oldItem.url == newItem.url
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShibeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                ShibeViewHolder.getLayout(),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    /**
     * Item is populated via data binding directly in the xml layout. We only bind the click
     * listener here.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        (holder as ShibeViewHolder).viewBinding.apply {
            this.shibe = currentItem
            this.root.setOnClickListener {
                callback.invoke(
                    currentItem,
                    this.iv,
                    this.tv,
                    this.root as ConstraintLayout
                )
            }
        }
    }
}