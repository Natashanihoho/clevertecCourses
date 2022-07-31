package ru.clevertec.gordievich.domain.products;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class Product {

    private int id;
    private String description;
    private double price;
    @Setter
    private int availableNumber;
    private boolean isSpecialOffer;

}
