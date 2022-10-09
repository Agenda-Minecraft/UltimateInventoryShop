package cat.kiwi.minecraft.uis.service.impl

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Config
import cat.kiwi.minecraft.uis.mapper.InitDBMapper
import cat.kiwi.minecraft.uis.service.InitDBService

class InitDBServiceImpl: InitDBService {
    private val initDBMapper: InitDBMapper = UltimateInventoryShopPlugin.sqlSession.getMapper(InitDBMapper::class.java)

    override fun createTableIfNotExist() {
        if (initDBMapper.isTableExist("${Config.tablePrefix}goods") != 1 ) {
            initDBMapper.createTable()
            initDBMapper.createTableIndex()
        }
        if ( initDBMapper.isTableExist("${Config.tablePrefix}players") != 1){
            initDBMapper.createPlayerTable()
            initDBMapper.createPlayerTableIndex()
        }
    }
}