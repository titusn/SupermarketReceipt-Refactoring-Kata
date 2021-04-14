package dojo.supermarket.model;

import dojo.supermarket.model.specialoffer.SpecialOffer;

public class Offer {
    SpecialOfferType offerType;
    SpecialOffer specialOffer;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    public Offer(SpecialOffer specialOffer, Product product, double argument) {
        this(SpecialOfferType.TwoForAmount, product, argument);
        this.specialOffer = specialOffer;
    }

    Product getProduct() {
        return this.product;
    }

    public boolean isApplicable(double quantity) {
        return offerType == SpecialOfferType.TwoForAmount && specialOffer.isApplicable(quantity);
    }

    public String generateDescription() {
        return specialOffer.generateDescription(argument);
    }

    public double calculateDiscountAmount(double quantity, double unitPrice) {
        return specialOffer.calculateDiscountAmount(quantity, argument, unitPrice);
    }
}
