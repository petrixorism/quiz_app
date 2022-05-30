package uz.gita.quizapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.data.model.TestData

interface TestViewModel {

    val testListLiveData: LiveData<List<TestData>>
    val finishLiveData: LiveData<Unit>
    val failLiveData: LiveData<String>
    val errorLiveData: LiveData<String>

    fun finishTest(historyData: HistoryData)

    fun getQuestions(category: String)


}