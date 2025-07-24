package com.salestax.model;
import java.math.BigDecimal;
import java.util.Objects;

/*  
Represents an item in a shopping basket with its properties. 
This is a simple data model that holds item information without business logic. 
*/

public class Item 
{
    private final int quantity;
    private final String name;
    private final BigDecimal shelfPrice;
    private final boolean isImported;
    private final ItemCategory category;
    private final BigDecimal salesTax;
    private final BigDecimal totalPrice;
    
    /**
    * Creates a new Item with calculated tax and total price.
    * 
    * @param quantity the quantity of the item
    * @param name the name/description of the item
    * @param shelfPrice the shelf price of the item
    * @param isImported whether the item is imported
    * @param category the category of the item
    * @param salesTax the calculated sales tax
    * @param totalPrice the total price including tax
    */
    
    public Item(int quantity, String name, BigDecimal shelfPrice, boolean isImported, 
                    ItemCategory category, BigDecimal salesTax, BigDecimal totalPrice) 
    {
        validateInputs(quantity, name, shelfPrice, category, salesTax, totalPrice);   
        this.quantity = quantity;
        this.name = name.trim();
        this.shelfPrice = shelfPrice;
        this.isImported = isImported;
        this.category = category;
        this.salesTax = salesTax;
        this.totalPrice = totalPrice;
    }
    
    private void validateInputs(int quantity, String name, BigDecimal shelfPrice, 
                               ItemCategory category, BigDecimal salesTax, BigDecimal totalPrice) 
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
        if (salesTax == null || salesTax.compareTo(BigDecimal.ZERO) < 0) 
        {
            throw new IllegalArgumentException("Sales tax cannot be null or negative");
        }
        if (totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) < 0) 
        {
            throw new IllegalArgumentException("Total price cannot be null or negative");
        }
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
}