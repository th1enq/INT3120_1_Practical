package com.example.mycity.data

import com.example.mycity.R

object Datasource {

    private val restaurantsCategory = Category(
        name = R.string.restaurants_category,
        icon = R.drawable.restaurant_icon,
        list = listOf(
            Place(
                name = R.string.kavkaz_title,
                description = R.string.kavkaz_description,
                address = R.string.kavkaz_address,
                photo = R.drawable.kavkaz
            ),
            Place(
                name = R.string.genacvale_title,
                description = R.string.genacvale_description,
                address = R.string.genacvale_address,
                photo = R.drawable.genacvale
            ),
            Place(
                name = R.string.black_angus_title,
                description = R.string.black_angus_description,
                address = R.string.black_angus_address,
                photo = R.drawable.blackangus
            ),
            Place(
                name = R.string.sakurada_title,
                description = R.string.sakurada_description,
                address = R.string.sakurada_address,
                photo = R.drawable.sakurada
            )
        )
    )

    private val barsCategory = Category(
        name = R.string.bars_category,
        icon = R.drawable.bar_icon,
        list = listOf(
            Place(
                name = R.string.beer_academy_title,
                description = R.string.beer_academy_description,
                address = R.string.beer_academy_address,
                photo = R.drawable.beeracademy
            ),
            Place(
                name = R.string.dargett_title,
                description = R.string.dargett_description,
                address = R.string.dargett_address,
                photo = R.drawable.dargett
            )
        )
    )
    private val parksCategory=Category(
        name=R.string.parks_category,
        icon = R.drawable.nature_icon,
        list=listOf(
            Place(
                name = R.string.lovers_park_title,
                description = R.string.lovers_park_description,
                address = R.string.lovers_park_address,
                photo = R.drawable.loverspark
            ),
            Place(
                name = R.string.victory_park_title,
                description = R.string.victory_park_description,
                address = R.string.victory_park_address,
                photo = R.drawable.victorypark
            )
        )
    )
    private val shopsCategory=Category(
        name=R.string.shops_category,
        icon=R.drawable.shops_icon,
        list = listOf(
            Place(
                name = R.string.miniso_title,
                description = R.string.miniso_description,
                address = R.string.miniso_address,
                photo = R.drawable.miniso
            ),
            Place(
                name = R.string.rio_mall_title,
                description = R.string.rio_mall_description,
                address = R.string.rio_mall_address,
                photo = R.drawable.riomall
            ),
            Place(
                name = R.string.tsiran_title,
                description = R.string.tsiran_description,
                address = R.string.tsiran_address,
                photo = R.drawable.tsiran
            )
        )
    )

    private val attractionsCategory= Category(
        name = R.string.attractions_category,
        icon = R.drawable.attractions_icon,
        list = listOf(
            Place(
                name = R.string.cascade_title,
                description = R.string.cascade_description,
                address = R.string.cascade_address,
                photo = R.drawable.cascade
            ),
            Place(
                name = R.string.metro_title,
                description = R.string.metro_description,
                address = R.string.metro_address,
                photo = R.drawable.metro
            ),
            Place(
                name = R.string.northern_avenue_title,
                description = R.string.northern_avenue_description,
                address = R.string.northern_avenue_address,
                photo = R.drawable.northernavenue
            ),
            Place(
                name = R.string.vernissage_title,
                description = R.string.vernissage_description,
                address = R.string.vernissage_address,
                photo = R.drawable.vernissage
            )
        )

    )
    val listOfCategories = listOf(restaurantsCategory, barsCategory, parksCategory, shopsCategory, attractionsCategory)

}