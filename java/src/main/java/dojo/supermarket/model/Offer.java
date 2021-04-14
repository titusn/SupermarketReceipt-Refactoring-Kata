package dojo.supermarket.model;

import dojo.supermarket.model.specialoffer.SpecialOffer;
import dojo.supermarket.model.specialoffer.TwoForAmount;

public class Offer {
    SpecialOfferType offerType;
    SpecialOffer specialOffer;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.specialOffer = new TwoForAmount();
        this.argument = argument;
        this.product = product;
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
}
