package cat.kiwi.minecraft.uis.mapper

import cat.kiwi.minecraft.uis.config.Config
import cat.kiwi.minecraft.uis.model.pojo.GoodPojo
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import java.util.*

interface GoodsMapper {
    @Insert("INSERT INTO \${tableName} (id, putter_name, putter_uid, caller_name, caller_uid, price, item_info, item_tag, description, been_sold, create_date, deal_date, meta) VALUES (#{goodPojo.id}, #{goodPojo.putterName}, #{goodPojo.putterUid}, #{goodPojo.callerName}, #{goodPojo.callerUid}, #{goodPojo.price}, #{goodPojo.itemInfo}, #{goodPojo.itemTag}, #{goodPojo.description}, #{goodPojo.beenSold}, #{goodPojo.createDate}, #{goodPojo.dealDate}, #{goodPojo.meta})")
    fun sellGoods(goodPojo: GoodPojo, tableName: String = "${Config.tablePrefix}goods")

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} ORDER BY create_date DESC, price")
    fun getAllGoods(beenSold: Boolean=false, tableName: String = "${Config.tablePrefix}goods"): List<GoodPojo>

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} AND putter_uid=#{playerUid} ORDER BY create_date DESC, price")
    fun getGoodsByPlayer(
        playerUid: String,
        beenSold: Boolean,
        tableName: String = "${Config.tablePrefix}goods"
    ): List<GoodPojo>

    @Select("SELECT * FROM \${tableName} WHERE been_sold=#{beenSold} AND item_info=#{itemInfo} ORDER BY create_date DESC, price")
    fun getGoodsByType(type: String, beenSold: Boolean, tableName: String = "${Config.tablePrefix}goods"): List<GoodPojo>

    @Update("UPDATE \${tableName} SET caller_name=#{playerName}, caller_uid=#{playerUid}, been_sold=1, deal_date=#{dealDate} WHERE id=#{goodUid}")
    fun buyGoods(playerName: String, playerUid: String, goodUid: String, dealDate: Date = Date(), tableName: String = "${Config.tablePrefix}goods")

    @Delete("DELETE FROM \${tableName} WHERE id=#{goodUid}")
    fun deleteGoods(goodUid: String, tableName: String = "${Config.tablePrefix}goods")

    @Select("SELECT * FROM \${tableName} WHERE id=#{goodUid}")
    fun queryGoods(goodUid: String, tableName: String = "${Config.tablePrefix}goods"): GoodPojo?

    @Select("SELECT COUNT(1) FROM \${tableName} WHERE been_sold=#{beenSold}")
    fun getCount(tableName: String="${Config.tablePrefix}goods", beenSold: Boolean=false): Int
    @Select("SELECT COUNT(1) FROM \${tableName} WHERE been_sold=#{beenSold} AND putter_name=#{putterName}")
    fun getCountPlayer(tableName: String="${Config.tablePrefix}goods", putterName: String, beenSold: Boolean=false): Int
}