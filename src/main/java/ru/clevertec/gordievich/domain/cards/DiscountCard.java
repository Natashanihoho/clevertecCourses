package ru.clevertec.gordievich.domain.cards;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class DiscountCard {

    private String cardName;
    private int discountPercent;
}
