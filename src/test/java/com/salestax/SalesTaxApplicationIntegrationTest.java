package com.salestax;
import com.salestax.model.Receipt;
import com.salestax.service.SalesTaxCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
class SalesTaxApplicationIntegrationTest 
{
    private SalesTaxCalculator calculator;

    @BeforeEach
    void setUp() 
    {
        calculator = new SalesTaxCalculator();
    }

    @Test
    @DisplayName("Should process basket 1 correctly")
    void shouldProcessBasket1Correctly() 
    {
        // Given
        List<String> basket1 = Arrays.asList(
            "1 book at 12.49",
            "1 music CD at 14.99",
            "1 chocolate bar at 0.85"
        );

        // When
        Receipt receipt = calculator.processShoppingBasket(basket1);

        // Then
        assertEquals(new BigDecimal("1.50"), receipt.getTotalSalesTax());
        assertEquals(new BigDecimal("29.83"), receipt.getTotalAmount());
        
        String expectedOutput = "1 book: 12.49\n" +
                               "1 music CD: 16.49\n" +
                               "1 chocolate bar: 0.85\n" +
                               "Sales Taxes: 1.50\n" +
                               "Total: 29.83";
        assertEquals(expectedOutput, receipt.format());
    }

    @Test
    @DisplayName("Should process basket 2 correctly")
    void shouldProcessBasket2Correctly() 
    {
        // Given
        List<String> basket2 = Arrays.asList(
            "1 imported box of chocolates at 10.00",
            "1 imported bottle of perfume at 47.50"
        );

        // When
        Receipt receipt = calculator.processShoppingBasket(basket2);

        // Then
        assertEquals(new BigDecimal("7.65"), receipt.getTotalSalesTax());
        assertEquals(new BigDecimal("65.15"), receipt.getTotalAmount());
        
        String expectedOutput = "1 imported box of chocolates: 10.50\n" +
                               "1 imported bottle of perfume: 54.65\n" +
                               "Sales Taxes: 7.65\n" +
                               "Total: 65.15";
        assertEquals(expectedOutput, receipt.format());
    }

    @Test
    @DisplayName("Should process basket 3 correctly")
    void shouldProcessBasket3Correctly() 
    {
        // Given
        List<String> basket3 = Arrays.asList(
            "1 imported bottle of perfume at 27.99",
            "1 bottle of perfume at 18.99",
            "1 packet of headache pills at 9.75",
            "1 box of imported chocolates at 11.25"
        );

        // When
        Receipt receipt = calculator.processShoppingBasket(basket3);

        // Then
        assertEquals(new BigDecimal("6.70"), receipt.getTotalSalesTax());
        assertEquals(new BigDecimal("74.68"), receipt.getTotalAmount());
        
        String expectedOutput = "1 imported bottle of perfume: 32.19\n" +
                               "1 bottle of perfume: 20.89\n" +
                               "1 packet of headache pills: 9.75\n" +
                               "1 box of imported chocolates: 11.85\n" +
                               "Sales Taxes: 6.70\n" +
                               "Total: 74.68";
        assertEquals(expectedOutput, receipt.format());
    }

    @Test
    @DisplayName("Should process multi-line string input")
    void shouldProcessMultiLineStringInput() 
    {
        // Given
        String basketInput = "1 book at 12.49\n" +
                            "1 music CD at 14.99\n" +
                            "1 chocolate bar at 0.85";

        // When
        Receipt receipt = calculator.processShoppingBasket(basketInput);

        // Then
        assertEquals(new BigDecimal("1.50"), receipt.getTotalSalesTax());
        assertEquals(new BigDecimal("29.83"), receipt.getTotalAmount());
    }

    @Test
    @DisplayName("Should handle empty basket")
    void shouldHandleEmptyBasket() 
    {
        // Given
        List<String> emptyBasket = Arrays.asList();

        // When
        Receipt receipt = calculator.processShoppingBasket(emptyBasket);

        // Then
        assertEquals(new BigDecimal("0.00"), receipt.getTotalSalesTax());
        assertEquals(new BigDecimal("0.00"), receipt.getTotalAmount());
        assertTrue(receipt.getItems().isEmpty());
    }

    @Test
    @DisplayName("Should throw exception for null basket")
    void shouldThrowExceptionForNullBasket() 
    {
        assertThrows(IllegalArgumentException.class, () -> 
            calculator.processShoppingBasket((List<String>) null));
    }

    @Test
    @DisplayName("Should throw exception for null string input")
    void shouldThrowExceptionForNullStringInput() 
    {
        assertThrows(IllegalArgumentException.class, () -> 
            calculator.processShoppingBasket((String) null));
    }

    @Test
    @DisplayName("Should throw exception for empty string input")
    void shouldThrowExceptionForEmptyStringInput() 
    {
        assertThrows(IllegalArgumentException.class, () -> 
            calculator.processShoppingBasket(""));
        
        assertThrows(IllegalArgumentException.class, () -> 
            calculator.processShoppingBasket("   "));
    }
}