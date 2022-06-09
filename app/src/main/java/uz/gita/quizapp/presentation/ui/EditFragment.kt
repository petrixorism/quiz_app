package uz.gita.quizapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.quizapp.R
import uz.gita.quizapp.databinding.FragmentEditBinding
import uz.gita.quizapp.presentation.adapter.TestEditAdapter
import uz.gita.quizapp.presentation.viewmodel.TestEditViewModel
import uz.gita.quizapp.presentation.viewmodel.impl.TestEditViewModelImpl
import uz.gita.quizapp.util.showSnackBar
import uz.gita.quizapp.util.showToast

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.fragment_edit) {

    private val binding by viewBinding(FragmentEditBinding::bind)
    private val viewModel: TestEditViewModel by viewModels<TestEditViewModelImpl>()
    private val args by navArgs<EditFragmentArgs>()
    private val adapter by lazy { TestEditAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.backLiveData.observe(this) {
            requireActivity().onBackPressed()
        }

        viewModel.errorLiveData.observe(this) {
            showToast(it)
        }

        viewModel.failLiveData.observe(this) {
            showSnackBar(it)
        }

        viewModel.deleteLiveData.observe(this) {
            showSnackBar("O'chirildi")
        }

        viewModel.editTestLiveData.observe(this) {
            showSnackBar("O'zgartirildi")
        }
        viewModel.deleteCategoryLiveData.observe(this) {
            showToast("Barcha testlar o'chirildi")
            requireActivity().onBackPressed()
        }

        viewModel.testListLiveData.observe(this) {
            if (it.isEmpty()) {
                viewModel.deleteCategory(args.category)
            }
            adapter.submitList(it)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            title.text = args.category
            testRv.adapter = adapter

            backBtn.setOnClickListener {
                viewModel.back()
            }
        }
        adapter.setEditClick {
            viewModel.editTest(it)
        }
        adapter.setDeleteClick {
            viewModel.deleteTest(it)
        }
        viewModel.getTestsByCategory(args.category)

    }

}