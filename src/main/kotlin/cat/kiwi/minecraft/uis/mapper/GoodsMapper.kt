package cat.kiwi.minecraft.uis.mapper

import cat.kiwi.minecraft.uis.config.ConfigLoader
import cat.kiwi.minecraft.uis.model.entity.Good
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select

interface GoodsMapper {
    @Insert("INSERT INTO \${tableName} (id, putter_name, putter_uid, caller_name, caller_uid, price, item_info, item_tag, description, been_sold, create_date, deal_date, meta) VALUES (#{good.id}, #{good.putterName}, #{good.putterUid}, #{good.callerName}, #{good.callerUid}, #{good.price}, #{good.itemInfo}, #{good.itemTag}, #{good.description}, #{good.beenSold}, #{good.createDate}, #{good.dealDate}, #{good.meta})")
    fun sellGoods(good: Good, tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} ORDER BY create_date")
    fun getAllGoods(beenSold: Boolean, tableName: String = "${ConfigLoader.tablePrefix}goods"): List<Good>

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} AND putter_uid=#{playerUid} ORDER BY create_date")
    fun getGoodsByPlayer(
        playerUid: String,
        beenSold: Boolean,
        tableName: String = "${ConfigLoader.tablePrefix}goods"
    ): List<Good>

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} AND item_info=#{itemInfo} ORDER BY create_date")
    fun getGoodsByType(type: String, beenSold: Boolean, tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Select("UPDATE \${tableName} SET caller_name=#{playerName}, caller_uid=#{playerUid}, been_sold=true, deal_date=#{dealDate} WHERE id=#{goodUid}")
    fun buyGoods(name: String, toString: String, goodUid: String, tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Select("DELETE FROM \${tableName} WHERE id=#{goodUid}")
    fun deleteGoods(goodUid: String, tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Select("SELECT * FROM \${tableName} WHERE id=#{goodUid}")
    fun queryGoods(goodUid: String, tableName: String = "${ConfigLoader.tablePrefix}goods"): Good
}