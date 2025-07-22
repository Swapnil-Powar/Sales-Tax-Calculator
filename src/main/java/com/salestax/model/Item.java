package com.salestax.model;
import java.math.BigDecimal;
import java.util.Objects;

/* Represents an item in a shopping basket with its properties and tax calculations. */

public class Item 
{
    private final int quantity;
    private final String name;
    private final BigDecimal shelfPrice;
    private final boolean isImported;
    private final ItemCategory category;
    private final BigDecimal salesTax;
    private final BigDecimal totalPrice;
    public Item(int quantity, String name, BigDecimal shelfPrice, boolean isImported, ItemCategory category) 
    {
        if (quantity <= 0) 
        {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (name == null || name.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (shelfPrice == null || shelfPrice.compareTo(BigDecimal.ZERO) < 0) 
        {
            throw new IllegalArgumentException("Shelf price cannot be null or negative");
        }
        if (category == null) 
        {
            throw new IllegalArgumentException("Category cannot be null");
        }
        this.quantity = quantity;
        this.name = name.trim();
        this.shelfPrice = shelfPrice;
        this.isImported = isImported;
        this.category = category;
        this.salesTax = calculateSalesTax();
        this.totalPrice = shelfPrice.add(salesTax);
    }
    private BigDecimal calculateSalesTax() 
    {
        BigDecimal basicTaxRate = category.isExempt() ? BigDecimal.ZERO : new BigDecimal("0.10");
        BigDecimal importTaxRate = isImported ? new BigDecimal("0.05") : BigDecimal.ZERO;
        BigDecimal totalTaxRate = basicTaxRate.add(importTaxRate);    
        if (totalTaxRate.compareTo(BigDecimal.ZERO) == 0) 
        {
            return BigDecimal.ZERO;
        }
        BigDecimal taxAmount = shelfPrice.multiply(totalTaxRate);
        return roundUpToNearestFiveCents(taxAmount);
    }
    private BigDecimal roundUpToNearestFiveCents(BigDecimal amount) 
    {
        // Convert to cents and round up to nearest 5 cents
        BigDecimal cents = amount.multiply(new BigDecimal("100"));
        BigDecimal roundedCents = cents.divide(new BigDecimal("5"), 0, java.math.RoundingMode.UP)
                                      .multiply(new BigDecimal("5"));
        return roundedCents.divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_UP);
    }
    public int getQuantity() 
    {
        return quantity;
    }
    public String getName() 
    {
        return name;
    }
    public BigDecimal getShelfPrice() 
    {
        return shelfPrice;
    }
    public boolean isImported() 
    {
        return isImported;
    }
    public ItemCategory getCategory() 
    {
        return category;
    }
    public BigDecimal getSalesTax() 
    {
        return salesTax;
    }
    public BigDecimal getTotalPrice() 
    {
        return totalPrice;
    }
    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantity == item.quantity &&
               isImported == item.isImported &&
               Objects.equals(name, item.name) &&
               Objects.equals(shelfPrice, item.shelfPrice) &&
               category == item.category;
    }
    @Override
    public int hashCode() 
    {
        return Objects.hash(quantity, name, shelfPrice, isImported, category);
    }
    @Override
    public String toString() 
    {
        return String.format("Item{quantity=%d, name='%s', shelfPrice=%s, isImported=%s, category=%s, salesTax=%s, totalPrice=%s}",
                quantity, name, shelfPrice, isImported, category, salesTax, totalPrice);
    }
}