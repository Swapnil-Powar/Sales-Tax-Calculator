package com.salestax;
import com.salestax.model.Receipt;
import com.salestax.service.SalesTaxCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Main application class for the Sales Tax Calculator.
Reads shopping basket items from console input and calculates taxes.
*/

public class SalesTaxApplication 
{    
    public static void main(String[] args) 
    {
        SalesTaxCalculator calculator = new SalesTaxCalculator();
        Scanner scanner = new Scanner(System.in);    
        System.out.println("Sales Tax Calculator");
        System.out.println("===================");
        System.out.println();
        
        List<String> basketItems = new ArrayList<>();
        String input;
        
        while (true) 
        {
            System.out.print("Enter item (or 'done' to finish): ");
            input = scanner.nextLine().trim();
            
            if ("done".equalsIgnoreCase(input)) 
            {
                break;
            }
            
            if (!input.isEmpty()) 
            {
                basketItems.add(input);
            }
        }
        
        if (basketItems.isEmpty()) 
        {
            System.out.println("No items entered. Exiting.");
            scanner.close();
            return;
        }
        
        try 
        {
            Receipt receipt = calculator.processShoppingBasket(basketItems);
            System.out.println();
            System.out.println("Receipt:");
            System.out.println("========");
            System.out.println(receipt.format());
        } 
        catch (Exception e) 
        {
            System.err.println("Error processing basket: " + e.getMessage());
        }
        scanner.close();
    }
}