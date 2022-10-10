package cat.kiwi.minecraft.uis.config

import cat.kiwi.minecraft.uis.model.enum.UisSqlType

object TableConfig {
    var sqlType = UisSqlType.MYSQL
    var beenSold = "tinyint(1)"
    var dateType = "datetime"
    var uidType = "varchar(36)"
    var priceType = "decimal(10, 2)"
    var nameType = "varchar(40)"
}