package com.salestax.service;
import com.salestax.model.Item;
import com.salestax.model.ItemCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
class ItemParserTest 
{
    private ItemParser parser;

    @BeforeEach
    void setUp() 
    {
        parser = new ItemParser();
    }

    @Test
    @DisplayName("Should parse simple item correctly")
    void shouldParseSimpleItem() 
    {
        // Given
        String itemString = "1 book at 12.49";
        
        // When
        Item item = parser.parseItem(itemString);
        
        // Then
        assertEquals(1, item.getQuantity());
        assertEquals("book", item.getName());
        assertEquals(new BigDecimal("12.49"), item.getShelfPrice());
        assertFalse(item.isImported());
        assertEquals(ItemCategory.BOOK, item.getCategory());
    }

    @Test
    @DisplayName("Should parse imported item correctly")
    void shouldParseImportedItem() 
    {
        // Given
        String itemString = "1 imported bottle of perfume at 47.50";
        
        // When
        Item item = parser.parseItem(itemString);
        
        // Then
        assertEquals(1, item.getQuantity());
        assertEquals("imported bottle of perfume", item.getName());
        assertEquals(new BigDecimal("47.50"), item.getShelfPrice());
        assertTrue(item.isImported());
        assertEquals(ItemCategory.OTHER, item.getCategory());
    }

    @Test
    @DisplayName("Should parse item with multiple words correctly")
    void shouldParseItemWithMultipleWords() 
    {
        // Given
        String itemString = "1 packet of headache pills at 9.75";
        
        // When
        Item item = parser.parseItem(itemString);
        
        // Then
        assertEquals(1, item.getQuantity());
        assertEquals("packet of headache pills", item.getName());
        assertEquals(new BigDecimal("9.75"), item.getShelfPrice());
        assertFalse(item.isImported());
        assertEquals(ItemCategory.MEDICAL, item.getCategory());
    }

    @Test
    @DisplayName("Should parse item with quantity greater than 1")
    void shouldParseItemWithMultipleQuantity() 
    {
        // Given
        String itemString = "3 chocolate bars at 0.85";
        
        // When
        Item item = parser.parseItem(itemString);
        
        // Then
        assertEquals(3, item.getQuantity());
        assertEquals("chocolate bars", item.getName());
        assertEquals(new BigDecimal("0.85"), item.getShelfPrice());
        assertFalse(item.isImported());
        assertEquals(ItemCategory.FOOD, item.getCategory());
    }

    @Test
    @DisplayName("Should handle extra whitespace")
    void shouldHandleExtraWhitespace() 
    {
        // Given
        String itemString = "  1   book   at   12.49  ";
        
        // When
        Item item = parser.parseItem(itemString);
        
        // Then
        assertEquals(1, item.getQuantity());
        assertEquals("book", item.getName());
        assertEquals(new BigDecimal("12.49"), item.getShelfPrice());
    }

    @Test
    @DisplayName("Should validate correct format")
    void shouldValidateCorrectFormat() 
    {
        assertTrue(parser.isValidFormat("1 book at 12.49"));
        assertTrue(parser.isValidFormat("10 imported chocolates at 5.00"));
        assertTrue(parser.isValidFormat("1 packet of headache pills at 9.75"));
    }

    @Test
    @DisplayName("Should invalidate incorrect format")
    void shouldInvalidateIncorrectFormat() 
    {
        assertFalse(parser.isValidFormat("book at 12.49")); // missing quantity
        assertFalse(parser.isValidFormat("1 book 12.49")); // missing "at"
        assertFalse(parser.isValidFormat("1 book at")); // missing price
        assertFalse(parser.isValidFormat("1 book at 12")); // invalid price format
        assertFalse(parser.isValidFormat("1 book at 12.4")); // invalid price format
        assertFalse(parser.isValidFormat("1 book at 12.499")); // invalid price format
        assertFalse(parser.isValidFormat("")); // empty string
        assertFalse(parser.isValidFormat(null)); // null string
    }

    @Test
    @DisplayName("Should throw exception for null input")
    void shouldThrowExceptionForNullInput() 
    {
        assertThrows(IllegalArgumentException.class, () -> parser.parseItem(null));
    }

    @Test
    @DisplayName("Should throw exception for empty input")
    void shouldThrowExceptionForEmptyInput() 
    {
        assertThrows(IllegalArgumentException.class, () -> parser.parseItem(""));
        assertThrows(IllegalArgumentException.class, () -> parser.parseItem("   "));
    }

    @Test
    @DisplayName("Should throw exception for invalid format")
    void shouldThrowExceptionForInvalidFormat() 
    {
        assertThrows(IllegalArgumentException.class, () -> parser.parseItem("invalid format"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseItem("1 book"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseItem("book at 12.49"));
    }

    @Test
    @DisplayName("Should throw exception for invalid number format")
    void shouldThrowExceptionForInvalidNumberFormat() 
    {
        assertThrows(IllegalArgumentException.class, () -> parser.parseItem("abc book at 12.49"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseItem("1 book at abc"));
    }
}