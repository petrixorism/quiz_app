package uz.gita.quizapp.presentation.viewmodel.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.quizapp.data.model.MainResponse
import uz.gita.quizapp.data.model.TestData
import uz.gita.quizapp.domain.repository.HistoryRepository
import uz.gita.quizapp.domain.repository.TestRepository
import uz.gita.quizapp.presentation.viewmodel.AddTestViewModel
import uz.gita.quizapp.util.isEmptyOrBlank
import javax.inject.Inject

@HiltViewModel
class AddTestViewModelImpl @Inject constructor(private val repository: TestRepository) :
    ViewModel(), AddTestViewModel {

    private val TAG = "QWERTY"

    override val failLiveData = MutableLiveData<String>()
    override val errorLiveData = MutableLiveData<String>()

    private val addChannel = Channel<Unit>()
    override val insertLiveData = addChannel.receiveAsFlow()



    override fun insertTest(testData: TestData) {


        if (testData.category.isEmptyOrBlank()) {
            failLiveData.value = "Kategoriya bo'sh"
        } else if (testData.question.isEmptyOrBlank()) {
            failLiveData.value = "Savol bo'sh"

        } else if (testData.option1.isEmptyOrBlank()) {
            failLiveData.value = "1-variant bo'sh"

        } else if (testData.option2.isEmptyOrBlank()) {
            failLiveData.value = "2-variant bo'sh"

        } else if (testData.option3.isEmptyOrBlank()) {
            failLiveData.value = "3-variant bo'sh"

        } else if (testData.option4.isEmptyOrBlank()) {
            failLiveData.value = "4-variant bo'sh"
        } else {

            viewModelScope.launch {

                insertLiveData

                repository.insertTest(testData).collect() {
                    when (it) {
                        is MainResponse.Success -> {
                            Log.d(TAG, "insertTest: success")
                            addChannel.send(Unit)
                        }
                        is MainResponse.Fail -> {
                            Log.d(TAG, "insertTest: ${it.message}")

                            failLiveData.postValue(it.message)
                        }
                        is MainResponse.Error -> {
                            Log.d(TAG, "insertTest: ${it.error.message}")

                            errorLiveData.postValue(it.error.message)
                        }
                    }
                }
            }

        }

    }
}