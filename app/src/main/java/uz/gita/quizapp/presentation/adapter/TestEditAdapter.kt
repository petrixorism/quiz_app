package uz.gita.quizapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.quizapp.data.model.TestData
import uz.gita.quizapp.databinding.ItemTestBinding

class TestEditAdapter : ListAdapter<TestData, TestEditAdapter.ViewHolder>(MyDiffUtil) {

    private var deleteClick: ((TestData) -> Unit)? = null
    private var editClick: ((TestData) -> Unit)? = null

    inner class ViewHolder(private val itemBinding: ItemTestBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {

            itemBinding.deleteBtn.setOnClickListener {
                deleteClick!!.invoke(getItem(absoluteAdapterPosition))
            }
            itemBinding.editBtn.setOnClickListener {
                val data = getItem(absoluteAdapterPosition)
                editClick!!.invoke(
                    TestData(
                        category = data.category,
                        id = data.id,
                        question = itemBinding.questionEt.text.toString(),
                        option1 = itemBinding.option1Et.text.toString(),
                        option2 = itemBinding.option2Et.text.toString(),
                        option3 = itemBinding.option3Et.text.toString(),
                        option4 = itemBinding.option4Et.text.toString(),
                        answer = itemBinding.option1Et.text.toString()
                    )
                )
            }

        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                itemBinding.categorieEt.text = category
                itemBinding.questionEt.setText(question)
                itemBinding.option1Et.setText(option1)
                itemBinding.option2Et.setText(option2)
                itemBinding.option3Et.setText(option3)
                itemBinding.option4Et.setText(option4)
            }
        }
    }

    object MyDiffUtil : DiffUtil.ItemCallback<TestData>() {
        override fun areItemsTheSame(oldItem: TestData, newItem: TestData): Boolean {
            return oldItem.id == newItem.id && oldItem.question == newItem.question
        }

        override fun areContentsTheSame(oldItem: TestData, newItem: TestData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setDeleteClick(block: ((TestData) -> Unit)) {
        deleteClick = block
    }

    fun setEditClick(block: ((TestData) -> Unit)) {
        editClick = block
    }

}