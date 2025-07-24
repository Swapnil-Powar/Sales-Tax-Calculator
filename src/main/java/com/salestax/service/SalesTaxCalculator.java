package com.salestax.service;
import com.salestax.model.Item;
import com.salestax.model.Receipt;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
Service class responsible for processing shopping baskets and generating receipts.
Coordinates between item parsing and tax calculation services.
*/

public class SalesTaxCalculator 
{
    private final ItemParser itemParser;
    private final TaxCalculationService taxCalculationService;
    
    // Creates a new SalesTaxCalculator with default dependencies.
    
    public SalesTaxCalculator() 
    {
        this.itemParser = new ItemParser();
        this.taxCalculationService = new TaxCalculationService();
    }
    
    /**
     * Processes a shopping basket represented as a list of item strings
     * and returns a receipt with calculated taxes.
     * 
     * @param itemStrings list of item strings in the format "quantity description at price"
     * @return Receipt object containing all items with calculated taxes
     * @throws IllegalArgumentException if any item string is invalid
    */
    
    public Receipt processShoppingBasket(List<String> itemStrings) 
    {
        if (itemStrings == null) 
        {
            throw new IllegalArgumentException("Item strings list cannot be null");
        }
        List<Item> items = new ArrayList<>();
        for (String itemString : itemStrings) 
        {
            if (itemString != null && !itemString.trim().isEmpty()) 
            {
                Item item = parseAndCalculateItem(itemString);
                items.add(item);
            }
        }
        return new Receipt(items);
    }
    
    /**
     * Processes a single shopping basket from a multi-line string input.
     * 
     * @param basketInput multi-line string where each line represents an item
     * @return Receipt object containing all items with calculated taxes
    */

    public Receipt processShoppingBasket(String basketInput) 
    {
        if (basketInput == null || basketInput.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Basket input cannot be null or empty");
        }
        
        String[] lines = basketInput.trim().split("\\r?\\n");
        List<String> itemStrings = new ArrayList<>();
        
        for (String line : lines) 
        {
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty()) 
            {
                itemStrings.add(trimmedLine);
            }
        }
        
        return processShoppingBasket(itemStrings);
    }
    
    /**
     * Parses an item string and calculates its tax and total price.
     * 
     * @param itemString the item string to parse
     * @return Item object with calculated tax and total price
    */

    private Item parseAndCalculateItem(String itemString) 
    {
        // Parse the basic item information
        Item basicItem = itemParser.parseItem(itemString);    
        // Calculate tax using the tax calculation service
        BigDecimal salesTax = taxCalculationService.calculateSalesTax(
            basicItem.getShelfPrice(), 
            basicItem.getCategory(), 
            basicItem.isImported()
        );
        
        // Calculate total price
        BigDecimal totalPrice = taxCalculationService.calculateTotalPrice(
            basicItem.getShelfPrice(), 
            salesTax
        );
        
        // Create a new Item with calculated values
        return new Item(
            basicItem.getQuantity(),
            basicItem.getName(),
            basicItem.getShelfPrice(),
            basicItem.isImported(),
            basicItem.getCategory(),
            salesTax,
            totalPrice
        );
    }
}