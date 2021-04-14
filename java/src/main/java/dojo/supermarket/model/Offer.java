package dojo.supermarket.model;

import dojo.supermarket.model.specialoffer.SpecialOffer;

public class Offer {
    SpecialOffer specialOffer;
    private final Product product;
    double argument;

    public Offer(SpecialOffer specialOffer, Product product, double argument) {
        this.specialOffer = specialOffer;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    public boolean isApplicable(double quantity) {
        return specialOffer.isApplicable(quantity);
    }

    public String generateDescription() {
        return specialOffer.generateDescription(argument);
    }

    public double calculateDiscountAmount(double quantity, double unitPrice) {
        return specialOffer.calculateDiscountAmount(quantity, argument, unitPrice);
    }
}
