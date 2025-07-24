package com.salestax.service;
import com.salestax.model.ItemCategory;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
Service responsible for calculating sales tax and import duty.
Handles all tax-related calculations and rounding logic.
*/

public class TaxCalculationService 
{    
    private static final BigDecimal BASIC_TAX_RATE = new BigDecimal("0.10");
    private static final BigDecimal IMPORT_TAX_RATE = new BigDecimal("0.05");
    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private static final BigDecimal FIVE = new BigDecimal("5");
    
    /**
     * Calculates the total sales tax for an item.
     * 
     * @param shelfPrice the shelf price of the item
     * @param category the category of the item
     * @param isImported whether the item is imported
     * @return the calculated sales tax amount
     */
 
    public BigDecimal calculateSalesTax(BigDecimal shelfPrice, ItemCategory category, boolean isImported) 
    {
        if (shelfPrice == null || shelfPrice.compareTo(BigDecimal.ZERO) < 0) 
        {
            throw new IllegalArgumentException("Shelf price cannot be null or negative");
        }
        if (category == null) 
        {
            throw new IllegalArgumentException("Category cannot be null");
        }
        
        BigDecimal basicTaxRate = category.isExempt() ? BigDecimal.ZERO : BASIC_TAX_RATE;
        BigDecimal importTaxRate = isImported ? IMPORT_TAX_RATE : BigDecimal.ZERO;
        BigDecimal totalTaxRate = basicTaxRate.add(importTaxRate);
        
        if (totalTaxRate.compareTo(BigDecimal.ZERO) == 0) 
        {
            return BigDecimal.ZERO;
        }
        
        BigDecimal taxAmount = shelfPrice.multiply(totalTaxRate);
        return roundUpToNearestFiveCents(taxAmount);
    }
    
    /**
     * Rounds up the tax amount to the nearest 5 cents.
     * 
     * @param amount the amount to round
     * @return the rounded amount
    */
    
    private BigDecimal roundUpToNearestFiveCents(BigDecimal amount) 
    {
        // Convert to cents and round up to nearest 5 cents
        BigDecimal cents = amount.multiply(HUNDRED);
        BigDecimal roundedCents = cents.divide(FIVE, 0, RoundingMode.UP).multiply(FIVE);
        return roundedCents.divide(HUNDRED, 2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculates the total price including tax.
     * 
     * @param shelfPrice the shelf price of the item
     * @param salesTax the calculated sales tax
     * @return the total price including tax
    */

    public BigDecimal calculateTotalPrice(BigDecimal shelfPrice, BigDecimal salesTax) 
    {
        if (shelfPrice == null || salesTax == null) 
        {
            throw new IllegalArgumentException("Shelf price and sales tax cannot be null");
        }
        return shelfPrice.add(salesTax);
    }
}