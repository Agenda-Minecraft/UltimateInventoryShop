package cat.kiwi.minecraft.uis.mapper

import cat.kiwi.minecraft.uis.config.ConfigLoader
import cat.kiwi.minecraft.uis.model.entity.GoodPojo
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select

interface GoodsMapper {
    @Insert("INSERT INTO \${tableName} (id, putter_name, putter_uid, caller_name, caller_uid, price, item_info, item_tag, description, been_sold, create_date, deal_date, meta) VALUES (#{goodPojo.id}, #{goodPojo.putterName}, #{goodPojo.putterUid}, #{goodPojo.callerName}, #{goodPojo.callerUid}, #{goodPojo.price}, #{goodPojo.itemInfo}, #{goodPojo.itemTag}, #{goodPojo.description}, #{goodPojo.beenSold}, #{goodPojo.createDate}, #{goodPojo.dealDate}, #{goodPojo.meta})")
    fun sellGoods(goodPojo: GoodPojo, tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} ORDER BY create_date")
    fun getAllGoods(beenSold: Boolean=false, tableName: String = "${ConfigLoader.tablePrefix}goods"): List<GoodPojo>

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} AND putter_uid=#{playerUid} ORDER BY create_date")
    fun getGoodsByPlayer(
        playerUid: String,
        beenSold: Boolean,
        tableName: String = "${ConfigLoader.tablePrefix}goods"
    ): List<GoodPojo>

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} AND item_info=#{itemInfo} ORDER BY create_date")
    fun getGoodsByType(type: String, beenSold: Boolean, tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Select("UPDATE \${tableName} SET caller_name=#{playerName}, caller_uid=#{playerUid}, been_sold=true, deal_date=#{dealDate} WHERE id=#{goodUid}")
    fun buyGoods(name: String, toString: String, goodUid: String, tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Delete("DELETE FROM \${tableName} WHERE id=#{goodUid}")
    fun deleteGoods(goodUid: String, tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Select("SELECT * FROM \${tableName} WHERE id=#{goodUid}")
    fun queryGoods(goodUid: String, tableName: String = "${ConfigLoader.tablePrefix}goods"): GoodPojo
}