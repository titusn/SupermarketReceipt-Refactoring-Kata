package dojo.supermarket.model;

import java.util.*;

public class OfferHandler {
    public OfferHandler(Map<Product, Double> productQuantities) {
        this.productQuantities = productQuantities;
    }

    Map<Product, Double> productQuantities;

    List<Discount> getDiscounts(Map<Product, Offer> offers, SupermarketCatalog catalog) {
        Optional<Discount> discount;
        List<Discount> discountList = new ArrayList<>();
        for (Product p: productQuantities.keySet()) {
            discount = checkOffersForProduct(offers, catalog, p);
            discount.ifPresent(discountList::add);
        }
        return discountList;
    }

    private Optional<Discount> checkOffersForProduct(Map<Product, Offer> offers, SupermarketCatalog catalog, Product p) {
        if (offers.containsKey(p)) {
            return getOptionalDiscount(offers, catalog, p);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Discount> getOptionalDiscount(Map<Product, Offer> offers, SupermarketCatalog catalog, Product p) {
        Optional<Discount> discount = Optional.empty();
        double quantity = productQuantities.get(p);
        Offer offer = offers.get(p);
        double unitPrice = catalog.getUnitPrice(p);
        if (offer.offerType == SpecialOfferType.TWO_FOR_AMOUNT && offer.offerType.applies((int) quantity)) {
            double discountAmount = offer.calculateDiscount(quantity, unitPrice);
            discount = Optional.of(new Discount(p, offer.generateDescription(), -discountAmount));
        }
        if (offer.offerType == SpecialOfferType.THREE_FOR_TWO && offer.offerType.applies((int) quantity)) {
            double discountAmount = offer.calculateDiscount(quantity, unitPrice);
            discount = Optional.of(new Discount(p, offer.generateDescription(), -discountAmount));
        }
        if (offer.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT && offer.offerType.applies((int) quantity)) {
            double discountAmount = offer.calculateDiscount(quantity, unitPrice);
            discount = Optional.of(new Discount(p, offer.generateDescription(), -discountAmount));
        }
        if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT && offer.offerType.applies((int) quantity)) {
            double discountAmount = offer.calculateDiscount(quantity, unitPrice);
            discount = Optional.of(new Discount(p, offer.generateDescription(), -discountAmount));
        }
        return discount;
    }
}
