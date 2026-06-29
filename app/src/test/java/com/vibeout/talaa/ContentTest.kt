package com.vibeout.talaa

import com.vibeout.talaa.feature.profile.ProfileInterestCatalog
import com.vibeout.talaa.ui.common.formatPriceLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ContentTest {
    @Test fun interestCatalogHasUniqueUppercaseKeys() {
        val keys = ProfileInterestCatalog.map { it.first }
        assertEquals(12, keys.size)
        assertEquals(keys.size, keys.toSet().size)
        assertTrue(keys.all { it == it.uppercase() && it.isNotBlank() })
    }

    @Test fun priceLevelFormatsToCurrencyTiers() {
        assertEquals("₺", formatPriceLevel("LOW"))
        assertEquals("₺₺", formatPriceLevel("MEDIUM"))
        assertEquals("₺₺₺", formatPriceLevel("HIGH"))
        assertEquals("₺₺₺₺", formatPriceLevel("PREMIUM"))
        assertEquals("UNKNOWN", formatPriceLevel("UNKNOWN"))
    }
}
