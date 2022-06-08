package uz.gita.quizapp.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.quizapp.data.model.MainResponse
import uz.gita.quizapp.data.model.TestData
import uz.gita.quizapp.domain.repository.TestRepository
import uz.gita.quizapp.presentation.viewmodel.TestEditViewModel
import javax.inject.Inject


@HiltViewModel
class TestEditViewModelImpl @Inject constructor(
    private val repository: TestRepository
) : ViewModel(), TestEditViewModel {

    override val testListLiveData = MutableLiveData<List<TestData>>()
    override val failLiveData = MutableLiveData<String>()
    override val errorLiveData = MutableLiveData<String>()
    override val backLiveData = MutableLiveData<Unit>()
    override val editTestLiveData = MutableLiveData<Unit>()
    override val deleteLiveData = MutableLiveData<Unit>()

    override fun getTestsByCategory(category: String) {
        viewModelScope.launch {
            repository.getQuestionsByCategory(category).collect {
                when (it) {
                    is MainResponse.Success -> {
                        testListLiveData.postValue(it.data!!)
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

    override fun editTest(testData: TestData) {
        viewModelScope.launch {
            repository.editTest(testData).collect {
                when (it) {
                    is MainResponse.Success -> {
                        editTestLiveData.postValue(Unit)
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

    override fun deleteTest(testData: TestData) {

        viewModelScope.launch {
            repository.deleteTest(testData).collect {
                when (it) {
                    is MainResponse.Success -> {
                        deleteLiveData.postValue(Unit)
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

    override fun back() {
        backLiveData.value = Unit
    }


}