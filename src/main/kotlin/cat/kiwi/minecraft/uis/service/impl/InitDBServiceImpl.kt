package cat.kiwi.minecraft.uis.service.impl

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.ConfigLoader
import cat.kiwi.minecraft.uis.mapper.InitDBMapper
import cat.kiwi.minecraft.uis.service.InitDBService

class InitDBServiceImpl: InitDBService {
    private val initDBMapper: InitDBMapper = UltimateInventoryShopPlugin.sqlSession.getMapper(InitDBMapper::class.java)

    override fun createTableIfNotExist() {
        if (initDBMapper.isTableExist("${ConfigLoader.tablePrefix}goods") != 1 ) {
            initDBMapper.createTable()
            initDBMapper.createTableIndex()
        }
        if ( initDBMapper.isTableExist("${ConfigLoader.tablePrefix}players") != 1){
            initDBMapper.createPlayerTable()
            initDBMapper.createPlayerTableIndex()
        }
    }
}