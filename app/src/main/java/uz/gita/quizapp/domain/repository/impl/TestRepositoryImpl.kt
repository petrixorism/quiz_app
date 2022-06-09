package uz.gita.quizapp.domain.repository.impl

import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import uz.gita.quizapp.data.model.CategoryData
import uz.gita.quizapp.data.model.MainResponse
import uz.gita.quizapp.data.model.TestData
import uz.gita.quizapp.domain.repository.TestRepository
import javax.inject.Inject


class TestRepositoryImpl @Inject constructor(
    private val fireStore: DocumentReference
) : TestRepository {


    override fun getCategoryNames() = callbackFlow<MainResponse<List<CategoryData>>> {
        fireStore.collection("categories").addSnapshotListener { snapshot, error ->
            val list = ArrayList<CategoryData>()
            snapshot?.documents?.forEach {
                it.toObject(CategoryData::class.java)?.let { it1 ->
                    list.add(it1)
                }
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


    override fun getQuestionsByCategory(category: String) =
        callbackFlow<MainResponse<List<TestData>>> {

            fireStore.collection("tests").document("categories").collection(category)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySendBlocking(MainResponse.Fail(error.message.toString()))
                    } else {
                        val testList = ArrayList<TestData>()
                        snapshot!!.documents.forEach {
                            it.toObject(TestData::class.java)?.let { it1 ->
                                testList.add(it1)
                            }
                        }
                        trySendBlocking(MainResponse.Success(testList))
                    }
                }
            awaitClose { }
        }.catch {
            emit(MainResponse.Error(it))
        }.flowOn(Dispatchers.IO)


    override fun editTest(testData: TestData) = callbackFlow<MainResponse<Unit>> {

        val ref = fireStore
            .collection("tests")
            .document("categories")
            .collection(testData.category)
            .document(testData.id)

        ref.set(testData).addOnSuccessListener {
            trySendBlocking(MainResponse.Success(Unit))
        }.addOnFailureListener {
            trySendBlocking(MainResponse.Fail(it.message.toString()))
        }

        awaitClose { }
    }.flowOn(Dispatchers.IO).catch {
        emit(MainResponse.Error(it))
    }


    override fun deleteTest(testData: TestData) = callbackFlow<MainResponse<Unit>> {

        val ref = fireStore
            .collection("tests")
            .document("categories")
            .collection(testData.category)
            .document(testData.id)

        ref.delete().addOnSuccessListener {
            trySendBlocking(MainResponse.Success(Unit))
        }.addOnFailureListener {
            trySendBlocking(MainResponse.Fail(it.message.toString()))
        }

        awaitClose { }
    }.flowOn(Dispatchers.IO).catch {
        emit(MainResponse.Error(it))
    }

    override fun deleteCategory(category: String) = callbackFlow<MainResponse<Unit>> {

        fireStore.collection("categories").document(category)
            .delete()
            .addOnSuccessListener {
                trySendBlocking(MainResponse.Success(Unit))
            }.addOnFailureListener {
                trySendBlocking(MainResponse.Fail(it.message!!))
            }
        awaitClose { }
    }.flowOn(Dispatchers.IO).catch {
        emit(MainResponse.Error(it))
    }


    override fun insertTest(testData: TestData) = callbackFlow<MainResponse<Unit>> {
        val categoryData = CategoryData(testData.category)

        val ref = fireStore
            .collection("tests")
            .document("categories")
            .collection(testData.category)
            .document()

        testData.id = ref.id

        val reference = fireStore.collection("categories").document(categoryData.categoryName)


        ref.set(testData).addOnSuccessListener {
            trySendBlocking(MainResponse.Success(Unit))
        }.addOnFailureListener {
            trySendBlocking(MainResponse.Fail(it.message.toString()))
        }

        reference.set(categoryData).addOnSuccessListener {
        }.addOnFailureListener {
            trySendBlocking(MainResponse.Fail(it.message.toString()))
        }



        awaitClose {}
    }.flowOn(Dispatchers.IO).catch { it ->
        emit(MainResponse.Error(it))
    }


}