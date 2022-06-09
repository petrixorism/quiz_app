package uz.gita.quizapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.quizapp.data.model.CategoryData
import uz.gita.quizapp.data.model.MainResponse
import uz.gita.quizapp.data.model.TestData

interface TestRepository {

    fun getCategoryNames(): Flow<MainResponse<List<CategoryData>>>

    fun getQuestionsByCategory(category: String): Flow<MainResponse<List<TestData>>>

    fun editTest(testData: TestData): Flow<MainResponse<Unit>>

    fun deleteTest(testData: TestData): Flow<MainResponse<Unit>>

    fun deleteCategory(category: String): Flow<MainResponse<Unit>>

    fun insertTest(testData: TestData): Flow<MainResponse<Unit>>

}