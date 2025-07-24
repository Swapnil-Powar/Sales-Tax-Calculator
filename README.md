# Sales Tax Calculator

A Java application that calculates sales tax for shopping baskets according to specific business rules.

## Business Rules

1. **Basic Sales Tax**: 10% on all goods, except books, food, and medical products, which are exempt
2. **Import Duty**: Additional 5% on all imported goods with no exemptions
3. **Tax Rounding**: Sales tax is rounded up to the nearest 0.05

## Project Structure

```
sales-tax-calculator/
├── pom.xml                                    			      # Maven configuration
├── src/
│   ├── main/java/com/salestax/
│   │   ├── SalesTaxApplication.java          			      # Main application class
│   │   ├── TestApplication.java                          # Application Test
│   │   ├── model/
│   │   │   ├── Item.java                     			      # Item domain model
│   │   │   ├── ItemCategory.java             			      # Item categorization enum
│   │   │   └── Receipt.java                  			      # Receipt model with formatting
│   │   └── service/
│   │       ├── ItemParser.java               			      # Parses item strings
│   │       └── SalesTaxCalculator.java       			      # Main business logic
│   │       └── taxCalculationService.java                # Tax calculation service
│   └── test/java/com/salestax/
│       ├── SalesTaxApplicationIntegrationTest.java                   # Integration tests
│       ├── model/
│       │   ├── ItemTest.java                 			      # Unit tests for Item
│       │   └── ItemCategoryTest.java         			      # Unit tests for ItemCategory
│       └── service/
│           └── ItemParserTest.java           			      # Unit tests for ItemParser
```

## Key Design Decisions

### Object-Oriented Design
- **Item**: Immutable value object that encapsulates item properties and tax calculations
- **Receipt**: Aggregates items and provides formatted output
- **ItemCategory**: Enum with business logic for tax exemptions
- **ItemParser**: Service for parsing input strings into domain objects
- **SalesTaxCalculator**: Main service orchestrating the business logic

### Tax Calculation
- Tax calculation is performed in the Item constructor to ensure consistency
- Rounding logic uses BigDecimal for precision and follows the "round up to nearest 0.05" rule
- Import detection is based on the presence of "imported" in the item name

### Input Parsing
- Robust regex-based parsing with validation
- Supports flexible item descriptions while maintaining strict format requirements
- Comprehensive error handling with meaningful exception messages

## Running the Application

### Prerequisites
- Java 11 or higher
- Maven (optional - can compile directly with javac)

### Compilation and Execution

#### Using Java directly:
```bash
# Compile
cd src/main/java
javac -d ../../../target/classes com/salestax/model/*.java com/salestax/service/*.java com/salestax/*.java
cd ../../..

# Run
java -cp target/classes com.salestax.SalesTaxApplication
```

#### Test Mode (Original Test Cases)
```bash
java -cp target/classes com.salestax.TestApplication
```

## Output

The application processes three predefined shopping baskets and produces the following output:

```
Output 1:
1 book: 12.49
1 music CD: 16.49
1 chocolate bar: 0.85
Sales Taxes: 1.50
Total: 29.83

Output 2:
1 imported box of chocolates: 10.50
1 imported bottle of perfume: 54.65
Sales Taxes: 7.65
Total: 65.15

Output 3:
1 imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 9.75
1 box of imported chocolates: 11.85
Sales Taxes: 6.70
Total: 74.68
```

## Testing

The project includes comprehensive unit and integration tests covering:
- Tax calculation accuracy
- Input parsing validation
- Edge cases and error conditions
- End-to-end functionality with the provided examples
