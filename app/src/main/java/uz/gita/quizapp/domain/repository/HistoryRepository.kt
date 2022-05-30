package uz.gita.quizapp.domain.repository

import com.google.api.ResourceDescriptor
import kotlinx.coroutines.flow.Flow
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.data.model.MainResponse

interface HistoryRepository {

    fun getAllHistory() :Flow<MainResponse<List<HistoryData>>>

    fun insertHistory(history: HistoryData) :Flow<MainResponse<Unit>>

}