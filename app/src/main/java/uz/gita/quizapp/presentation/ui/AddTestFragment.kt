package uz.gita.quizapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.gita.quizapp.R
import uz.gita.quizapp.data.model.TestData
import uz.gita.quizapp.databinding.FragmentAddTestBinding
import uz.gita.quizapp.presentation.viewmodel.AddTestViewModel
import uz.gita.quizapp.presentation.viewmodel.impl.AddTestViewModelImpl
import uz.gita.quizapp.util.showSnackBar
import uz.gita.quizapp.util.showToast

@AndroidEntryPoint
class AddTestFragment : Fragment(R.layout.fragment_add_test) {

    private val binding by viewBinding(FragmentAddTestBinding::bind)
    private val viewModel: AddTestViewModel by viewModels<AddTestViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.failLiveData.observe(this) {
            showToast(it)
        }
        viewModel.errorLiveData.observe(this) {
            showSnackBar(it)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addTestBtn.setOnClickListener {
            viewModel.insertTest(
                TestData(
                    category = binding.categorieEt.text.toString(),
                    question = binding.questionEt.text.toString(),
                    option1 = binding.option1Et.text.toString(),
                    option2 = binding.option2Et.text.toString(),
                    option3 = binding.option3Et.text.toString(),
                    option4 = binding.option4Et.text.toString(),
                    answer = binding.option1Et.text.toString()
                )
            )
        }
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.insertLiveData.collect() {
                showSnackBar("Test qo'shildi")
            }
        }

    }


}