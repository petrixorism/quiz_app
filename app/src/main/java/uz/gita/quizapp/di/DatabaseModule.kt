package uz.gita.quizapp.di

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @[Singleton Provides]
    fun provideFirebaseDatabase(): DocumentReference =
        FirebaseFirestore.getInstance().collection("main").document("Djumayev")


}