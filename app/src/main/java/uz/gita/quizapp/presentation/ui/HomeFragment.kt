package uz.gita.quizapp.presentation.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.quizapp.R
import uz.gita.quizapp.data.SharedPref
import uz.gita.quizapp.data.model.CategoryData
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.data.model.TestData
import uz.gita.quizapp.databinding.FragmentHistoryBinding
import uz.gita.quizapp.databinding.FragmentHomeBinding
import uz.gita.quizapp.presentation.adapter.CategoryAdapter
import uz.gita.quizapp.presentation.viewmodel.HomeViewModel
import uz.gita.quizapp.presentation.viewmodel.impl.HomeViewModelImpl
import uz.gita.quizapp.util.isEmptyOrBlank
import uz.gita.quizapp.util.showSnackBar
import uz.gita.quizapp.util.showToast

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {


    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val adapter by lazy { CategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.categoriesLiveData.observe(this) {
            adapter.submitList(it)
        }
        viewModel.failLiveData.observe(this) {
            showToast(it)
        }
        viewModel.errorLiveData.observe(this) {
            showSnackBar(it)
        }
        viewModel.introLivedata.observe(this) {
            if (it.isEmptyOrBlank()) showDialog()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.categoriesRv.adapter = adapter

        binding.textView2.text = "\uD83D\uDC4B\uD83C\uDFFB  Salom ${SharedPref.getInstance().name}"

        adapter.setItemClick {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTestFragment(it.categoryName))
        }
        adapter.setItemLongClick { data, view ->
            showPopUp(view, data)
        }

    }


    // ---------- Dialog ----------//
    @SuppressLint("SetTextI18n")
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(R.drawable.background_et_brown_dialog);
        dialog.setContentView(R.layout.dialog_username)


        val nameEt = dialog.findViewById<EditText>(R.id.name_et)
        dialog.findViewById<Button>(R.id.confirm_btn).setOnClickListener {
            if (nameEt.text.toString().isEmptyOrBlank()) {
                showToast("Ism kiritilmadi")
            } else {
                viewModel.setUserName(nameEt.text.toString())
                binding.textView2.text =
                    "\uD83D\uDC4B\uD83C\uDFFB  Salom ${SharedPref.getInstance().name}"
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    // ---------- PopUp ----------//
    private fun showPopUp(view: View, data: CategoryData) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.item_menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToEditFragment(
                            data.categoryName
                        )
                    )
                    return@setOnMenuItemClickListener true
                }
            }

            return@setOnMenuItemClickListener false
        }
    }

}