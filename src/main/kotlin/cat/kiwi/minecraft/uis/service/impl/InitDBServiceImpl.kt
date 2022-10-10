package cat.kiwi.minecraft.uis.service.impl

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Config
import cat.kiwi.minecraft.uis.mapper.InitDBMapper
import cat.kiwi.minecraft.uis.service.InitDBService

class InitDBServiceImpl: InitDBService {
    private val initDBMapper: InitDBMapper = UltimateInventoryShopPlugin.sqlSession.getMapper(InitDBMapper::class.java)

    override fun createTableIfNotExist() {
            initDBMapper.createTable()
            initDBMapper.createTableIndex()
            initDBMapper.createPlayerTable()
            initDBMapper.createPlayerTableIndex()
    }
}