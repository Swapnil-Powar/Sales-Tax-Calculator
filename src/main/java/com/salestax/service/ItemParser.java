package com.salestax.service;
import com.salestax.model.Item;
import com.salestax.model.ItemCategory;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Service class responsible for parsing item strings into basic Item objects.
Does not perform tax calculations - only parses the input format.
*/

public class ItemParser 
{
    // Pattern to match: "quantity description at price"
    private static final Pattern ITEM_PATTERN = Pattern.compile(
        "^(\\d+)\\s+(.+?)\\s+at\\s+(\\d+\\.\\d{2})$"
    );
    
    /**
     * Parses an item string into a basic Item object without tax calculations.
     * Expected format: "1 book at 12.49" or "1 imported bottle of perfume at 47.50"
     * 
     * @param itemString the string representation of the item
     * @return the parsed Item object with zero tax values (to be calculated later)
     * @throws IllegalArgumentException if the string format is invalid
    */
    
    public Item parseItem(String itemString) 
    {
        if (itemString == null || itemString.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Item string cannot be null or empty");
        }
        
        String trimmedInput = itemString.trim();
        Matcher matcher = ITEM_PATTERN.matcher(trimmedInput);
        
        if (!matcher.matches()) 
        {
            throw new IllegalArgumentException("Invalid item format: " + itemString);
        }
        
        try 
        {
            int quantity = Integer.parseInt(matcher.group(1));
            String description = matcher.group(2).trim();
            BigDecimal price = new BigDecimal(matcher.group(3));
            
            boolean isImported = description.toLowerCase().contains("imported");
            ItemCategory category = ItemCategory.fromItemName(description);
            
            // Return basic item with zero tax values - tax will be calculated by TaxCalculationService
            return new Item(
                quantity, 
                description, 
                price, 
                isImported, 
                category,
                BigDecimal.ZERO,  // salesTax - to be calculated later
                price             // totalPrice - will be recalculated with tax
            );
            
        } 
        catch (NumberFormatException e) 
        {
            throw new IllegalArgumentException("Invalid number format in item: " + itemString, e);
        }
    }
    
    /**
     * Validates if an item string has the correct format.
     * 
     * @param itemString the string to validate
     * @return true if the format is valid, false otherwise
    */

    public boolean isValidFormat(String itemString) 
    {
        if (itemString == null || itemString.trim().isEmpty()) 
        {
            return false;
        }
        return ITEM_PATTERN.matcher(itemString.trim()).matches();
    }
}