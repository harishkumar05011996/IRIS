package com.IRIS.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceCalculatorUtil {

    public static BigDecimal parseMoney(String rawText) {
        if (rawText == null) {
            throw new IllegalArgumentException("Price text is null");
        }

        // Keep digits and decimal point, remove commas too
        String cleaned = rawText.replaceAll("[^0-9.]", "");

        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("No numeric value found in: " + rawText);
        }

        return new BigDecimal(cleaned);
    }

    // Format BigDecimal to 2 decimal string (e.g., 2.4 -> "2.40")
    public static String formatMoney(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    // Calculate tax = itemTotal * taxRate, rounded to 2 decimals
    public static BigDecimal calculateTax(BigDecimal itemTotal, BigDecimal taxRate) {
        return itemTotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate total = itemTotal + tax, rounded to 2 decimals
    public static BigDecimal calculateTotal(BigDecimal itemTotal, BigDecimal tax) {
        return itemTotal.add(tax).setScale(2, RoundingMode.HALF_UP);
    }
}