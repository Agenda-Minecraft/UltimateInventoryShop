package cat.kiwi.minecraft.uis.service.impl

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.mapper.PlayerMapper
import cat.kiwi.minecraft.uis.service.PlayerService
import java.util.UUID

class PlayerServiceImpl: PlayerService {
    private val goodsMapper: PlayerMapper = UltimateInventoryShopPlugin.sqlSession.getMapper(PlayerMapper::class.java)
    override fun getPlayerUUIDByName(name: String): UUID? {
        val uidString = goodsMapper.getPlayerUUIDByName(name)
        return if (uidString == null) {
            null
        } else {
            UUID.fromString(uidString)
        }
    }

    override fun getPlayerNameByUUID(uuid: UUID): String? {
        return goodsMapper.getPlayerNameByUUID(uuid.toString())
    }

    override fun updatePlayerName(uuid: UUID, name: String) {
        goodsMapper.updatePlayerName(uuid.toString(), name)
    }

    override fun insertPlayerName(uuid: UUID, name: String) {
        goodsMapper.insertPlayerName(uuid.toString(), name)
    }
}