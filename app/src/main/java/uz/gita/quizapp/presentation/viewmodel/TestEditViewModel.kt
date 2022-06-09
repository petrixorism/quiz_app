package uz.gita.quizapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.data.model.TestData

interface TestEditViewModel {

    val testListLiveData: LiveData<List<TestData>>
    val failLiveData: LiveData<String>
    val errorLiveData: LiveData<String>
    val backLiveData: LiveData<Unit>
    val editTestLiveData: LiveData<Unit>
    val deleteLiveData: LiveData<Unit>
    val deleteCategoryLiveData: LiveData<Unit>

    fun getTestsByCategory(category: String)

    fun editTest(testData: TestData)

    fun deleteTest(testData: TestData)

    fun deleteCategory(category: String)

    fun back()


}