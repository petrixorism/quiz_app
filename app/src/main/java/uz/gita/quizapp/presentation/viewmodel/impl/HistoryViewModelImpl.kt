package uz.gita.quizapp.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.data.model.MainResponse
import uz.gita.quizapp.domain.repository.HistoryRepository
import uz.gita.quizapp.presentation.viewmodel.HistoryViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModelImpl @Inject constructor(repository: HistoryRepository) : ViewModel(),
    HistoryViewModel {

    override val historyListLiveData = MutableLiveData<List<HistoryData>>()

    override val failLiveData = MutableLiveData<String>()

    override val errorLiveData = MutableLiveData<String>()


    init {
        viewModelScope.launch {
            repository.getAllHistory().collect() {
                when (it) {
                    is MainResponse.Success -> {
                        historyListLiveData.postValue(it.data!!)
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