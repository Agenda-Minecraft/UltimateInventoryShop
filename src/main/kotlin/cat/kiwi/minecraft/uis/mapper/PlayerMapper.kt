package cat.kiwi.minecraft.uis.mapper

import cat.kiwi.minecraft.uis.config.Config
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import java.util.Date

interface PlayerMapper {
    @Select("SELECT uid FROM \${tableName} WHERE name = #{name}")
    fun getPlayerUUIDByName(name: String,tableName: String = "${Config.tablePrefix}players"): String?
    @Select("SELECT name FROM \${tableName} WHERE uid = #{uuid} ORDER BY record_date DESC LIMIT 1")
    fun getPlayerNameByUUID(uuid: String, tableName: String = "${Config.tablePrefix}players"): String?
    @Update("UPDATE \${tableName} (uid, name, record_date) VALUES (#{uuid}, #{name}, #{date})")
    fun updatePlayerName(uuid: String, name: String, date: Date ,tableName: String = "${Config.tablePrefix}players")
    @Insert("INSERT INTO \${tableName} (uid, name, record_date) VALUES (#{uuid}, #{name}, #{date})")
    fun insertPlayerName(uuid: String, name: String, date: Date,tableName: String = "${Config.tablePrefix}players")
}