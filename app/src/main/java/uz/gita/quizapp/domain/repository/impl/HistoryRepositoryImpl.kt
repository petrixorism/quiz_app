package uz.gita.quizapp.domain.repository.impl

import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import uz.gita.quizapp.data.model.HistoryData
import uz.gita.quizapp.data.model.MainResponse
import uz.gita.quizapp.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val fireStore: DocumentReference
) : HistoryRepository {
    override fun getAllHistory() =
        callbackFlow<MainResponse<List<HistoryData>>> {
            val ref = fireStore.collection("history")

            ref.addSnapshotListener { snapshot, error ->
                val list = ArrayList<HistoryData>()
                snapshot?.documents?.forEach {
                    it.toObject(HistoryData::class.java)?.let { it1 -> list.add(it1) }
                }
                if (error != null) {
                    trySendBlocking(MainResponse.Fail(error.message.toString()))
                } else {
                    trySendBlocking(MainResponse.Success(list))
                }
            }


            awaitClose { }
        }.catch {
            emit(MainResponse.Error(it))
        }.flowOn(Dispatchers.IO)

    override fun insertHistory(history: HistoryData) =
        callbackFlow<MainResponse<Unit>> {

            val ref = fireStore.collection("history").document()
            history.id = ref.id
            ref.set(history)

            awaitClose { }
        }.catch {
            emit(MainResponse.Error(it))
        }.flowOn(Dispatchers.IO)

}