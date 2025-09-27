package com.example.unit2_pathway3_tipcalculator

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorTests {
    @Test
    fun calculateTip_20PercentNoRoundup() {
        val amount = 10.00
        val tipPercent = 20.0
        val tip = 2.0
        val total = 12.0

        val currencyFormatter = NumberFormat.getCurrencyInstance()
        val expected = TipCalculationResult(
            tipAmount = currencyFormatter.format(tip),
            totalAmount = currencyFormatter.format(total),
            tipPercentage = tipPercent
        )

        val actual = calculateTipResult(
            amount = amount,
            tipPercent = tipPercent,
            roundUp = false
        )

        assertEquals(expected, actual)
    }
}
