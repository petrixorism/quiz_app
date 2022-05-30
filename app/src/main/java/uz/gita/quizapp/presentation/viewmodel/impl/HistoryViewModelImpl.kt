package uz.gita.quizapp.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.presentation.viewmodel.HistoryViewModel

class HistoryViewModelImpl : ViewModel(), HistoryViewModel {

    override val historyListLiveData: LiveData<List<HistoryData>>
        get() = TODO("Not yet implemented")
    override val failLiveData: LiveData<String>
        get() = TODO("Not yet implemented")
    override val errorLiveData: LiveData<String>
        get() = TODO("Not yet implemented")


}