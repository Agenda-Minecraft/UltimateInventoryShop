package cat.kiwi.minecraft.uis.mapper

import cat.kiwi.minecraft.uis.config.Config
import cat.kiwi.minecraft.uis.config.TableConfig
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface InitDBMapper {

    @Select("SELECT COUNT(*) FROM information_schema.tables WHERE table_name = #{tableName}")
    fun isTableExist(tableName: String = "${Config.tablePrefix}goods"): Int

    @Select(
        "CREATE TABLE IF NOT EXISTS \${tableName}  (\n" + "  id \${uidType} NOT NULL PRIMARY KEY,\n" + "  putter_name \${nameType}NOT NULL,\n" + "  putter_uid \${uidType} NOT NULL,\n" + "  caller_name \${nameType} NOT NULL,\n" + "  caller_uid \${uidType} NOT NULL,\n" + "  price \${priceType} NOT NULL,\n" + "  item_info text NOT NULL,\n" + "  item_tag \${nameType} NOT NULL,\n" + "  description text NOT NULL,\n" + "  been_sold \${beenSoldType} NOT NULL,\n" + "  create_date \${dateType} NOT NULL,\n" + "  deal_date \${dateType} NOT NULL," + "  meta text NOT NULL\n" + ") \n"
    )
    fun createTable(
        tableName: String = "${Config.tablePrefix}goods",
        beenSoldType: String = TableConfig.beenSold,
        dateType: String = TableConfig.dateType,
        uidType: String = TableConfig.uidType,
        priceType: String = TableConfig.priceType,
        nameType: String = TableConfig.nameType
    )

    @Select(
        "CREATE TABLE IF NOT EXISTS \${tableName} (\n" + "  uid \${uidType} NOT NULL PRIMARY KEY,\n" + "  name \${nameType} NOT NULL,\n" + "   record_date \${dateType} NOT NULL\n" + ")\n"
    )
    fun createPlayerTable(
        tableName: String = "${Config.tablePrefix}players",
        dateType: String = TableConfig.dateType,
        uidType: String = TableConfig.uidType,
        nameType: String = TableConfig.nameType
    )

    @Select("CREATE INDEX IF NOT EXISTS goods_index ON \${tableName} (id, putter_name, putter_uid, caller_name, caller_uid, item_tag, been_sold, create_date, deal_date)")
    fun createTableIndex(tableName: String = "${Config.tablePrefix}goods")

    @Select("CREATE INDEX IF NOT EXISTS player_index ON \${tableName} (name, uid)")
    fun createPlayerTableIndex(tableName: String = "${Config.tablePrefix}players")

}