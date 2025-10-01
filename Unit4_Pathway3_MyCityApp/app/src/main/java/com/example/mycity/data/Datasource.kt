package com.example.mycity.data

import com.example.mycity.R

object Datasource {

    private val restaurantsCategory = Category(
        name = R.string.restaurants_category,
        icon = R.drawable.restaurant_icon,
        list = listOf(
            Place(
                name = R.string.pho_gia_truyen_title,
                description = R.string.pho_gia_truyen_description,
                address = R.string.pho_gia_truyen_address,
                photo = R.drawable.pho_gia_truyen
            ),
            Place(
                name = R.string.bun_cha_dac_kim_title,
                description = R.string.bun_cha_dac_kim_description,
                address = R.string.bun_cha_dac_kim_address,
                photo = R.drawable.bun_cha_dac_kim
            ),
            Place(
                name = R.string.madame_hiên_title,
                description = R.string.madame_hiên_description,
                address = R.string.madame_hiên_address,
                photo = R.drawable.madame_hien
            ),
            Place(
                name = R.string.quan_an_ngon_title,
                description = R.string.quan_an_ngon_description,
                address = R.string.quan_an_ngon_address,
                photo = R.drawable.quan_an_ngon
            )
        )
    )

    private val barsCategory = Category(
        name = R.string.bars_category,
        icon = R.drawable.bar_icon,
        list = listOf(
            Place(
                name = R.string.bia_hoi_corner_title,
                description = R.string.bia_hoi_corner_description,
                address = R.string.bia_hoi_corner_address,
                photo = R.drawable.bia_hoi_corner
            ),
            Place(
                name = R.string.summit_lounge_title,
                description = R.string.summit_lounge_description,
                address = R.string.summit_lounge_address,
                photo = R.drawable.summit_lounge
            )
        )
    )
    private val parksCategory=Category(
        name=R.string.parks_category,
        icon = R.drawable.nature_icon,
        list=listOf(
            Place(
                name = R.string.hoan_kiem_lake_title,
                description = R.string.hoan_kiem_lake_description,
                address = R.string.hoan_kiem_lake_address,
                photo = R.drawable.hoan_kiem_lake
            ),
            Place(
                name = R.string.lenin_park_title,
                description = R.string.lenin_park_description,
                address = R.string.lenin_park_address,
                photo = R.drawable.thong_nhat_park
            )
        )
    )
    private val shopsCategory=Category(
        name=R.string.shops_category,
        icon=R.drawable.shops_icon,
        list = listOf(
            Place(
                name = R.string.dong_xuan_market_title,
                description = R.string.dong_xuan_market_description,
                address = R.string.dong_xuan_market_address,
                photo = R.drawable.dong_xuan_market
            ),
            Place(
                name = R.string.vincom_ba_trieu_title,
                description = R.string.vincom_ba_trieu_description,
                address = R.string.vincom_ba_trieu_address,
                photo = R.drawable.vincom_ba_trieu
            ),
            Place(
                name = R.string.trang_tien_plaza_title,
                description = R.string.trang_tien_plaza_description,
                address = R.string.trang_tien_plaza_address,
                photo = R.drawable.trang_tien_plaza
            )
        )
    )

    private val attractionsCategory= Category(
        name = R.string.attractions_category,
        icon = R.drawable.attractions_icon,
        list = listOf(
            Place(
                name = R.string.old_quarter_title,
                description = R.string.old_quarter_description,
                address = R.string.old_quarter_address,
                photo = R.drawable.old_quarter
            ),
            Place(
                name = R.string.temple_of_literature_title,
                description = R.string.temple_of_literature_description,
                address = R.string.temple_of_literature_address,
                photo = R.drawable.temple_of_literature
            ),
            Place(
                name = R.string.ho_chi_minh_mausoleum_title,
                description = R.string.ho_chi_minh_mausoleum_description,
                address = R.string.ho_chi_minh_mausoleum_address,
                photo = R.drawable.ho_chi_minh_mausoleum
            ),
            Place(
                name = R.string.long_bien_bridge_title,
                description = R.string.long_bien_bridge_description,
                address = R.string.long_bien_bridge_address,
                photo = R.drawable.long_bien_bridge
            )
        )

    )
    val listOfCategories = listOf(restaurantsCategory, barsCategory, parksCategory, shopsCategory, attractionsCategory)

}