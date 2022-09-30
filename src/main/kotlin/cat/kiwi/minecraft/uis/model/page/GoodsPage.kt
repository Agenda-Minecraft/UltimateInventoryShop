package cat.kiwi.minecraft.uis.model.page

import cat.kiwi.minecraft.uis.model.entity.Goods

data class GoodsPage(val index: Int, val maxPage: Int, val goods: List<Goods>)
