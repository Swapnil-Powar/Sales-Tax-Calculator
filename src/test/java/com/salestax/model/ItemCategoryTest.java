package com.salestax.model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
class ItemCategoryTest 
{
    @Test
    @DisplayName("Should identify exempt categories")
    void shouldIdentifyExemptCategories() 
    {
        assertTrue(ItemCategory.BOOK.isExempt());
        assertTrue(ItemCategory.FOOD.isExempt());
        assertTrue(ItemCategory.MEDICAL.isExempt());
        assertFalse(ItemCategory.OTHER.isExempt());
    }

    @Test
    @DisplayName("Should categorize book items")
    void shouldCategorizeBookItems() 
    {
        assertEquals(ItemCategory.BOOK, ItemCategory.fromItemName("book"));
        assertEquals(ItemCategory.BOOK, ItemCategory.fromItemName("Book"));
        assertEquals(ItemCategory.BOOK, ItemCategory.fromItemName("BOOK"));
        assertEquals(ItemCategory.BOOK, ItemCategory.fromItemName("history book"));
    }

    @Test
    @DisplayName("Should categorize food items")
    void shouldCategorizeFoodItems() 
    {
        assertEquals(ItemCategory.FOOD, ItemCategory.fromItemName("chocolate bar"));
        assertEquals(ItemCategory.FOOD, ItemCategory.fromItemName("box of chocolates"));
        assertEquals(ItemCategory.FOOD, ItemCategory.fromItemName("imported box of chocolates"));
        assertEquals(ItemCategory.FOOD, ItemCategory.fromItemName("candy"));
        assertEquals(ItemCategory.FOOD, ItemCategory.fromItemName("food"));
    }

    @Test
    @DisplayName("Should categorize medical items")
    void shouldCategorizeMedicalItems() 
    {
        assertEquals(ItemCategory.MEDICAL, ItemCategory.fromItemName("packet of headache pills"));
        assertEquals(ItemCategory.MEDICAL, ItemCategory.fromItemName("pills"));
        assertEquals(ItemCategory.MEDICAL, ItemCategory.fromItemName("medicine"));
        assertEquals(ItemCategory.MEDICAL, ItemCategory.fromItemName("medical supplies"));
        assertEquals(ItemCategory.MEDICAL, ItemCategory.fromItemName("headache relief"));
    }

    @Test
    @DisplayName("Should categorize other items")
    void shouldCategorizeOtherItems() 
    {
        assertEquals(ItemCategory.OTHER, ItemCategory.fromItemName("music CD"));
        assertEquals(ItemCategory.OTHER, ItemCategory.fromItemName("bottle of perfume"));
        assertEquals(ItemCategory.OTHER, ItemCategory.fromItemName("imported bottle of perfume"));
        assertEquals(ItemCategory.OTHER, ItemCategory.fromItemName("electronics"));
    }

    @Test
    @DisplayName("Should handle null item name")
    void shouldHandleNullItemName() 
    {
        assertEquals(ItemCategory.OTHER, ItemCategory.fromItemName(null));
    }

    @Test
    @DisplayName("Should handle empty item name")
    void shouldHandleEmptyItemName() 
    {
        assertEquals(ItemCategory.OTHER, ItemCategory.fromItemName(""));
        assertEquals(ItemCategory.OTHER, ItemCategory.fromItemName("   "));
    }
}