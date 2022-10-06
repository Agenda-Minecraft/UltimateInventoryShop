package cat.kiwi.minecraft.uis.mapper

import cat.kiwi.minecraft.uis.config.ConfigLoader
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface InitDBMapper {
    fun getTablePrefix(): String {
        return "prefix_"
    }

    @Select("SELECT EXISTS(SELECT * FROM information_schema.tables WHERE table_name = #{tableName})")
    fun isTableExist(tableName:String = "${ConfigLoader.tablePrefix}goods"): Int

    @Select("CREATE TABLE IF NOT EXISTS \${tableName}  (\n" +
            "  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `putter_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `putter_uid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `caller_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `caller_uid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `price` decimal(10, 2) NOT NULL,\n" +
            "  `item_info` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,\n" +
            "  `item_tag` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `been_sold` tinyint(1) NOT NULL,\n" +
            "  `create_date` datetime NOT NULL,\n" +
            "  `deal_date` datetime NOT NULL,"+
            "  `meta` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;\n")
    fun createTable(tableName: String = "${ConfigLoader.tablePrefix}goods")

    @Select("CREATE INDEX goods_index ON \${tableName} (id, putter_name, putter_uid, caller_name, caller_uid, item_tag, description, been_sold, create_date, deal_date)")
    fun createTableIndex(tableName: String = "${ConfigLoader.tablePrefix}goods")

}