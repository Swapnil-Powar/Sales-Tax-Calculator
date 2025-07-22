package com.salestax;
import com.salestax.model.Receipt;
import com.salestax.service.SalesTaxCalculator;
import java.util.Arrays;
import java.util.List;

/* Main application class for the Sales Tax Calculator. Processes the three example shopping baskets and prints their receipts. */

public class SalesTaxApplication 
{
    public static void main(String[] args) 
    {
        SalesTaxCalculator calculator = new SalesTaxCalculator();    
        // Test data from the problem statement
        List<String> basket1 = Arrays.asList(
            "1 book at 12.49",
            "1 music CD at 14.99",
            "1 chocolate bar at 0.85"
        );
        
        List<String> basket2 = Arrays.asList(
            "1 imported box of chocolates at 10.00",
            "1 imported bottle of perfume at 47.50"
        );
        
        List<String> basket3 = Arrays.asList(
            "1 imported bottle of perfume at 27.99",
            "1 bottle of perfume at 18.99",
            "1 packet of headache pills at 9.75",
            "1 box of imported chocolates at 11.25"
        );

        // Process and print receipts
        System.out.println("Output 1:");
        Receipt receipt1 = calculator.processShoppingBasket(basket1);
        System.out.println(receipt1.format());
        System.out.println();

        System.out.println("Output 2:");
        Receipt receipt2 = calculator.processShoppingBasket(basket2);
        System.out.println(receipt2.format());
        System.out.println();

        System.out.println("Output 3:");
        Receipt receipt3 = calculator.processShoppingBasket(basket3);
        System.out.println(receipt3.format());
    }
}