package uz.gita.quizapp.data.model

data class HistoryData(
    var name: String = "",
    var category: String = "",
    var result: String = "0/0",
    var id: String = "",
    var duration: Double = 0.0,
    var time: Long = System.currentTimeMillis(),
)
