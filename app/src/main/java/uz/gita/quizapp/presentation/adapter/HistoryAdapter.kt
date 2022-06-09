package uz.gita.quizapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.quizapp.data.model.CategoryData
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.databinding.ItemCategoryBinding
import uz.gita.quizapp.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<HistoryData, HistoryAdapter.ViewHolder>(MyDiffUtil) {

    private var itemClick: ((HistoryData) -> Unit)? = null

    inner class ViewHolder(private val itemBinding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {

            itemBinding.root.setOnClickListener {
                itemClick!!.invoke(getItem(absoluteAdapterPosition))
            }

        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                itemBinding.categoryTv.text = this.category
                itemBinding.nameTv.text = this.name

                val seconds = this.duration.toInt() / 1000

                val duration = if (seconds < 60) {
                    (seconds).toString() + " sek"
                } else {
                    "${(seconds / 60)} min ${(seconds) % 60} sek"
                }

                itemBinding.durationTv.text = duration
                itemBinding.resultTv.text = this.result


            }
        }
    }

    object MyDiffUtil : DiffUtil.ItemCallback<HistoryData>() {
        override fun areItemsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setItemClick(block: ((HistoryData) -> Unit)) {
        itemClick = block
    }

}