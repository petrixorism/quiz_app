package uz.gita.quizapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.quizapp.data.model.TestData

interface AddTestViewModel {

    val insertLiveData: Flow<Unit>
    val failLiveData: LiveData<String>
    val errorLiveData: LiveData<String>

    fun insertTest(testData: TestData)

}