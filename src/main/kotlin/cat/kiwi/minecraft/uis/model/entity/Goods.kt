package cat.kiwi.minecraft.uis.model.entity

import java.util.*

data class Goods(
    val id: String,
    val putterName: String,
    val putterUid: String,
    val callerName: String,
    val callerUid: String,
    val price: Double,
    val itemInfo: String,
    val itemTag: String,
    val displayName: String,
    val beenSold: Boolean,
    val createDate: Date,
    val dealDate: Date,
    val meta: String?
)
