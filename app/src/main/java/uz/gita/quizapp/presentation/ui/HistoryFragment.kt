package uz.gita.quizapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.quizapp.R
import uz.gita.quizapp.databinding.FragmentHistoryBinding
import uz.gita.quizapp.presentation.adapter.HistoryAdapter
import uz.gita.quizapp.presentation.viewmodel.HistoryViewModel
import uz.gita.quizapp.presentation.viewmodel.impl.HistoryViewModelImpl
import uz.gita.quizapp.util.isEmptyOrBlank
import uz.gita.quizapp.util.showSnackBar
import uz.gita.quizapp.util.showToast

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val binding by viewBinding(FragmentHistoryBinding::bind)
    private val viewModel: HistoryViewModel by viewModels<HistoryViewModelImpl>()
    private val adapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.historyListLiveData.observe(this) {
            adapter.submitList(it.sortedByDescending { it.time })
        }
        viewModel.failLiveData.observe(this) {
            showToast(it)
        }
        viewModel.errorLiveData.observe(this) {
            showSnackBar(it)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.historyRv.adapter = adapter

    }

}