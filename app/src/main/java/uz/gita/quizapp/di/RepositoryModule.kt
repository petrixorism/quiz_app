package uz.gita.quizapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.quizapp.domain.repository.HistoryRepository
import uz.gita.quizapp.domain.repository.TestRepository
import uz.gita.quizapp.domain.repository.impl.HistoryRepositoryImpl
import uz.gita.quizapp.domain.repository.impl.TestRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindTestRepository(impl: TestRepositoryImpl): TestRepository

    @Binds
    @Singleton
    fun bindHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository

}