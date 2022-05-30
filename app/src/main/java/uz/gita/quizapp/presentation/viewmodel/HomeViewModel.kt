package uz.gita.quizapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.quizapp.data.model.CategoryData

interface HomeViewModel {

    val categoriesLiveData: LiveData<List<CategoryData>>
    val failLiveData: LiveData<String>
    val introLivedata: LiveData<String>
    val errorLiveData: LiveData<String>

    fun setUserName(name: String)
}