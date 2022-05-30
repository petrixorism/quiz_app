package uz.gita.quizapp.presentation.viewmodel.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.gita.quizapp.data.SharedPref
import uz.gita.quizapp.data.model.CategoryData
import uz.gita.quizapp.data.model.MainResponse
import uz.gita.quizapp.domain.repository.TestRepository
import uz.gita.quizapp.presentation.viewmodel.HomeViewModel
import uz.gita.quizapp.util.isEmptyOrBlank
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(private val repository: TestRepository) : ViewModel(),
    HomeViewModel {

    override val categoriesLiveData = MutableLiveData<List<CategoryData>>()
    override val failLiveData = MutableLiveData<String>()
    override val introLivedata = MutableLiveData<String>()
    override val errorLiveData = MutableLiveData<String>()

    override fun setUserName(name: String) {
        if (name.isEmptyOrBlank()) {
            errorLiveData.postValue("Ism kiritilmadi")
        } else {
            SharedPref.getInstance().name = name
            introLivedata.postValue(SharedPref.getInstance().name)
        }
    }

    init {

        introLivedata.value = SharedPref.getInstance().name

        viewModelScope.launch {
            repository.getCategoryNames().collect() {
                when (it) {
                    is MainResponse.Success -> {
                        categoriesLiveData.postValue(it.data!!)
                    }
                    is MainResponse.Fail -> {

                        failLiveData.postValue(it.message)
                    }
                    is MainResponse.Error -> {

                        errorLiveData.postValue(it.error.message)
                    }
                }
            }

        }
    }
}