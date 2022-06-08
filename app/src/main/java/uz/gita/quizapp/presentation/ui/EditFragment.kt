package uz.gita.quizapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.quizapp.R
import uz.gita.quizapp.databinding.FragmentEditBinding
import uz.gita.quizapp.presentation.viewmodel.TestEditViewModel
import uz.gita.quizapp.presentation.viewmodel.impl.TestEditViewModelImpl

class EditFragment : Fragment(R.layout.fragment_edit) {

    private val binding by viewBinding(FragmentEditBinding::bind)
    private val viewModel: TestEditViewModel by viewModels<TestEditViewModelImpl>()
    private val args by navArgs<EditFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.backLiveData.observe(this) {
            requireActivity().onBackPressed()
        }

        viewModel.errorLiveData
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

}