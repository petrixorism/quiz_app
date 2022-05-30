package uz.gita.quizapp.data

import android.content.Context

class SharedPref(context: Context) {

    companion object {

        private var instance: SharedPref? = null

        fun init(context: Context) {
            instance = SharedPref(context)
        }

        fun getInstance(): SharedPref = instance!!

    }

    private val pref = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)


    var name: String
        set(value) = pref.edit().putString("USERNAME", value).apply()
        get() = pref.getString("USERNAME", "")!!

}