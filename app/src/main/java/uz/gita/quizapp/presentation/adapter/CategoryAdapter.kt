package uz.gita.quizapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.quizapp.data.model.CategoryData
import uz.gita.quizapp.databinding.ItemCategoryBinding

class CategoryAdapter : ListAdapter<CategoryData, CategoryAdapter.ViewHolder>(MyDiffUtil) {

    private var itemClick: ((CategoryData) -> Unit)? = null

    inner class ViewHolder(private val itemBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {

            itemBinding.root.setOnClickListener {
                itemClick!!.invoke(getItem(absoluteAdapterPosition))
            }

        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                itemBinding.categoryTv.text = this.categoryName
            }
        }

    }

    object MyDiffUtil : DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem.categoryName == newItem.categoryName
        }

        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setItemClick(block: ((CategoryData) -> Unit)) {
        itemClick = block
    }

}