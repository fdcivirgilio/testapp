package com.example.testapp.fragments.placeholder

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    init {
        val menuItems = listOf(
            "Profile", "Users", "More"
        )

        menuItems.forEach { item ->
            addItem(createPlaceholderItem(item))
        }
    }

    private fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.content, item)
    }

    private fun createPlaceholderItem(content: String): PlaceholderItem {
        return PlaceholderItem(content)
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(val content: String) {
        override fun toString(): String = content
    }
}