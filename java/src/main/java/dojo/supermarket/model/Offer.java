package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    public String generateDescription() {
        return offerType.generateDescription(argument);
    }

    public double calculateDiscount(double quantity, double unitPrice) {
        return offerType.calculateDiscount(quantity, unitPrice, argument);
    }

    public boolean applies(SpecialOfferType offerType, double quantity) {
        return this.offerType == offerType && this.offerType.applies((int) quantity);
    }
}
