package cat.kiwi.minecraft.uis.service

interface InitDBService {
    fun createTableIfNotExist()
}