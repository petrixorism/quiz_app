package uz.gita.quizapp.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.data.model.MainResponse
import uz.gita.quizapp.data.model.TestData
import uz.gita.quizapp.domain.repository.HistoryRepository
import uz.gita.quizapp.domain.repository.TestRepository
import uz.gita.quizapp.presentation.viewmodel.TestViewModel
import javax.inject.Inject


@HiltViewModel
class TestViewModelImpl @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val testRepository: TestRepository,
) : ViewModel(), TestViewModel {
    override val testListLiveData = MutableLiveData<List<TestData>>()
    override val finishLiveData = MutableLiveData<Unit>()
    override val failLiveData = MutableLiveData<String>()
    override val errorLiveData = MutableLiveData<String>()
    override val backLiveData = MutableLiveData<Unit>()


    override fun finishTest(historyData: HistoryData) {

        viewModelScope.launch {
            historyRepository.insertHistory(historyData).collect() {

                when (it) {
                    is MainResponse.Success -> {
                        finishLiveData.postValue(Unit)
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

    override fun getQuestions(category: String) {

        viewModelScope.launch {
            testRepository.getQuestionsByCategory(category).collect() {

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

    override fun back() {
        backLiveData.value = Unit
    }
}