package com.example.unit4_pathway2_traininglunchtray.datasource

import com.example.unit4_pathway2_traininglunchtray.model.MenuItem.AccompanimentItem
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem.EntreeItem
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem.SideDishItem

/**
 * Data source object containing all available menu items for the lunch tray app.
 * This serves as a mock data repository for demonstration purposes.
 */
object DataSource {

    /**
     * Available entree (main course) menu items.
     * Each entree serves as the primary component of a meal.
     */
    val entreeMenuItems = listOf(
        EntreeItem(
            name = "Cauliflower",
            description = "Whole cauliflower, brined, roasted, and deep fried",
            price = 7.00,
        ),
        EntreeItem(
            name = "Three Bean Chili",
            description = "Black beans, red beans, kidney beans, slow cooked, topped with onion",
            price = 4.00,
        ),
        EntreeItem(
            name = "Mushroom Pasta",
            description = "Penne pasta, mushrooms, basil, with plum tomatoes cooked in garlic and olive oil",
            price = 5.50,
        ),
        EntreeItem(
            name = "Spicy Black Bean Skillet",
            description = "Seasonal vegetables, black beans, house spice blend, served with avocado and quick pickled onions",
            price = 5.50,
        )
    )

    /**
     * Available side dish menu items.
     * Side dishes complement the main entree.
     */
    val sideDishMenuItems = listOf(
        SideDishItem(
            name = "Summer Salad",
            description = "Heirloom tomatoes, butter lettuce, peaches, avocado, balsamic dressing",
            price = 2.50,
        ),
        SideDishItem(
            name = "Butternut Squash Soup",
            description = "Roasted butternut squash, roasted peppers, chili oil",
            price = 3.00,
        ),
        SideDishItem(
            name = "Spicy Potatoes",
            description = "Marble potatoes, roasted, and fried in house spice blend",
            price = 2.00,
        ),
        SideDishItem(
            name = "Coconut Rice",
            description = "Rice, coconut milk, lime, and sugar",
            price = 1.50,
        )
    )

    /**
     * Available accompaniment menu items.
     * Accompaniments are small additional items like bread or extras.
     */
    val accompanimentMenuItems = listOf(
        AccompanimentItem(
            name = "Lunch Roll",
            description = "Fresh baked roll made in house",
            price = 0.50,
        ),
        AccompanimentItem(
            name = "Mixed Berries",
            description = "Strawberries, blueberries, raspberries, and huckleberries",
            price = 1.00,
        ),
        AccompanimentItem(
            name = "Pickled Veggies",
            description = "Pickled cucumbers and carrots, made in house",
            price = 0.50,
        )
    )

    /**
     * Gets all menu items across all categories.
     * @return List of all available menu items
     */
    fun getAllMenuItems() = entreeMenuItems + sideDishMenuItems + accompanimentMenuItems

    /**
     * Gets the count of menu items in each category.
     * @return Triple containing counts of (entrees, sides, accompaniments)
     */
    fun getMenuCounts() = Triple(
        entreeMenuItems.size,
        sideDishMenuItems.size,
        accompanimentMenuItems.size
    )

    /**
     * Gets the price range for a specific category.
     * @param category The category to get price range for
     * @return Pair of (min price, max price) or null if category is empty
     */
    fun getPriceRange(category: String): Pair<Double, Double>? {
        val items = when (category.lowercase()) {
            "entree" -> entreeMenuItems
            "side", "sidedish" -> sideDishMenuItems
            "accompaniment" -> accompanimentMenuItems
            else -> return null
        }
        
        if (items.isEmpty()) return null
        
        val prices = items.map { it.price }
        return Pair(prices.minOrNull() ?: 0.0, prices.maxOrNull() ?: 0.0)
    }
}