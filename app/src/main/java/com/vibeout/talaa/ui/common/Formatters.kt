package com.vibeout.talaa.ui.common

import com.vibeout.talaa.core.model.City
import java.util.Locale

fun City.localizedName(locale: Locale = Locale.getDefault()): String = when (locale.language) {
    "ar" -> nameAr.ifBlank { nameEn }
    "tr" -> nameTr.ifBlank { nameEn }
    else -> nameEn
}

fun formatPriceLevel(level: String): String = when (level) {
    "LOW" -> "₺"
    "MEDIUM" -> "₺₺"
    "HIGH" -> "₺₺₺"
    "PREMIUM" -> "₺₺₺₺"
    else -> level
}
