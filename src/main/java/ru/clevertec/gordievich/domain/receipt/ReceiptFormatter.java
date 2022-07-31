package ru.clevertec.gordievich.domain.receipt;

public final class ReceiptFormatter {

    public static final String TITLE = """
                          CASH RECEIPT
                          
                     SUPERMARKET "LOLLIPOP" 
              17100 Collins Ave Sunny Isles Beach,              
                            FL 33160
                      Tel: 37529-234-6375
                      
            Cashier: %-15s DATE: %s
                                     TIME: %s
            -----------------------------------------
            ID | DESCRIPTION | PRICE | NUMBER | TOTAL
            -----------------------------------------
            """;
    public static final String NORMAL_FIELD = """
            %-4d %-13s %-7.2f %-8d %-6.2f
            """;

    public static final String DISCOUNT_FIELD = """
                                      discount %.2f
            """;

    public static final String COMPLETION = """
            -----------------------------------------
            -----------------------------------------
            Discount%33.2f
                                              -------
            TO PAY%35.2f
                         ---THANK YOU!---
            """;

    private ReceiptFormatter() {
    }


}
