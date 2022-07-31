package ru.clevertec.gordievich.domain.receipt;

import ru.clevertec.gordievich.domain.products.Product;

public record Position(Product product, int requiredNumber) {

    public int getRequiredNumber() {
        return requiredNumber;
    }

    public Product getProduct() {
        return product;
    }

    public double getFullPrice() {
        return this.product.getPrice() * this.requiredNumber;
    }

    public boolean isSpecialDiscount() {
        return this.product.isSpecialOffer() && this.requiredNumber >= 5;
    }

}
