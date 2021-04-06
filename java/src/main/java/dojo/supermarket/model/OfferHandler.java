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
        int quantityAsInt = (int) quantity;
        int divisor;
        if (offer.offerType == SpecialOfferType.TWO_FOR_AMOUNT && quantityAsInt >= 2) {
            divisor = 2;
            double discountAmount = calculateDiscountN(quantity, offer, unitPrice, quantityAsInt, divisor);
            discount = Optional.of(new Discount(p, offer.offerType.getDescription() + offer.argument, -discountAmount));
        }
        if (offer.offerType == SpecialOfferType.THREE_FOR_TWO && quantityAsInt >= 3) {
            divisor = 3;
            int multiplier = 2;
            double discountAmount = calculateDiscountN(quantity, offer, unitPrice, quantityAsInt, divisor, multiplier);
            discount = Optional.of(new Discount(p, offer.offerType.getDescription(), -discountAmount));
        }
        if (offer.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
            double discountAmount = calculateDiscountPercent(quantity, offer, unitPrice);
            discount = Optional.of(new Discount(p, offer.argument + offer.offerType.getDescription(), -discountAmount));
        }
        if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT && quantityAsInt >= 5) {
            divisor = 5;
            double discountAmount = calculateDiscountN(quantity, offer, unitPrice, quantityAsInt, divisor);
            discount = Optional.of(new Discount(p, divisor + offer.offerType.getDescription() + offer.argument, -discountAmount));
        }
        return discount;
    }

    private double calculateDiscountPercent(double quantity, Offer offer, double unitPrice) {
        return quantity * unitPrice * offer.argument / 100.0;
    }

    private double calculateDiscountN(double quantity, Offer offer, double unitPrice, int quantityAsInt, int divisor) {
        return calculateDiscountN(quantity, offer, unitPrice, quantityAsInt, divisor, 1);
    }

    private double calculateDiscountN(double quantity, Offer offer, double unitPrice, int quantityAsInt, int divisor, int multiplier) {
        int intDivision = quantityAsInt / divisor;
        double pricePerUnit = offer.argument * intDivision * multiplier;
        double totalAboveDiscount = (quantityAsInt % divisor) * unitPrice;
        double total = pricePerUnit + totalAboveDiscount;
        return unitPrice * quantity - total;
    }

}
