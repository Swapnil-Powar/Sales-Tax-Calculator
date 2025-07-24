package com.salestax.model;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* 
Represents a shopping receipt containing items and their tax calculations. 
*/

public class Receipt 
{
    private final List<Item> items;
    private final BigDecimal totalSalesTax;
    private final BigDecimal totalAmount;
    
    public Receipt(List<Item> items) 
    {
        if (items == null) 
        {
            throw new IllegalArgumentException("Items list cannot be null");
        }
        this.items = new ArrayList<>(items);
        this.totalSalesTax = calculateTotalSalesTax();
        this.totalAmount = calculateTotalAmount();
    }
    
    private BigDecimal calculateTotalSalesTax() 
    {
        return items.stream()
                   .map(Item::getSalesTax)
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private BigDecimal calculateTotalAmount() 
    {
        return items.stream()
                   .map(Item::getTotalPrice)
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<Item> getItems() 
    {
        return Collections.unmodifiableList(items);
    }
    
    public BigDecimal getTotalSalesTax() 
    {
        return totalSalesTax;
    }
    
    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    /*
    * Formats the receipt as a string matching the expected output format.
    * 
    * @return formatted receipt string
    */
    
    public String format() 
    {
        StringBuilder receipt = new StringBuilder();
        DecimalFormat priceFormat = new DecimalFormat("0.00");
        
        // Add each item line
        for (Item item : items) 
        {
            receipt.append(item.getQuantity())
                   .append(" ")
                   .append(item.getName())
                   .append(": ")
                   .append(priceFormat.format(item.getTotalPrice()))
                   .append("\n");
        }
        
        // Add sales tax line
        receipt.append("Sales Taxes: ")
               .append(priceFormat.format(totalSalesTax))
               .append("\n");
        
        // Add total line
        receipt.append("Total: ")
               .append(priceFormat.format(totalAmount));
        
        return receipt.toString();
    }

    @Override
    public String toString() 
    {
        return format();
    }
}