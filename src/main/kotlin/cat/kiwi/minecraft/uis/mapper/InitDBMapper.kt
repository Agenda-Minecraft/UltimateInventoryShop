package cat.kiwi.minecraft.uis.mapper

import cat.kiwi.minecraft.uis.UltimateInventoryShopPlugin
import cat.kiwi.minecraft.uis.config.Config
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface InitDBMapper {
    fun getTablePrefix(): String {
        return "prefix_"
    }

    @Select("SELECT EXISTS(SELECT * FROM information_schema.tables WHERE table_name = #{tableName})")
    fun isTableExist(tableName:String = "${Config.tablePrefix}goods"): Int

    @Select("CREATE TABLE IF NOT EXISTS \${tableName}  (\n" +
            "  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `puttername` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `putteruid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `callername` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `calleruid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `price` decimal(10, 2) NOT NULL,\n" +
            "  `itemInfo` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,\n" +
            "  `itemTag` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `displayName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,\n" +
            "  `beensold` tinyint(1) NOT NULL,\n" +
            "  `createdate` datetime NOT NULL,\n" +
            "  `dealdate` datetime NOT NULL,"+
            "  `meta` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL\n" +
            ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;\n")
    fun createTable(tableName: String = "${Config.tablePrefix}goods")

    @Select("CREATE INDEX goods_index ON \${tableName} (id, puttername, putteruid, callername, calleruid, itemTag, displayName)")
    fun createTableIndex(tableName: String = "${Config.tablePrefix}goods")

}