package com.salestax.model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
class ItemTest 
{
    @Test
    @DisplayName("Should create item with basic tax for non-exempt item")
    void shouldCreateItemWithBasicTax() 
    {
        // Given
        Item item = new Item(1, "music CD", new BigDecimal("14.99"), false, ItemCategory.OTHER);
        
        // Then
        assertEquals(1, item.getQuantity());
        assertEquals("music CD", item.getName());
        assertEquals(new BigDecimal("14.99"), item.getShelfPrice());
        assertFalse(item.isImported());
        assertEquals(ItemCategory.OTHER, item.getCategory());
        assertEquals(new BigDecimal("1.50"), item.getSalesTax()); // 14.99 * 0.10 = 1.499, rounded up to 1.50
        assertEquals(new BigDecimal("16.49"), item.getTotalPrice());
    }

    @Test
    @DisplayName("Should create exempt item with no basic tax")
    void shouldCreateExemptItemWithNoBasicTax() 
    {
        // Given
        Item item = new Item(1, "book", new BigDecimal("12.49"), false, ItemCategory.BOOK);
        
        // Then
        assertEquals(new BigDecimal("0.00"), item.getSalesTax());
        assertEquals(new BigDecimal("12.49"), item.getTotalPrice());
    }

    @Test
    @DisplayName("Should create imported item with import duty")
    void shouldCreateImportedItemWithImportDuty() 
    {
        // Given
        Item item = new Item(1, "imported box of chocolates", new BigDecimal("10.00"), true, ItemCategory.FOOD);
        
        // Then
        assertEquals(new BigDecimal("0.50"), item.getSalesTax()); // 10.00 * 0.05 = 0.50
        assertEquals(new BigDecimal("10.50"), item.getTotalPrice());
    }

    @Test
    @DisplayName("Should create imported non-exempt item with both taxes")
    void shouldCreateImportedNonExemptItemWithBothTaxes() 
    {
        // Given
        Item item = new Item(1, "imported bottle of perfume", new BigDecimal("47.50"), true, ItemCategory.OTHER);
        
        // Then
        assertEquals(new BigDecimal("7.15"), item.getSalesTax()); // 47.50 * 0.15 = 7.125, rounded up to 7.15
        assertEquals(new BigDecimal("54.65"), item.getTotalPrice());
    }

    @Test
    @DisplayName("Should round tax up to nearest 0.05")
    void shouldRoundTaxUpToNearestFiveCents() 
    {
        // Given - price that results in tax needing rounding
        Item item = new Item(1, "test item", new BigDecimal("18.99"), false, ItemCategory.OTHER);
        
        // Then
        assertEquals(new BigDecimal("1.90"), item.getSalesTax()); // 18.99 * 0.10 = 1.899, rounded up to 1.90
        assertEquals(new BigDecimal("20.89"), item.getTotalPrice());
    }

    @Test
    @DisplayName("Should throw exception for invalid quantity")
    void shouldThrowExceptionForInvalidQuantity() 
    {
        assertThrows(IllegalArgumentException.class, () -> 
            new Item(0, "test", new BigDecimal("10.00"), false, ItemCategory.OTHER));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Item(-1, "test", new BigDecimal("10.00"), false, ItemCategory.OTHER));
    }

    @Test
    @DisplayName("Should throw exception for null or empty name")
    void shouldThrowExceptionForInvalidName() 
    {
        assertThrows(IllegalArgumentException.class, () -> 
            new Item(1, null, new BigDecimal("10.00"), false, ItemCategory.OTHER));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Item(1, "", new BigDecimal("10.00"), false, ItemCategory.OTHER));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Item(1, "   ", new BigDecimal("10.00"), false, ItemCategory.OTHER));
    }

    @Test
    @DisplayName("Should throw exception for invalid price")
    void shouldThrowExceptionForInvalidPrice() 
    {
        assertThrows(IllegalArgumentException.class, () -> 
            new Item(1, "test", null, false, ItemCategory.OTHER));
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Item(1, "test", new BigDecimal("-1.00"), false, ItemCategory.OTHER));
    }

    @Test
    @DisplayName("Should throw exception for null category")
    void shouldThrowExceptionForNullCategory() 
    {
        assertThrows(IllegalArgumentException.class, () -> 
            new Item(1, "test", new BigDecimal("10.00"), false, null));
    }

    @Test
    @DisplayName("Should trim item name")
    void shouldTrimItemName() 
    {
        // Given
        Item item = new Item(1, "  test item  ", new BigDecimal("10.00"), false, ItemCategory.OTHER);
        
        // Then
        assertEquals("test item", item.getName());
    }
}