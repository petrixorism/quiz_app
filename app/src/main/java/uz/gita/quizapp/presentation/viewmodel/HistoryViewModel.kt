package uz.gita.quizapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.quizapp.data.model.CategoryData
import uz.gita.quizapp.data.model.HistoryData

interface HistoryViewModel {


    val historyListLiveData: LiveData<List<HistoryData>>
    val failLiveData: LiveData<String>
    val errorLiveData: LiveData<String>

}