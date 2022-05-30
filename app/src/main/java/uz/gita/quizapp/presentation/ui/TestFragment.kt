package uz.gita.quizapp.presentation.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import uz.gita.quizapp.R
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.data.model.TestData
import uz.gita.quizapp.databinding.DialogEndBinding
import uz.gita.quizapp.databinding.FragmentTestBinding
import uz.gita.quizapp.presentation.viewmodel.TestViewModel
import uz.gita.quizapp.presentation.viewmodel.impl.TestViewModelImpl
import uz.gita.quizapp.util.showSnackBar
import uz.gita.quizapp.util.showToast

@AndroidEntryPoint
class TestFragment : Fragment(R.layout.fragment_test) {

    private val binding by viewBinding(FragmentTestBinding::bind)
    private val viewModel: TestViewModel by viewModels<TestViewModelImpl>()
    private val args by navArgs<TestFragmentArgs>()
    private lateinit var testList: List<TestData>
    private var counter = 0
    private var trueAnswers = 0
    private var time: Long = 0L
    private var buttons = ArrayList<RadioButton>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.testListLiveData.observe(this, testListObserver)
        viewModel.errorLiveData.observe(this) {
            showSnackBar(it)
        }
        viewModel.failLiveData.observe(this) {
            showToast(it)
        }
        viewModel.finishLiveData.observe(this) {
            findNavController().navigate(TestFragmentDirections.actionTestFragmentToHomeFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        time = System.currentTimeMillis()

        buttons.add(binding.option1)
        buttons.add(binding.option2)
        buttons.add(binding.option3)
        buttons.add(binding.option4)


        viewModel.getQuestions(args.category)

        binding.title.text = args.category

        binding.radioGroup.children.forEach {
            it.setOnClickListener {
                if (testList.isNotEmpty()) {
                    setAsDefault(it as RadioButton)
                } else {
                    showSnackBar("Testlar mavjud emas!")
                }

            }

        }

    }

    private val testListObserver = Observer<List<TestData>> {
        testList = it
        if (it.isNotEmpty()) {
            setButtons()
        } else {
            showSnackBar("Testlar mavjud emas!")
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    private fun setButtons() {
        //   3    <     4
        Log.d("TAGD", "setButtons")

        if (counter < testList.size) {
            GlobalScope.launch(Dispatchers.Main) {

                buttons = buttons.shuffled() as ArrayList<RadioButton>
                buttons[0].setBackgroundResource(R.drawable.correct_background)

                buttons[0].text = testList[counter].option1
                buttons[1].text = testList[counter].option2
                buttons[2].text = testList[counter].option3
                buttons[3].text = testList[counter].option4

                binding.apply {
                    progressBar.incrementProgressBy(100 / testList.size)
                    questionTv.text = testList[counter].question
                    countTestsTv.text = "/${testList.count()}"
                    currentStateTv.text = (counter + 1).toString()
                }

                radioClickable(true)
                counter++
            }

        } else if (counter == testList.size) {
            counter++

            val result = "$trueAnswers/${testList.size}"
            radioClickable(false)
            binding.radioGroup.clearCheck()

            showDialog(result)
        } else radioClickable(false)
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun setAsDefault(view: RadioButton) {
        if (counter <= testList.size) {
            GlobalScope.launch(Dispatchers.Main) {
                Log.d("TAGD", "setAsDefault: $counter")
                radioClickable(false)
                if (view.text == testList[counter - 1].answer) trueAnswers++
                delay(500)
                binding.radioGroup.clearCheck()
                buttons.forEach {
                    Log.d("TAGD", it.text.toString())
                    it.setBackgroundResource(R.drawable.incorrect_background)
                }
                setButtons()
                radioClickable(true)
            }
        } else radioClickable(false)

    }


    private fun radioClickable(isClickable: Boolean) {
        buttons.forEach {
            it.isClickable = isClickable
            it.isEnabled = isClickable
        }

    }


    @SuppressLint("SetTextI18n")
    private fun showDialog(result: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_end)


        val resultTime = (System.currentTimeMillis() - time) / 1000.0 / 60.0

        dialog.findViewById<TextView>(R.id.result_time_tv).text = resultTime.toString() + " minut"
        dialog.findViewById<TextView>(R.id.result_count_tv).text = result
        dialog.findViewById<Button>(R.id.menu_btn).setOnClickListener {
            findNavController().navigate(TestFragmentDirections.actionTestFragmentToHomeFragment())
            dialog.dismiss()
        }

        viewModel.finishTest(
            HistoryData(
                "Ravshan", args.category, result, duration = resultTime
            )
        )
        dialog.show()
    }
}