package cat.kiwi.minecraft.uis.service

import java.util.UUID

interface PlayerService {
    fun getPlayerUUIDByName(name: String): UUID?
    fun getPlayerNameByUUID(uuid: UUID): String?
    fun updatePlayerName(uuid: UUID, name: String)
    fun insertPlayerName(uuid: UUID, name: String)
}