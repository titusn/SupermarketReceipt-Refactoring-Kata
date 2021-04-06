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
        int x = 1;
        if (offer.offerType == SpecialOfferType.THREE_FOR_TWO) {
            x = 3;

        } else if (offer.offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
            x = 2;
            if (quantityAsInt >= 2) {
                double discountN = calculateDiscountN(quantity, offer, unitPrice, quantityAsInt, x);
                discount = Optional.of(new Discount(p, offer.offerType.getDescription() + offer.argument, -discountN));
            }

        }
        if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
            x = 5;
        }
        int numberOfXs = quantityAsInt / x;
        if (offer.offerType == SpecialOfferType.THREE_FOR_TWO && quantityAsInt > 2) {
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            discount = Optional.of(new Discount(p, offer.offerType.getDescription(), -discountAmount));
        }
        if (offer.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
            discount = Optional.of(new Discount(p, offer.argument + offer.offerType.getDescription(), -quantity * unitPrice * offer.argument / 100.0));
        }
        if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT && quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
            discount = Optional.of(new Discount(p, x + offer.offerType.getDescription() + offer.argument, -discountTotal));
        }
        return discount;
    }

    private double calculateDiscountN(double quantity, Offer offer, double unitPrice, int quantityAsInt, int x) {
        int intDivision = quantityAsInt / x;
        double pricePerUnit = offer.argument * intDivision;
        double theTotal = (quantityAsInt % 2) * unitPrice;
        double total = pricePerUnit + theTotal;
        return unitPrice * quantity - total;
    }

}
