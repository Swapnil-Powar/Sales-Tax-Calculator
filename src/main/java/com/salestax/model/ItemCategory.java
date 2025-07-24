package com.salestax.model;

/*
Represents the category of an item for tax calculation purposes. 
Books, food, and medical products are exempt from basic sales tax. 
*/

public enum ItemCategory 
{
    BOOK(true), FOOD(true), MEDICAL(true), OTHER(false);
    private final boolean exempt;
    ItemCategory(boolean exempt) 
    {
        this.exempt = exempt;
    }
    
    /**
    * Returns true if this category is exempt from basic sales tax.
    * 
    * @return true if exempt from basic sales tax, false otherwise
    */
    
    public boolean isExempt() 
    {
        return exempt;
    }

    /**
    * Determines the category of an item based on its name.
    * This method uses simple keyword matching to categorize items.
    * 
    * @param itemName the name of the item
    * @return the appropriate ItemCategory
    */

    public static ItemCategory fromItemName(String itemName) 
    {
        if (itemName == null) 
        {
            return OTHER;
        }
        String lowerName = itemName.toLowerCase();

        // Check for book keywords
        if (lowerName.contains("book")) 
        {
            return BOOK;
        }
        
        // Check for food keywords
        if (lowerName.contains("chocolate") || lowerName.contains("candy") || lowerName.contains("food")) 
        {
            return FOOD;
        }
        
        // Check for medical keywords
        if (lowerName.contains("pills") || lowerName.contains("medicine") || lowerName.contains("medical") || lowerName.contains("headache")) 
        {
            return MEDICAL;
        }
        return OTHER;
    }
}